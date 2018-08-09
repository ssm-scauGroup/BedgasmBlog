package com.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.domain.User;//域模型
import com.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController {
	//依赖注入UserService进行后台处理
	@Autowired
	private UserService userService;
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	public String login(@ModelAttribute User user, Model model, HttpSession session) {
		if(userService.selectByUser(user) != null){
			session.setAttribute("user", user);
			return "/card/main";
		}
		model.addAttribute("errorMessage", "用户名或密码错误");
		return "/user/login";
	}
	/**
	 * 注册
	 */
	@RequestMapping("/register")
	public String register(@ModelAttribute User user, Model model) {
		if(user.getFlag() == 0){
			if(userService.selectByPrimaryKey(user.getUsername()) != null)
				//用户名已存在
				model.addAttribute("isExit", "false");
			else
				//用户名可用
				model.addAttribute("isExit", "true");
			return "/user/register";
		}else{
			//注册成功
			if(userService.insert(user) > 0)
				return "/user/login";
			//注册失败
			return "/user/register";
		}
	}
	/**
	 * 安全退出
	 */
	@RequestMapping("/exit")
	public String exit(HttpSession session, Model model){
		session.invalidate();
		model.addAttribute("user", new User());
		return "/user/login";
	}
	/**
	 * 基本信息
	 */
	@RequestMapping("/userInfo")
	public String userInfo(HttpSession session, Model model){
		User u = (User)session.getAttribute("user");
		model.addAttribute("user", u);
		return "/user/userInfo";
	}
	/**
	 * 修改密码初始页面
	 */
	@RequestMapping("/updatePWD")
	public String updateInput(HttpSession session, Model model){
		User u = (User)session.getAttribute("user");
		model.addAttribute("user", u);
		return "/user/updatePWD";
	}
	/**
	 * 修改密码
	 */
	@RequestMapping("/updateUser")
	public String updateUser(HttpSession session, String password, Model model){
		User u = (User)session.getAttribute("user");
		model.addAttribute("user", u);
		u.setPassword(password);
		if(userService.updateByPrimaryKeySelective(u) > 0){
			return "/user/login";
		}else{
			return "/user/updatePWD";
		}
	}
}
