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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import blog.entity.Article;
import blog.entity.User;
import blog.service.ArticleService;
import blog.service.UserService;
import blog.util.Md5Encrypt;
import blog.util.UserisLogin;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleService articleService;
	
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
			jsonObject.put("msg", "用户不存在");
		}else{
			if(Md5Encrypt.getMD5(password).equals(user.getPassword())){
				jsonObject.put("success", true);
				jsonObject.put("msg", "登录成功");
				jsonObject.put("user", user);
				session.setAttribute("user", user);
				System.out.println(session.getId());
			}
			else{
				jsonObject.put("success", false);
				jsonObject.put("msg", "登录失败：密码错误");
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
			jsonObject.put("msg","注册成功");
			jsonObject.put("user",user);
		}
		else {
			jsonObject.put("success",false);
			jsonObject.put("msg", "注册失败");
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
		
		if(email!=null && !email.equals("")){
			map.put("email",email);
		}else if(username!=null && !username.equals("")){
			map.put("username", username);
		}else{
			jsonObject.put("success", false);
			jsonObject.put("msg", "传入参数的值为空");
			return jsonObject.toString();
		}
		
		List<User> users = userService.listUser(map);
		
		if(users.size() > 0){
			jsonObject.put("success",true);
			jsonObject.put("isValid",false);
			jsonObject.put("msg","用户名或邮箱已被占用");
		}else {
			jsonObject.put("success",true);
			jsonObject.put("isValid",true);
			jsonObject.put("msg","用户名或邮箱可用");
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 修改用户信息
	 * @param oldpassword
	 * @param user
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userModify(@RequestParam(value="oldpassword",required=false) String oldpassword,
			User user,HttpSession session){
		JSONObject jsonObject = new JSONObject();
		
		//先用session判断是否有登录 没有直接拒绝
		//再判断是不是用户本人或者管理员.(管理员可以用role判断是否是 不能用username判断.如果有多个管理员的话)
		//0为管理员 1为普通用户
		if(UserisLogin.getUser(session)==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		System.out.println(user);
		
		if(!user.getUsername().equals(UserisLogin.getUser(session).getUsername()) && 
				UserisLogin.getUser(session).getRole()==1){
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		if(user.getPassword()!=null){ //TODO 如果修改了密码,前端页面应该在修改成功后提示重新登录 调用logout
			if(!Md5Encrypt.getMD5(oldpassword).equals(UserisLogin.getUser(session).getPassword())){
				jsonObject.put("success", false);
				jsonObject.put("msg", "oldpassword error");
				return jsonObject.toString();
			}

			user.setPassword(Md5Encrypt.getMD5(user.getPassword()));//md5 密码之后再存进实体类
		}
		//修复的数据可以是用户昵称(用户名不能修改),头像,个性签名,密码,邮箱.
		int res = userService.updateUser(user);
		if(res > 0 ){
			jsonObject.put("success", true);
			jsonObject.put("msg","修改成功");
		}else {
			jsonObject.put("success", false);
			jsonObject.put("msg","修改失败");
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
			jsonObject.put("msg", "用户已安全退出。");
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户安全退出失败");
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
		
		//判断是否有登录
		if(UserisLogin.getUser(session)==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		//判断是否是管理员
		if(UserisLogin.getUser(session).getRole()==1){
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		
		System.out.println("user is "+user);
		
//		if(user==null){
//			jsonObject.put("success", false);
//			jsonObject.put("msg", "没有传入user对象");
//			return jsonObject.toString();
//		}
		
		// TODO 管理员默认设置用户名,用户昵称,用户邮箱,初始密码均为123456(前端可以设置);
		if(user.getPassword()!=null){
			user.setPassword(Md5Encrypt.getMD5(user.getPassword()));
		}
		
		int res = 0 ;
		
		try {
			user.setNickname(user.getUsername()); //昵称和用户名默认一样.
			res = userService.addUser(user);
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
			jsonObject.put("msg", "没有传入user对象或者插入数据库失败");
			return jsonObject.toString();
		}
		
		if(res > 0){
			jsonObject.put("success", true);
			jsonObject.put("msg", "添加用户成功");
		}else {
			jsonObject.put("success",false);
			jsonObject.put("msg", "添加用户失败");
		}
		return jsonObject.toString();
	}
	
	/**
	 * 查看登录用户个人信息。可用于查看是否登录，与其他接口相联合，例如游客没有登录的话
	 * 不能发布文章，登录的人没有管理员权限(可以通过role属性判断)的话，不能进行用户删除添加一些相关操作等。
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userinfo",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String userInfo(HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		User user = UserisLogin.getUser(session);
		
		//判断是否有登录
		if(user==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		jsonObject.put("success", true);
		jsonObject.put("user",user);
		return jsonObject.toString();
	}
	
	/**
	 * 根据条件查询用户
	 * @param email
	 * @param username
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listUser(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "username", required = false) String username,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		User user = UserisLogin.getUser(session);
		
		//判断是否有登录
		if(user==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		//如果不是管理员，拒绝
		if(user.getRole()==1){
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//因为在mapper.xml已经有test是否为null或者'',所以这里不用再判断
		map.put("email",email);
		map.put("username", username);
		
		List<User> users = userService.listUser(map);
		
		Integer total = users.size();

		JSONArray array = JSON
				.parseArray(JSONObject.toJSONString(users, SerializerFeature.DisableCircularReferenceDetect));
		
		jsonObject.put("success", true);
		jsonObject.put("total", total);
		jsonObject.put("users", array);

		return jsonObject.toString();
	}
	
	
	/**
	 * 通过id删除用户(单个用户)
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String deleteUser(@RequestParam("id") String id,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		User user = UserisLogin.getUser(session);
		
		//判断是否有登录
		if(user==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		//如果不是管理员，拒绝
		if(user.getRole()==1){
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		
		User user2 = userService.findById(Integer.parseInt(id));
		
		if(user2==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户不存在");
			return jsonObject.toString();
		}
		
		//TODO 应该考虑删除用户的一些后果。建议搞一个拉黑选项 这样用户不能登录
		
		int res = userService.deleteUser(Integer.parseInt(id));
		
		if (res > 0) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "删除用户成功");
		}else{
			jsonObject.put("success", false);
			jsonObject.put("msg", "删除用户失败");
		}
		return jsonObject.toString();
	}

}
