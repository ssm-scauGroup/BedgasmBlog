package blog.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.StarDao;
import blog.entity.Star;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class StarDaoTest {
	
	@Resource
	private StarDao starDao;
	
//	@Test
//	public void addStar() throws Exception{
//		Star star = new Star(1,2);
//		int res = starDao.addStar(star);
//		System.out.println(res);
//	}
	
//	@Test
//	public void deleteStar() throws Exception{
//		int res = starDao.deleteStar(2,3);
//		System.out.println(res);
//	}
	
//	@Test
//	public void findBySubscriber() throws Exception{
//		List<Star> stars = starDao.findBySubscriber(2);
//		for (Star star : stars) {
//			System.out.println("�û� 2 ��ע������ "+star.getSubscribee());
//		}
//	}
	
//	@Test
//	public void findBySubscribee() throws Exception{
//		List<Star> stars = starDao.findBySubscribee(2);
//		for (Star star : stars) {
//			System.out.println("�û� 2 �ķ�˿�� "+star.getSubscriber());
//		}
//	}
	
//	@Test
//	public void getTotalSubscriber() throws Exception{
//		Long res = starDao.getTotalSubscriber(2);
//		System.out.println("�û�2��ע��������"+res);
//	}
//	
//	@Test
//	public void getTotalSubscribee() throws Exception{
//		Long res = starDao.getTotalSubscribee(4);
//		System.out.println("�û�4��˿������"+res);
//	}
	
}
