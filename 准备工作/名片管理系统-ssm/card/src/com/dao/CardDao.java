package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.model.Cardinfo; 
@Repository("cardDao")
@Mapper//使用Spring自动扫描MyBatis的接口并装配（Spring会将指定包中的所有被@Mapper注解标注的接口自动装配为MyBatis的映射接口
public interface CardDao{
    int deleteByPrimaryKey(Integer id);

    int insert(Cardinfo record);

    int insertSelective(Cardinfo record);

    Cardinfo selectByPrimaryKey(Integer id);
    
    List<Cardinfo>	selectAll(String userName);
    
    List<Cardinfo> selectAllByPage(Map<String, Object> map);
    
    int updateByPrimaryKeySelective(Cardinfo record);

    int updateByPrimaryKey(Cardinfo record);
}