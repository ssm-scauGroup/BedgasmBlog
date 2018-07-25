package blog.service;

import java.util.List;

import blog.entity.User;

public interface UserService {
	public Integer addUser(User user);

	public Integer deleteUser(Integer id);

	public User findById(Integer id);

	public Integer updateUser(User user);
	
	public List<User> listUser();
	
	public User fingByUserLogin(String username);
	
}
