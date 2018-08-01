package blog.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.dao.ArticleDao;
import blog.entity.Article;
import blog.util.FormatDate;

/**
 * @author Administrator
 * 文章测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans.xml")
public class ArticleDaoTest {
	
	@Resource
	private ArticleDao articleDao;
	
//	@Test
//    public void addArticle() throws Exception {
//        Article article = new Article();
//        article.setAuthor(1);
//        article.setBlogtypeid(11);
//        article.setContent("今天是个好日子");
//        article.setSummary("这是摘要");
//        article.setTags("这是标签,分号隔开");
//        article.setTitle("这是标题");
//        for (int i = 0; i < 10; i++) {
//        	int result = articleDao.addArticle(article);
//        	System.out.println("result is "+result);
//		}
//        
//    }
	
//	@Test
//	public void getById() throws Exception{
//		Article article = articleDao.findById(4);
//		System.out.println(article);
//	}
	
//	@Test
//	public void getBlogByTypeId(){
//		Integer total = articleDao.getBlogByTypeId(1);
//		System.out.println("total is "+total);
//	}
	
//	@Test
//	public void deleteArticle(){
//		Integer res = articleDao.deleteArticle(1);
//		System.out.println("result is "+res);
//	}
	
//	@Test
//	public void updateArticle() throws Exception{
//		Article article = new Article(4,"helladfasdfo1","今天是个好adsfadf日子",FormatDate.formatDate(), null, null, "hhhhhhhhhhhhh","demo",1);
//		Integer res = articleDao.updateArticle(article);
//		System.out.println("result is "+res);
//	}
	
//	@Test
//	public void listByPage(){
//		Integer start = 0;
//		Integer end = 2;
//		List<Article> articles = articleDao.listByPage(start, end);
//		for (Article article : articles) {
//			System.out.println(article);
//		}
//	}
}
