package blog.service;

import java.util.List;

import blog.entity.Link;

public interface LinkService {
	public Integer addLink(Link link);

	public Integer deleteLink(Integer id);

	public Link findById(Integer id);

	public Integer updateLink(Link link);

	public List<Link> listLink();

	public Long getTotal();
}
