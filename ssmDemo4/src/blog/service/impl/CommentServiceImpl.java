package blog.service.impl;

import java.util.List;

import blog.entity.Comment;
import blog.service.CommentService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import blog.dao.*;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao cdo;
	@Override
	public Integer addComment(Comment comment) {
		// TODO Auto-generated method stub
		return cdo.addComment(comment);
	}

	@Override
	public Integer deleteComment(Integer id) {
		// TODO Auto-generated method stub
		return cdo.deleteComment(id);
	}

	@Override
	public Comment findById(Integer id) {
		// TODO Auto-generated method stub
		return cdo.findById(id);
	}

	@Override
	public Integer updateComment(Comment comment) {
		// TODO Auto-generated method stub
		return cdo.updateComment(comment);
	}

	@Override
	public Long getTotalByArticleId(Integer articleid) {
		// TODO Auto-generated method stub
		return cdo.getTotalByArticleId(articleid);
	}

	@Override
	public Long getTotalByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return cdo.getTotalByUserId(userid);
	}

	@Override
	public List<Comment> listComment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cdo.listComment(map);
	}

}
