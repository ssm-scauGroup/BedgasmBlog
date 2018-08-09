package com.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.model.Usertable;
@Repository("userDao") 
@Mapper
public interface UserDao {
    int deleteByPrimaryKey(String username);

    int insert(Usertable record);

    int insertSelective(Usertable record);
    
    Usertable selectByUser(Usertable record);
    
    Usertable selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Usertable record);

    int updateByPrimaryKey(Usertable record);
}