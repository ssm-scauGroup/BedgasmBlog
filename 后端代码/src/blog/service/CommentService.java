package blog.service;

import java.util.List;
import java.util.Map;
import blog.entity.*;

public interface CommentService {
	public Integer addComment(Comment comment);

	public Integer deleteComment(Integer id);

	public Comment findById(Integer id);

	public Integer updateComment(Comment comment);

	public List<Comment> listComment(Map<String, Object> map);

	public Long getTotalByArticleId(Integer articleid);

	public Long getTotalByUserId(Integer userid);
}
