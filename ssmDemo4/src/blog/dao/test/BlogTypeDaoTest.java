package blog.dao.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.BlogTypeDao;
import blog.entity.BlogType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class BlogTypeDaoTest {
	
	@Resource
	private BlogTypeDao blogTypeDao;
	
//	@Test
//	public void addBlogType() throws Exception{
//		BlogType blogType = new BlogType("这是一个测试",0);
//		int res = blogTypeDao.addBlogType(blogType);
//		System.out.println(res);
//	}
	
//	@Test
//	public void findById() throws Exception{
//		BlogType blogType = blogTypeDao.findById(1);
//		System.out.println(blogType);
//	}
	
//	@Test
//	public void updateBlogType() throws Exception{
//		BlogType blogType = new BlogType(1,0,"修改类别");
//		int res = blogTypeDao.updateBlogType(blogType);
//		System.out.println("res is "+res);
//	}
	
//	@Test
//	public void getTotal() throws Exception{
//		Long res = blogTypeDao.getTotal();
//		System.out.println("res is "+res);
//	}
	
//	@Test
//	public void listBlogType() throws Exception{
//		int start = 0;
//		int end = 3;
//		List<BlogType> blogTypeList = blogTypeDao.listBlogType(start,end);
//		for (BlogType blogType : blogTypeList) {
//			System.out.println(blogType);
//		}
//	}
	
//	@Test
//	public void deleteBlogType() throws Exception{
//		int res = blogTypeDao.deleteBlogType(2);
//		System.out.println("res is "+res);
//	}
}
