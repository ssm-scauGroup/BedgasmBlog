package com.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserDao;
import com.domain.User;//域模型
import com.model.Usertable;//持久化模型

@Service
@Transactional
//注意@Transactional只能针对public属性范围内的方法添加
public class UserServiceImpl implements UserService{
	@Autowired
	private  UserDao userDao;
	/**
	 * 注册
	 */
	@Override
	public int insert(User user) {
		Usertable record= new Usertable();
		//将源对象和目标对象中相同名称的属性值复制过去，注意前面的参数是源对象，后面是目标对象。
		BeanUtils.copyProperties(user, record);//将域模型封装到持久化对象， 保证在dao中不使用域模型
		return userDao.insert(record);
	}
	/**
	 * 判断用户名是否已存在
	 */
	@Override
	public Usertable selectByPrimaryKey(String username) {
		return userDao.selectByPrimaryKey(username);
	}
	/**
	 * 登录
	 */
	@Override
	public Usertable selectByUser(User user) {
		Usertable record= new Usertable();
		//将域模型封装到持久化对象
		BeanUtils.copyProperties(user, record);
		return userDao.selectByUser(record);
	}
	/**
	 * 修改密码
	 */
	@Override
	public int updateByPrimaryKeySelective(User user) {
		Usertable record= new Usertable();
		//将域模型封装到持久化对象
		BeanUtils.copyProperties(user, record);
		return userDao.updateByPrimaryKeySelective(record);
	}
}
