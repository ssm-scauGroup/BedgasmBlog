package blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import blog.entity.Star;

@Repository("starDao")
public interface StarDao {
	
	/**
	 * 添加订阅关系
	 * @param star
	 * @return
	 */
	public Integer addStar(Star star);
	
	/**
	 * 删除订阅关系
	 * @param subscriber
	 * @param subscribee
	 * @return
	 */
	public Integer deleteStar(Star star);
	
	
	//public Integer deleteStar(Star star);
	
	/**
	 * 列出关注的人
	 * @param subscriber
	 * @return
	 */
	public List<Star> findBySubscriber(Integer subscriber);
	
	/**
	 * 列出自己的粉丝
	 * @param subscribee
	 * @return
	 */
	public List<Star> findBySubscribee(Integer subscribee);
	
	/**
	 * 更新订阅关系
	 * @param star
	 * @return
	 */
	//public Integer updateStar(Star star);
	
	/**
	 * subscriber 关注总数
	 * @param subscriber
	 * @return
	 */
	public Long getTotalSubscriber(Integer subscriber);
	
	/**
	 * subscribee 粉丝总数
	 * @param subscribee
	 * @return
	 */
	public Long getTotalSubscribee(Integer subscribee);
	

}
