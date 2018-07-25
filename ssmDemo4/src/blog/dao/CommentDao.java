package blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import blog.entity.Comment;

/**
 * @author Administrator
 * 评论Dao类
 */
@Repository("commentDao")
public interface CommentDao {
	/**
	 * 增加评论
	 * @param comment
	 * @return
	 */
	public Integer addComment(Comment comment);
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	public Integer deleteComment(Integer id);
	/**
	 * 通过id获取评论
	 * @param id
	 * @return
	 */
	public Comment findById(Integer id);
	/**
	 * 更新评论
	 * @param comment
	 * @return
	 */
	public Integer updateComment(Comment comment);
	
	/**
	 * 获取某篇文章的所有评论
	 * @param articleid
	 * @return
	 */
	public List<Comment> listByArticleId(Integer articleid);
	
	/**
	 * 某个人的所有评论
	 * @param userid
	 * @return
	 */
	public List<Comment> listByUserId(Integer userid);
	
	/**
	 * 获取某篇文章的评论总数
	 * @param articleid
	 * @return
	 */
	public Long getTotalByArticleId(Integer articleid);
	
	/**
	 * 获取某用户评论的总数
	 * @param userid
	 * @return
	 */
	public Long getTotalByUserId(Integer userid);
}
