package com.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.exception.LoginNoException;
@Controller
public class BaseController {
	/**
	 * 登录权限控制，处理方法执行前执行该方法
	 * @throws LoginNoException 
	 */
	@ModelAttribute  
    public void isLogin(HttpSession session, HttpServletRequest request) throws LoginNoException {      
       if(session.getAttribute("user") == null){  
            throw new LoginNoException("没有登录");
       }  
    } 
}
