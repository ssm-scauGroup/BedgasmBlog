package com.exception;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.model.Usertable;
public class MyExceptionHandler implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3) {
	   	Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", arg3); 
        // 根据不同错误转向不同页面  
        if(arg3 instanceof SQLException) { 
        	return new ModelAndView("/error/sql-error", model);
        }else if(arg3 instanceof LoginNoException){
        	//登录页面需要user对象
        	arg0.setAttribute("user", new Usertable());
        	return new ModelAndView("/user/login", model);
        }else{  
        	return new ModelAndView("/error/error", model);  
        }  
	}
}
