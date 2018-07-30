package blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import blog.entity.User;
import blog.service.UserService;
import blog.util.Md5Encrypt;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userLogin(@RequestParam("username") String username,
			@RequestParam("password") String password,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		User user = userService.fingByUserLogin(username);
		
		if (user==null){
			jsonObject.put("success", false);
		}else{
			if(Md5Encrypt.getMD5(password).equals(user.getPassword())){
				jsonObject.put("success", true);
				jsonObject.put("user", user);
				session.setAttribute("user", user);
				System.out.println(session.getId());
			}
			else{
				jsonObject.put("success", false);
			}			
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userRegister(User user){
		
		JSONObject jsonObject = new JSONObject();
		
		int res = 0;
		
		//用户注册需要填写的信息
		//用户名,密码,邮箱
		user.setRole(1); //0为管理员 1为普通用户
		user.setNickname(user.getUsername()); //昵称和用户名默认一样.	
		user.setPassword(Md5Encrypt.getMD5(user.getPassword()));//md5 password后存进user.password
		
		res = userService.addUser(user);
		
		if(res > 0){
			jsonObject.put("success",true);
			jsonObject.put("user",user);
		}
		else {
			jsonObject.put("success",false);
		}
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 可用于注册时验证用户名或者邮箱是否有效(是否重复).
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/isValid",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String searchIfValid(@RequestParam(value="username",required=false) String username,
							@RequestParam(value="email",required=false) String email) {
		
		JSONObject jsonObject = new JSONObject();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(email!=null){
			map.put("email",email);
		}else if(username!=null){
			map.put("username", username);
		}else{
			jsonObject.put("success", false);
			return jsonObject.toString();
		}
		
		List<User> users = userService.listUser(map);
		
		if(users.size() > 0){
			jsonObject.put("success",true);
			jsonObject.put("isValid",false);
			System.out.println("列表不为空,用户名或邮箱已被占用");
		}else {
			jsonObject.put("success",true);
			jsonObject.put("isValid",true);
			System.out.println("列表为空,用户名或邮箱可用");
		}
		
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userModify(@RequestParam(value="oldpassword",required=false) String oldpassword,
			User user,HttpSession session){
		JSONObject jsonObject = new JSONObject();
		
		//先用session判断是否有登录 没有直接拒绝
		//再判断是不是用户本人或者管理员.(管理员可以用role判断是否是 不能用username判断.如果有多个管理员的话)
		//0为管理员 1为普通用户
		if(getUser(session)==null){
			jsonObject.put("success", false);
			jsonObject.put("reason", "no login");
			return jsonObject.toString();
		}
		
//		if(user==null){
//			jsonObject.put("success", false);
//			jsonObject.put("reason", "user is null");
//			return jsonObject.toString();
//		}
		
		System.out.println(user);
		
		if(!user.getUsername().equals(getUser(session).getUsername()) && 
				getUser(session).getRole()==1){
			jsonObject.put("success", false);
			jsonObject.put("reason", "no privileges");
			return jsonObject.toString();
		}
		if(user.getPassword()!=null){ //TODO 如果修改了密码,前端页面应该在修改成功后提示重新登录 调用logout
			if(!Md5Encrypt.getMD5(oldpassword).equals(getUser(session).getPassword())){
				jsonObject.put("success", false);
				jsonObject.put("reason", "oldpassword error");
				return jsonObject.toString();
			}

			user.setPassword(Md5Encrypt.getMD5(user.getPassword()));//md5 密码之后再寸金
		}
		//修复的数据可以是用户昵称(用户名不能修改),头像,个性签名,密码,邮箱.
		int res = userService.updateUser(user);
		if(res > 0 ){
			jsonObject.put("success", true);
			System.out.println("修改成功");
		}else {
			jsonObject.put("success", false);
			System.out.println("修改失败");
		}
		return jsonObject.toString();
	}
	
	/**
	 * 用户安全退出 清除session
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout",produces="application/json;charset=UTF-8")
	public String userLogout(HttpSession session){
		JSONObject jsonObject = new JSONObject();
		try {
			session.invalidate();
			System.out.println(session.getId());
			jsonObject.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
		}
		return jsonObject.toString();
	}
	
	/**
	 * 管理员增加用户
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userAdd(User user,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		//判断是否是管理员
		if(getUser(session)==null){
			jsonObject.put("success", false);
			jsonObject.put("reason", "no login");
			return jsonObject.toString();
		}
		
		if(getUser(session).getRole()==1){
			jsonObject.put("success", false);
			jsonObject.put("reason", "no privileges");
			return jsonObject.toString();
		}
		// 管理员默认设置用户名,用户昵称,用户邮箱,初始密码均为123456(前端可以设置);
		if(user.getPassword()!=null){
			user.setPassword(Md5Encrypt.getMD5(user.getPassword()));
		}
		int res = userService.addUser(user);
		if(res > 0){
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success",false);
		}
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/userinfo",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userInfo(HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		//判断是否有登录
		if(getUser(session)==null){
			jsonObject.put("success", false);
			jsonObject.put("reason", "no login");
			return jsonObject.toString();
		}
		
//		if(getUser(session).getRole()==1){
//			jsonObject.put("success", false);
//			jsonObject.put("reason", "no privileges");
//			return jsonObject.toString();
//		}
		// 管理员默认设置用户名,用户昵称,用户邮箱,初始密码均为123456(前端可以设置);
		User user = userService.findById(getUser(session).getId());
		jsonObject.put("success", true);
		jsonObject.put("user",user);
		return jsonObject.toString();
	}
	
	/**
	 * 获取session中的User对象(非处理请求方法)
	 */
	public User getUser(HttpSession session){
		 User user = (User)session.getAttribute("user");
		 System.out.println(user);
		 return user;
	}

}
