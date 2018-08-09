package blog.service;

import java.util.List;

import blog.entity.Star;

public interface StarService {
	public Integer addStar(Star star);

	public Integer deleteStar(Star star);

	public List<Star> findBySubscriber(Integer subscriber);

	public List<Star> findBySubscribee(Integer subscribee);

//	public Integer updateStar(Star star);

	public Long getTotalSubscriber(Integer subscriber);

	public Long getTotalSubscribee(Integer subscribee);
}
