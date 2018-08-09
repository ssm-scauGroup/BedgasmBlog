package blog.dao.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.LinkDao;
import blog.entity.Link;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class LinkDaoTest {
	
	@Resource
	public LinkDao linkDao;
	
//	@Test
//	public void addLink() throws Exception{
//		//Link link = new Link("ronething's playlist","http://music.ronething.top");
//		Link link = new Link("ronething's lab","http://lab.ronething.top");
//		int res = linkDao.addLink(link);
//		System.out.println("res is "+res);
//	}

//	@Test
//	public void findById() throws Exception{
//		Link link = linkDao.findById(1);
//		System.out.println(link);
//	}

//	@Test
//	public void updateLink() throws Exception{
//		Link link = new Link(1,"ronething's blog","https://blog.ronething.top");
//		int res = linkDao.updateLink(link);
//		System.out.println(res);
//	}

//	@Test
//	public void listLink() throws Exception{
//		List<Link> l = linkDao.listLink();
//		for (Link link : l) {
//			System.out.println(link);
//		}
//	}

//	@Test
//	public void getTotal() throws Exception{
//		System.out.println(linkDao.getTotal());
//	}
}
