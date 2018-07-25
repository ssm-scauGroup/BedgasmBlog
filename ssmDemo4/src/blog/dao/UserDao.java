package blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import blog.entity.User;

@Repository("userDao")
public interface UserDao {
	
	/**
	 * ����û�
	 * @param user
	 * @return
	 */
	public Integer addUser(User user);
	/**
	 * ɾ���û�
	 * @param id
	 * @return
	 */
	public Integer deleteUser(Integer id);
	/**
	 * ����id��ȡ�û�
	 * @param id
	 * @return
	 */
	public User findById(Integer id);
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	public Integer updateUser(User user);
	
	/**
	 * �г������û�
	 * @return
	 */
	public List<User> listUser();

}
