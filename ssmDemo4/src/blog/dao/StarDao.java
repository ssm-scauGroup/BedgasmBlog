package blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import blog.entity.Star;

@Repository("starDao")
public interface StarDao {
	
	/**
	 * ��Ӷ��Ĺ�ϵ
	 * @param star
	 * @return
	 */
	public Integer addStar(Star star);
	
	/**
	 * ɾ�����Ĺ�ϵ
	 * @param subscriber
	 * @param subscribee
	 * @return
	 */
	public Integer deleteStar(@Param("subscriber") Integer subscriber,@Param("subscribee") Integer subscribee);
	
	
	//public Integer deleteStar(Star star);
	
	/**
	 * �г���ע����
	 * @param subscriber
	 * @return
	 */
	public List<Star> findBySubscriber(Integer subscriber);
	
	/**
	 * �г��Լ��ķ�˿
	 * @param subscribee
	 * @return
	 */
	public List<Star> findBySubscribee(Integer subscribee);
	
	/**
	 * ���¶��Ĺ�ϵ
	 * @param star
	 * @return
	 */
	//public Integer updateStar(Star star);
	
	/**
	 * subscriber ��ע����
	 * @param subscriber
	 * @return
	 */
	public Long getTotalSubscriber(Integer subscriber);
	
	/**
	 * subscribee ��˿����
	 * @param subscribee
	 * @return
	 */
	public Long getTotalSubscribee(Integer subscribee);
	

}
