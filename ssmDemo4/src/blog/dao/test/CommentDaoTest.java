package blog.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.CommentDao;
import blog.entity.Comment;
import blog.util.FormatDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class CommentDaoTest {
	
	@Resource
	private CommentDao commentDao;
	
//	@Test
//	public void addComment(){
//		Comment c = new Comment("test",2,3);
//		int res = commentDao.addComment(c);
//		System.out.println(res);
//	}

//	@Test
//	public void deleteComment(){
//		Integer res = commentDao.deleteComment(6);
//		System.out.println("result is "+res);
//	}
	
//	@Test
//	public void findById(){
//		Comment c = commentDao.findById(7);
//		System.out.println(c);
//	}
//	@Test
//	public void updateComment(){
//		Comment c = new Comment(11,"qwe",FormatDate.formatDate(), 2, 6);
//		int res=commentDao.updateComment(c);
//		System.out.println(res);
//	}
//	@Test
//	public void listByArticleId(){
//		List<Comment> c=commentDao.listByArticleId(6);
//		System.out.println(c);
//}
//	@Test
//	public void listByArticleId(){
//		List<Comment> c=commentDao.listByArticleId(2);
//		System.out.println(c);
//}
	
//	@Test
//	public void listByUserId() throws Exception{
//		List<Comment> c = commentDao.listByUserId(2);
//		System.out.println(c);
//	}
	
//	@Test
//	public void getTotalByArticleId() throws Exception{
//		Long res=commentDao.getTotalByArticleId(2);
//		System.out.println(res);
//	}
	
//	@Test
//	public void getTotalByUserId() throws Exception{
//		Long res=commentDao.getTotalByUserId(1);
//		System.out.println(res);
//	}
}
