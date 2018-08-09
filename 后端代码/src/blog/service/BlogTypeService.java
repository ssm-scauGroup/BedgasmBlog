package blog.service;

import java.util.List;
import java.util.Map;

import blog.entity.BlogType;
import blog.util.PageBean;

public interface BlogTypeService {
	public Integer addBlogType(BlogType blogType);

	public Integer deleteBlogType(Integer id);

	public BlogType findById(Integer id);

	public Integer updateBlogType(BlogType blogType);
	
	public PageBean<BlogType> listByPage(PageBean<BlogType> pageBean);
	
	public List<BlogType> listBlogType(Map<String, Object> map);

	public Long getTotal();
}
