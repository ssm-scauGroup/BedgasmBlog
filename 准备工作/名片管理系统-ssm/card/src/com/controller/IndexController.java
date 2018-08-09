package com.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.domain.User;
@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		//跳转到user目录下
		return "/user/login";
	}
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "/user/register";
	}
}