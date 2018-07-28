package blog.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.UserDao;
import blog.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class UserDaoTest {
	
	@Resource
	private UserDao userDao;
	
//	@Test
//	public void addUser() throws Exception{
//		User user = new User();
//		user.setUsername("nihao");
//		user.setEmail("123@qq.com");
//		user.setPassword("123456");
//		user.setNickname("nihao");
//		//User u = new User("ronething","123456","axing","178965100@qq.com","这是一个个性签名","images/touxiang",1);
//		Integer res = userDao.addUser(user);
//		System.out.println(res);
//	}
	
//	@Test
//	public void updateUser() throws Exception{
//		User user = new User();
//		user.setId(3);
//		user.setUsername("demo");
//		user.setPassword("12345678");
//		user.setEmail("axingfly@gmail.com");
//		user.setSignature("这是一个测试");
//		user.setProfile("这是一个profile");
//		user.setRole(0);
//		int res = userDao.updateUser(user);
//		System.out.println(res);
//	}
	
//	@Test
//	public void findById() throws Exception{
//		User user = userDao.findById(3);
//		System.out.println(user);
//	}
	
//	@Test
//	public void listUser() throws Exception{
//		List<User> users = userDao.listUser();
//		for (User user : users) {
//			System.out.println(user);
//		}
//	}
	
//	@Test
//	public void fingByUserLogin() throws Exception{
//		User user = userDao.fingByUserLogin("demo");
//		System.out.println(user.getPassword());
//	}
}
