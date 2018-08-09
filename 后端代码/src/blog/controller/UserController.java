package blog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import blog.entity.User;
import blog.service.UserService;
import blog.util.AccountMatch;
import blog.util.Md5Encrypt;
import blog.util.SendMail;
import blog.util.UserSystemInfo;
import blog.util.UserisLogin;
import blog.util.ValidateCode;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session) {

		JSONObject jsonObject = new JSONObject();
		
		User user;
		
		if(AccountMatch.isEmail(username)){//如果是邮箱登录
			user = userService.findByUserEmail(username);
		}else {
			user = userService.fingByUserLogin(username);
		}

		if (user == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户不存在");
		} else {
			if (Md5Encrypt.getMD5(password).equals(user.getPassword())) {
				jsonObject.put("success", true);
				jsonObject.put("msg", "登录成功");
				jsonObject.put("user", user);
				session.setAttribute("user", user);
				System.out.println(session.getId());
			} else {
				jsonObject.put("success", false);
				jsonObject.put("msg", "登录失败：密码错误");
			}
		}

		// String jsonp = callback+"("+jsonObject.toString()+")";

		// System.out.println(jsonp);

		return jsonObject.toString();
	}

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String userRegister(User user, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		/*
		 * 验证邮箱或用户名是否有效
		 */

		List<User> users;

		Map<String, Object> map = new HashMap<String, Object>();

		if (user.getEmail() != null && !user.getEmail().equals("")) {
			map.put("email", user.getEmail());
			users = userService.listUser(map);
			if (users.size() > 0) {
				jsonObject.put("success", true);
				jsonObject.put("isValid", false);
				jsonObject.put("msg", "邮箱已被占用");
				return jsonObject.toString();
			}
			map.clear(); // 验证完清除map
		}

		if (user.getUsername() != null && !user.getUsername().equals("")) {
			map.put("username", user.getUsername());
			users = userService.listUser(map);
			if (users.size() > 0) {
				jsonObject.put("success", true);
				jsonObject.put("isValid", false);
				jsonObject.put("msg", "用户名已被占用");
				return jsonObject.toString();
			}
			map.clear(); // 验证完清除map
		}

		int res = 0;

		// 用户注册需要填写的信息
		// 用户名,密码,邮箱
		user.setRole(1); // 0为管理员 1为普通用户
		user.setNickname(user.getUsername()); // 昵称和用户名默认一样.
		user.setPassword(Md5Encrypt.getMD5(user.getPassword()));// md5
																// password后存进user.password

		String base64Img = "https://ws3.sinaimg.cn/large/006OWbT9gy1ftwxpbhzi8j30bo0bogld.jpg";

		user.setProfile(base64Img);

		String signature = "这个人很懒，什么都没有留下";

		user.setSignature(signature);

		try {
			res = userService.addUser(user);
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
			jsonObject.put("msg", "注册失败 出现问题");
			return jsonObject.toString();
		}

		if (res > 0) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "注册成功");
			jsonObject.put("user", user);
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "注册失败");
		}

		return jsonObject.toString();

	}

	/**
	 * 可用于注册时验证用户名或者邮箱是否有效(是否重复).
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isValid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String searchIfValid(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "email", required = false) String email, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		Map<String, Object> map = new HashMap<String, Object>();

		if (email != null && !email.equals("")) {
			map.put("email", email);
		} else if (username != null && !username.equals("")) {
			map.put("username", username);
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "传入参数的值为空");
			return jsonObject.toString();
		}

		List<User> users = userService.listUser(map);

		if (users.size() > 0) {
			jsonObject.put("success", true);
			jsonObject.put("isValid", false);
			jsonObject.put("msg", "用户名或邮箱已被占用");
		} else {
			jsonObject.put("success", true);
			jsonObject.put("isValid", true);
			jsonObject.put("msg", "用户名或邮箱可用");
		}

		return jsonObject.toString();
	}

	/**
	 * 修改用户信息
	 * 
	 * @param oldpassword
	 * @param user
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String userModify(@RequestParam(value = "oldpassword", required = false) String oldpassword, User user,
			HttpSession session) {
		JSONObject jsonObject = new JSONObject();

		// 先用session判断是否有登录 没有直接拒绝
		// 再判断是不是用户本人或者管理员.(管理员可以用role判断是否是 不能用username判断.如果有多个管理员的话)
		// 0为管理员 1为普通用户
		if (UserisLogin.getUser(session) == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}

		System.out.println(user);

		if (!user.getUsername().equals(UserisLogin.getUser(session).getUsername())
				&& UserisLogin.getUser(session).getRole() == 1) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		if (user.getPassword() != null) { // TODO 如果修改了密码,前端页面应该在修改成功后提示重新登录
											// 调用logout
			if (!Md5Encrypt.getMD5(oldpassword).equals(UserisLogin.getUser(session).getPassword())) {
				jsonObject.put("success", false);
				jsonObject.put("msg", "oldpassword error");
				return jsonObject.toString();
			}

			user.setPassword(Md5Encrypt.getMD5(user.getPassword()));// md5
																	// 密码之后再存进实体类
		}
		// 修复的数据可以是用户昵称(用户名不能修改),头像,个性签名,密码,邮箱.
		int res = userService.updateUser(user);
		if (res > 0) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "修改成功");
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "修改失败");
		}
		return jsonObject.toString();
	}

	/**
	 * 用户安全退出 清除session
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8")
	public String userLogout(HttpSession session) {
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
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String userAdd(User user, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		// 判断是否有登录
		if (UserisLogin.getUser(session) == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}

		// 判断是否是管理员
		if (UserisLogin.getUser(session).getRole() == 1) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}

		System.out.println("user is " + user);

		// if(user==null){
		// jsonObject.put("success", false);
		// jsonObject.put("msg", "没有传入user对象");
		// return jsonObject.toString();
		// }

		// TODO 管理员默认设置用户名,用户昵称,用户邮箱,初始密码均为123456(前端可以设置);
		if (user.getPassword() != null) {
			user.setPassword(Md5Encrypt.getMD5(user.getPassword()));
		}

		int res = 0;

		try {
			user.setNickname(user.getUsername()); // 昵称和用户名默认一样.
			user.setProfile("https://ws3.sinaimg.cn/large/006OWbT9gy1ftwxpbhzi8j30bo0bogld.jpg");
			user.setSignature("这个人很懒，什么都没有留下");
			res = userService.addUser(user);
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
			jsonObject.put("msg", "没有传入user对象或者插入数据库失败");
			return jsonObject.toString();
		}

		if (res > 0) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "添加用户成功");
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "添加用户失败");
		}
		return jsonObject.toString();
	}

	/**
	 * 查看登录用户个人信息。可用于查看是否登录，与其他接口相联合，例如游客没有登录的话
	 * 不能发布文章，登录的人没有管理员权限(可以通过role属性判断)的话，不能进行用户删除添加一些相关操作等。
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String userInfo(HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		// User user = UserisLogin.getUser(session);

		System.out.println("userinfo sessionid " + session.getId());

		User user = (User) session.getAttribute("user");

		// 判断是否有登录
		if (user == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}

		jsonObject.put("success", true);
		jsonObject.put("user", user);

		// String jsonp = callback+"("+jsonObject.toString()+")";

		// System.out.println("jsonp is "+jsonp);

		return jsonObject.toString();
	}

	/**
	 * 通过id查询用户信息
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userinfobyid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String userInfobyId(@RequestParam(value = "id", required = false) String id, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		if (id == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "没有传入用户id");
			return jsonObject.toString();
		}

		User user = userService.findSimpleUser(Integer.parseInt(id));

		// 判断是否存在
		if (user == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户不存在");
			return jsonObject.toString();
		}

		jsonObject.put("success", true);
		jsonObject.put("user", user);

		return jsonObject.toString();

	}

	/**
	 * 根据条件查询用户
	 * 
	 * @param email
	 * @param username
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String listUser(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "username", required = false) String username, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		User user = UserisLogin.getUser(session);

		// 判断是否有登录
		if (user == null) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}

		// 如果不是管理员，拒绝
		if (user.getRole() == 1) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}

		Map<String, Object> map = new HashMap<String, Object>();

		// 因为在mapper.xml已经有test是否为null或者'',所以这里不用再判断
		map.put("email", email);
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
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String deleteUser(@RequestParam("id") String id, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		// User user = UserisLogin.getUser(session);
		//
		// //判断是否有登录
		// if(user==null){
		// jsonObject.put("success", false);
		// jsonObject.put("msg", "用户没有登录");
		// return jsonObject.toString();
		// }
		//
		// //如果不是管理员，拒绝
		// if(user.getRole()==1){
		// jsonObject.put("success", false);
		// jsonObject.put("msg", "无权限");
		// return jsonObject.toString();
		// }
		//
		// User user2 = userService.findById(Integer.parseInt(id));
		//
		// if(user2==null){
		// jsonObject.put("success", false);
		// jsonObject.put("msg", "用户不存在");
		// return jsonObject.toString();
		// }
		//
		// //TODO 应该考虑删除用户的一些后果。建议搞一个拉黑选项 这样用户不能登录
		//
		// int res = userService.deleteUser(Integer.parseInt(id));
		//
		// if (res > 0) {
		// jsonObject.put("success", true);
		// jsonObject.put("msg", "删除用户成功");
		// }else{
		// jsonObject.put("success", false);
		// jsonObject.put("msg", "删除用户失败");
		// }

		jsonObject.put("success", false);
		jsonObject.put("msg", "接口未实现");

		return jsonObject.toString();
	}

	/**
	 * 验证码生成
	 */
	@RequestMapping(value = "/validatecode")
	public void ValidateCode(HttpSession session, HttpServletResponse response) {

		// JSONObject jsonObject = new JSONObject();

		ValidateCode vCode = new ValidateCode();
		try {
			vCode.write(response.getOutputStream());
			session.setAttribute("vcode", vCode.getCode().toLowerCase());
			System.out.println(vCode.getCode());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 检验验证码
	 * 
	 * @param vcode
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkcode", produces = "application/json;charset=UTF-8")
	public String checkCode(@RequestParam(value = "vcode", required = true) String vcode, HttpSession session) {
		JSONObject jsonObject = new JSONObject();

		String code = (String) session.getAttribute("vcode");

		if (code == null || code.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "验证码已失效");
			return jsonObject.toString();
		}

		System.out.println("验证码真实是" + code);
		System.out.println("你输入的是" + vcode);

		if (code.equals(vcode.toLowerCase())) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "验证成功");
			session.setAttribute("vcode", ""); // 清除session vcode
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "验证失败");
			session.setAttribute("vcode", ""); // 清除session vcode
		}
		return jsonObject.toString();
	}

	/**
	 * 重置密码发送邮件
	 * 
	 * @param email
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String resetPassword(@RequestParam(value = "email", required = true) String email, HttpSession session) {

		System.out.println(email);

		JSONObject jsonObject = new JSONObject();

		Map<String, Object> map = new HashMap<String, Object>();

		if (email == null || email.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "邮箱为空");
			return jsonObject.toString();
		}

		map.put("email", email);

		List<User> users = userService.listUser(map);

		if (users.size() == 0) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "邮箱错误");
			return jsonObject.toString();
		}

		String resetCodeString = UUID.randomUUID().toString().replaceAll("\\-", "");

		System.out.println("resetcode is " + resetCodeString);

		String host = "http://bedgasmblog.cn";

		// String localhostString = "http://localhost:8080/ssmDemo4";

		String localString = host + "/resetpassword.html";

		String resetUrl = localString + "?rescode=" + resetCodeString;

		System.out.println(resetUrl);

		// String sendString = "您在<a
		// href='http://bedgasmblog.cn'>bedgasmblog</a>网站通过邮件发送重置密码，请点击一下链接进行重置密码操作"+
		// "<br/><h2><a
		// href="+resetUrl+">"+resetUrl+"</a></h2>"+"<br/><br/><br/>如果不是您本人操作，请忽略此邮件。<br/>该邮件为系统邮件，请勿回复。";

		String sendString = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><title>MAIL</title><link rel=\"stylesheet\" href=\"https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css\"></head><style>.logo {padding-top: 22px;overflow: hidden;}footer {width: 100%;bottom: 0px;padding: 0;font-size: 12px;font-weight: 500;text-transform: uppercase;letter-spacing: 1px;color: #717171;text-align: center;border-top: 1px solid #CECECE;margin-top: 50px;}footer .footer-bottom {padding: 10px 0 40px;}.footer2 {position: absolute;bottom: 0;}</style><body><div align=\"center\"><a href='http://bedgasmblog.cn' align=\"middle\"><img src=\"http://bedgasmblog.cn/bedgasmblog.png\" align=\"middle\" /></a></div><h4 align=\"center\">您在bedgasmblog通过邮件发送重置密码，请点击以下链接重置您的密码</h4><br/><h3 align=\"center\">"
				+ "<a href=" + resetUrl + ">" + resetUrl + "</a>"
				+ "</h3><br/><p align=\"center\"><br/>如果不是您本人操作，请忽略此邮件。<br/>该邮件为系统邮件，请勿回复。</p><footer class=\"footer\"><div class=\"footer-bottom\"><h5>感谢使用Bedgasm Blog</h5><i class=\"fa fa-copyright\"></i> Copyright 2018 Bedgasm Blog. All Rights Reserved.<br></div></footer></body></html>";

		boolean sendEmailStatus = SendMail.sendMail(email, sendString, "重置您的账户密码");

		if (sendEmailStatus) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "邮件发送成功");
			session.setAttribute("resetcode", resetCodeString);
			session.setAttribute("email", email);
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "邮件发送失败");
		}

		return jsonObject.toString();
	}

	/**
	 * 检验链接的有效性
	 * 
	 * @param rescode
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkResetUrl", produces = "application/json;charset=UTF-8")
	public String checkResetUrl(@RequestParam(value = "rescode", required = true) String rescode, HttpSession session) {

		System.out.println("rescode is " + rescode);

		JSONObject jsonObject = new JSONObject();

		String code = (String) session.getAttribute("resetcode");
		// String email = (String)session.getAttribute("email");

		if (code == null || code.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "重置链接已失效");
			return jsonObject.toString();
		}

		// System.out.println("session code is "+code);
		// System.out.println("session email is "+email);

		if (code.equals(rescode)) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "验证成功");
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "验证失败");
		}
		return jsonObject.toString();
	}

	/**
	 * 重置用户的密码
	 * 
	 * @param rescode
	 * @param password
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetUserPassword", produces = "application/json;charset=UTF-8")
	public String resetUserPassword(@RequestParam(value = "rescode", required = true) String rescode,
			@RequestParam(value = "password", required = true) String password, HttpSession session) {

		// System.out.println("rescode is "+rescode);

		JSONObject jsonObject = new JSONObject();

		if (password == null || password.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "password is required");
			return jsonObject.toString();
		}

		String code = (String) session.getAttribute("resetcode");

		if (code == null || code.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "链接已失效");
			return jsonObject.toString();
		}
		if (!code.equals(rescode)) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "链接已失效");
			return jsonObject.toString();
		}

		String email = (String) session.getAttribute("email");

		if (email == null || email.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "链接已失效");
			return jsonObject.toString();
		}

		// System.out.println("session code is "+code);
		// System.out.println("session email is "+email);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("email", email);

		List<User> users = userService.listUser(map);

		int res = 0;

		for (User user : users) {
			try {
				System.out.println("before is " + user.getPassword());
				user.setPassword(Md5Encrypt.getMD5(password));
				System.out.println(user.getId());
				System.out.println("after is " + user.getPassword());
				res = userService.updateUser(user);
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("success", false);
				jsonObject.put("msg", "更新过程中出现错误");
				return jsonObject.toString();
			}
		}

		if (res > 0) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "更新成功");
			session.invalidate(); // 重置成功后清楚session
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "更新失败");
		}

		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getusersysteminfo", produces = "application/json;charset=UTF-8")
	public String getUserSystemInfo(HttpServletRequest request, HttpSession session) {

		JSONObject jsonObject = new JSONObject();

		String userIpAddr, userBrowser, userSystem, userHostName;

		try {
			userIpAddr = UserSystemInfo.getIpAddr(request);
			userBrowser = UserSystemInfo.getRequestBrowserInfo(request);
			userSystem = UserSystemInfo.getRequestSystemInfo(request);
			userHostName = UserSystemInfo.getHostName(userIpAddr);
			// System.out.println("==============");
			// System.out.println(userIpAddr);
			// System.out.println(userBrowser);
			// System.out.println(userSystem);
			// System.out.println(userHostName);
			// System.out.println("==============");
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取用户系统信息失败");
			return jsonObject.toString();
		}

		jsonObject.put("success", true);
		jsonObject.put("IP", userIpAddr);
		jsonObject.put("Browser", userBrowser);
		jsonObject.put("OS", userSystem);
		jsonObject.put("HOSTNAME", userHostName);

		return jsonObject.toString();
	}

}
