package blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.UserDao;
import blog.entity.User;
import blog.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao udo;
	@Override
	public Integer addUser(User user) {
		// TODO Auto-generated method stub
		return udo.addUser(user);
	}

	@Override
	public Integer deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return udo.deleteUser(id);
	}

	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return udo.findById(id);
	}

	@Override
	public Integer updateUser(User user) {
		// TODO Auto-generated method stub
		return udo.updateUser(user);
	}

	@Override
	public List<User> listUser() {
		// TODO Auto-generated method stub
		return udo.listUser();
	}

	@Override
	public User fingByUserLogin(String username) {
		// TODO Auto-generated method stub
		return udo.fingByUserLogin(username);
	}

}
