package blog.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.MediaDao;
import blog.entity.Media;
import blog.util.FormatDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class MediaDaoTest {

	@Resource
	private MediaDao mediaDao;
	
//	@Test
//	public void addMedia() throws Exception{
//		Media media = new Media("css/test.txt",1);
//		int res = mediaDao.addMedia(media);
//		System.out.println(res);
//	}

//	@Test
//	public void deleteMedia() throws Exception{
//		int res = mediaDao.deleteMedia(1);
//		System.out.println(res);
//	}

//	@Test
//	public void findById() throws Exception{
//		Media media = mediaDao.findById(1);
//		System.out.println(media);
//	}

//	@Test
//	public void updateMedia() throws Exception{
//		Media media = new Media(1,"js/test.txt",2,FormatDate.formatDate());
//		int res = mediaDao.updateMedia(media);
//		System.out.println(res);
//	}
	
//	@Test
//	public void listByUserId() throws Exception{
//		List<Media> medias = mediaDao.listByUserId(2);
//		for (Media media : medias) {
//			System.out.println(media);
//		}
//	}
	
//	@Test
//	public void getTotalByUserId() throws Exception{
//		Long total = mediaDao.getTotalByUserId(2);
//		System.out.println(total);
//	}
}
