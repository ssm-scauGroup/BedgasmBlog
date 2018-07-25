package blog.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import blog.entity.BlogType;

public interface BlogTypeService {
	public Integer addBlogType(BlogType blogType);

	public Integer deleteBlogType(Integer id);

	public BlogType findById(Integer id);

	public Integer updateBlogType(BlogType blogType);

	public List<BlogType> listBlogType(@Param("start") Integer start, @Param("end") Integer end);

	public Long getTotal();
}
