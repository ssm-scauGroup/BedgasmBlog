package blog.service;

import java.util.List;
import java.util.Map;
import blog.entity.Article;
import blog.util.PageBean;

public interface ArticleService {
	public List<Article> listArticle(Map<String, Object> map);
	
	public PageBean<Article> listByPage(PageBean<Article> pageBean);

	public Long getTotal(Map<String, Object> map);

	public Integer addArticle(Article article);

	public Integer updateArticle(Article article);

	public Integer deleteArticle(Integer id);

	public Article findById(Integer id);
	
	public List<Article> listByAuthor(Integer author);
	
	public List<Article> listByClickCount(Map<String, Object> map);
}
