package blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.LinkDao;
import blog.entity.Link;
import blog.service.LinkService;

@Service("linkService")
public class LinkServiceImpl implements LinkService {
	@Autowired
	private LinkDao ldo;
	@Override
	public Integer addLink(Link link) {
		// TODO Auto-generated method stub
		return ldo.addLink(link);
	}

	@Override
	public Integer deleteLink(Integer id) {
		// TODO Auto-generated method stub
		return ldo.deleteLink(id);
	}

	@Override
	public Link findById(Integer id) {
		// TODO Auto-generated method stub
		return ldo.findById(id);
	}

	@Override
	public Integer updateLink(Link link) {
		// TODO Auto-generated method stub
		return ldo.updateLink(link);
	}

	@Override
	public List<Link> listLink() {
		// TODO Auto-generated method stub
		return ldo.listLink();
	}

	@Override
	public Long getTotal() {
		// TODO Auto-generated method stub
		return ldo.getTotal();
	}

}
