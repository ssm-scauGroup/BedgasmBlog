package blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import blog.entity.BlogType;

/**
 * @author Administrator
 * 文章类别Dao类
 */
@Repository("blogTypeDao")
public interface BlogTypeDao {

	/**
	 * 添加类别
	 * @param blogType
	 * @return
	 */
	public Integer addBlogType(BlogType blogType);
	/**
	 * 删除类别
	 * @param id
	 * @return
	 */
	public Integer deleteBlogType(Integer id);
	/**
	 * 通过id查询类别
	 * @param id
	 * @return
	 */
	public BlogType findById(Integer id);
	/**
	 * 更新类别
	 * @param blogType
	 * @return
	 */
	public Integer updateBlogType(BlogType blogType);
	/**
	 * 查询第几页
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BlogType> listBlogType(@Param("start") Integer start,
			@Param("end") Integer end);
	/**
	 * 类别总数
	 * @return
	 */
	public Long getTotal();
}
