package blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.BlackListDao;
import blog.entity.BlackList;
import blog.service.BlackListService;

@Service("blackListService")
public class BlackListServiceImpl implements BlackListService{
	
	@Autowired
	private BlackListDao blackListDao;

	@Override
	public boolean addBlackList(BlackList blackList) {
		int res = 0;
		try {
			res = blackListDao.addBlackList(blackList);
		} catch (Exception e) {
			return false;
		}
		if(res > 0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public BlackList findById(Integer id) {
		return blackListDao.findById(id);
	}

	@Override
	public boolean updateBlackList(BlackList blackList) {
		int res = 0 ;
		try {
			res = blackListDao.updateBlackList(blackList);
		} catch (Exception e) {
			return false;
		}
		if(res > 0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<BlackList> listBlackList(Map<String, Object> map) {
		return blackListDao.listBlackList(map);
	}

}
