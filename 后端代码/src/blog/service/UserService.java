package blog.service;

import java.util.List;
import java.util.Map;

import blog.entity.User;

public interface UserService {
	public Integer addUser(User user);

	public Integer deleteUser(Integer id);

	public User findById(Integer id);

	public Integer updateUser(User user);
	
	public List<User> listUser(Map<String, Object> map);
	
	public User fingByUserLogin(String username);
	
	public User findByUserEmail(String email);
	
	public User findSimpleUser(Integer id);
	
}
