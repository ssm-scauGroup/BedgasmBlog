package blog.dao;

import java.util.List;

import java.util.Map;

import org.springframework.stereotype.Repository;

import blog.entity.User;

@Repository("userDao")
public interface UserDao {
	
	/**
	 * 娣诲姞鐢ㄦ埛
	 * @param user
	 * @return
	 */
	public Integer addUser(User user);
	/**
	 * 鍒犻櫎鐢ㄦ埛
	 * @param id
	 * @return
	 */
	public Integer deleteUser(Integer id);
	/**
	 * 鏍规嵁id鑾峰彇鐢ㄦ埛
	 * @param id
	 * @return
	 */
	public User findById(Integer id);
	/**
	 * 鏇存柊鐢ㄦ埛
	 * @param user
	 * @return
	 */
	public Integer updateUser(User user);
	
	/**
	 * 鏍规嵁鏉′欢鏌ヨ
	 * @param map
	 * @return
	 */
	public List<User> listUser(Map<String, Object> map);
	
	/**
	 * 鏍规嵁鐢ㄦ埛鍚嶆煡鍑烘敼鐢ㄦ埛
	 * @param username
	 * @return
	 */
	public User fingByUserLogin(String username);
}
