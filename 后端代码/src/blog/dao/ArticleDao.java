package blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import blog.entity.Article;

/**
 * @author Administrator
 * 文章Dao类
 */
@Repository("articleDao")
public interface ArticleDao {
	/**
	 * 根据相关条件查询所有文章
	 * @param map
	 * @return
	 */
	public List<Article> listArticle(Map<String,Object> map);
	
	
	/**
	 * 分页查询
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Article> listByPage(@Param("start") Integer start,
			@Param("end") Integer end);
	
	/**
	 * 获取文章数目。
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
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
	
	/**
	 * 返回某个作者的所有文章
	 * @param author
	 * @return
	 */
	public List<Article> listByAuthor(Integer author);
	
	/**
	 * 根据阅读次数(热度)列出文章
	 * @param map
	 * @return
	 */
	public List<Article> listByClickCount(Map<String, Object> map);
}
