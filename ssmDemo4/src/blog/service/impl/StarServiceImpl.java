package blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.StarDao;
import blog.entity.Star;
import blog.service.StarService;

@Service("starService")
public class StarServiceImpl implements StarService {
	@Autowired
	private StarDao sdo;
	@Override
	public Integer addStar(Star star) {
		// TODO Auto-generated method stub
		return sdo.addStar(star);
	}

	@Override
	public List<Star> findBySubscriber(Integer subscriber) {
		// TODO Auto-generated method stub
		return sdo.findBySubscriber(subscriber);
	}

	@Override
	public List<Star> findBySubscribee(Integer subscribee) {
		// TODO Auto-generated method stub
		return sdo.findBySubscribee(subscribee);
	}

//	@Override
//	public Integer updateStar(Star star) {
//		// TODO Auto-generated method stub
//		return sdo.updateStar(star);
//	}

	@Override
	public Long getTotalSubscriber(Integer subscriber) {
		// TODO Auto-generated method stub
		return sdo.getTotalSubscriber(subscriber);
	}

	@Override
	public Long getTotalSubscribee(Integer subscribee) {
		// TODO Auto-generated method stub
		return sdo.getTotalSubscribee(subscribee);
	}

	@Override
	public Integer deleteStar(Star star) {
		// TODO Auto-generated method stub
		return sdo.deleteStar(star);
	}

}
