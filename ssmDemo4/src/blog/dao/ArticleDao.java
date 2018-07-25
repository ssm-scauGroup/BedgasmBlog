package blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import blog.entity.Article;

/**
 * @author Administrator
 * 文章Dao类
 */
@Repository("articleDao")
public interface ArticleDao {
	/**
	 * 查询所有文章
	 * @param map
	 * @return
	 */
	public List<Article> listArticle(Map<String,Object> map);
	/**
	 * 获取文章数目。
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	/**
	 * 查询某个类别下的文章数
	 * @param blogtypeid
	 * @return
	 */
	public Integer getBlogByTypeId(Integer blogtypeid);
	/**
	 * 添加文章
	 * @param article
	 * @return
	 */
	public Integer addArticle(Article article);
	/**
	 * 更新文章
	 * @param article
	 * @return
	 */
	public Integer updateArticle(Article article);
	/**
	 * 删除文章
	 * @param id
	 * @return
	 */
	public Integer deleteArticle(Integer id);
	/**
	 * 通过id获取文章
	 * @param id
	 * @return
	 */
	public Article findById(Integer id);
	
}
