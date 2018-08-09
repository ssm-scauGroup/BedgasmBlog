package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.CardDao;
import com.domain.Card;
import com.model.Cardinfo;
@Service
@Transactional
//注意@Transactional只能针对public属性范围内的方法添加
public class CardServiceImpl implements CardService{
	@Autowired
	private CardDao cardDao;

	@Override
	public List<Cardinfo> selectAll(String userName) {
		return  cardDao.selectAll(userName);
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<Cardinfo> selectAllByPage(Integer page, String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("startIndex", (page - 1) * 10);//起始位置
		map.put("perPageSize", 10);//每页10个
		return cardDao.selectAllByPage(map);
	}
	/**
	 * 添加
	 */
	@Override
	public int insert(Card card) {
		Cardinfo record = new Cardinfo();
		BeanUtils.copyProperties(card, record);//将域模型封装到持久化对象， 保证在dao中不使用域模型
		return cardDao.insert(record);
	}
	/**
	 * 修改
	 */
	@Override
	public int updateByPrimaryKeySelective(Card card) {
		Cardinfo record = new Cardinfo();
		BeanUtils.copyProperties(card, record);//将域模型封装到持久化对象， 保证在dao中不使用域模型
		return cardDao.updateByPrimaryKeySelective(record);
	}
	/**
	 * 查询一个名片
	 */
	@Override
	public Cardinfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return cardDao.selectByPrimaryKey(id);
	}
	/**
	 * 删除一个名片
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return cardDao.deleteByPrimaryKey(id);
	}
	@Override
	public int deleteCards(Integer[] ids) {
		for (int i = 0; i < ids.length; i++) {
			cardDao.deleteByPrimaryKey(ids[i]);
		}
		return 1;
	}
	

}
