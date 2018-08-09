package blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import blog.entity.Link;

/**
 * @author Administrator
 * 友情链接Dao类
 */
@Repository("linkDao")
public interface LinkDao {
	/**
	 * 添加友情链接
	 * @param link
	 * @return
	 */
	public Integer addLink(Link link);
	/**
	 * 删除友情链接
	 * @param id
	 * @return
	 */
	public Integer deleteLink(Integer id);
	/**
	 * 根据id查询链接
	 * @param id
	 * @return
	 */
	public Link findById(Integer id);
	/**
	 * 更新友情链接
	 * @param link
	 * @return
	 */
	public Integer updateLink(Link link);
	/**
	 * 获取所有友情链接
	 * @return
	 */
	public List<Link> listLink();
	/**
	 * 获取友情链接的总数
	 * @return
	 */
	public Long getTotal();
}
