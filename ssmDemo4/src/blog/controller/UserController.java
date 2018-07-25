package blog.controller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import blog.entity.User;
import blog.service.UserService;
import blog.util.Md5Encrypt;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("nameoremail") String nameoremail,@RequestParam("password") String password){
		ModelAndView mav=new ModelAndView();
		User user = userService.fingByUserLogin(nameoremail);
		if (user==null){
			mav.setViewName("/error");
		}else{
			if(Md5Encrypt.getMD5(password).equals(user.getPassword())){
				mav.addObject("user",user);
				mav.setViewName("/signin");
			}
			else{
				mav.setViewName("/error");
			}
		}
		
		return mav;
	}

}
