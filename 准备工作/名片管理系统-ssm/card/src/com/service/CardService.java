package com.service;
import java.util.List;

import com.domain.Card;
import com.model.Cardinfo;
public interface CardService {
	 List<Cardinfo>	selectAll(String userName);
	 List<Cardinfo>	selectAllByPage(Integer page, String userName);
	 int insert(Card card);
	 int updateByPrimaryKeySelective(Card card);
	 Cardinfo selectByPrimaryKey(Integer id);
	 int deleteByPrimaryKey(Integer id);
	 int deleteCards (Integer ids[]);
}
