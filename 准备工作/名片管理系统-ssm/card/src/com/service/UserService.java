package com.service;

import com.domain.User;
import com.model.Usertable;
public interface UserService {
	int insert(User user);
	Usertable selectByPrimaryKey(String username);
	Usertable selectByUser(User user);
	int updateByPrimaryKeySelective(User user);
}
