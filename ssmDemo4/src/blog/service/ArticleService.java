package blog.service;

import java.util.List;
import java.util.Map;
import blog.entity.Article;

public interface ArticleService {
	public List<Article> listArticle(Map<String, Object> map);

	public Long getTotal(Map<String, Object> map);

	public Integer getBlogByTypeId(Integer blogtypeid);

	public Integer addArticle(Article article);

	public Integer updateArticle(Article article);

	public Integer deleteArticle(Integer id);

	public Article findById(Integer id);
}
