package blog.util;

import javax.servlet.http.HttpSession;

import blog.entity.User;

/**
 * @author Administrator
 * 获取session中的User对象
 */
public class UserisLogin {
	
	public static User getUser(HttpSession session){
		 User user = (User)session.getAttribute("user");
		 System.out.println(user);
		 return user;
	}

}
