package blog.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.entity.BlogType;
import blog.service.*;
import blog.util.PageBean;
import blog.dao.*;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService{
	
	@Autowired
    private BlogTypeDao bdo;
	
	@Override
	public Integer addBlogType(BlogType blogType) {
		// TODO Auto-generated method stub
		return bdo.addBlogType(blogType);
	}

	@Override
	public Integer deleteBlogType(Integer id) {
		// TODO Auto-generated method stub
		return bdo.deleteBlogType(id);
	}

	@Override
	public BlogType findById(Integer id) {
		// TODO Auto-generated method stub
		return bdo.findById(id);
	}

	@Override
	public Integer updateBlogType(BlogType blogType) {
		// TODO Auto-generated method stub
		return bdo.updateBlogType(blogType);
	}

	@Override
	public Long getTotal() {
		// TODO Auto-generated method stub
		return bdo.getTotal();
	}

	@Override
	public PageBean<BlogType> listByPage(PageBean<BlogType> pageBean) {
		// TODO Auto-generated method stub
		pageBean.setResult(bdo.listByPage(pageBean.getStart(), pageBean.getEnd()));
		return pageBean;
	}

	@Override
	public List<BlogType> listBlogType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bdo.listBlogType(map);
	}
	

}
