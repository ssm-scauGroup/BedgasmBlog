package blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import blog.entity.Comment;

/**
 * @author Administrator
 * ����Dao��
 */
@Repository("commentDao")
public interface CommentDao {
	/**
	 * ��������
	 * @param comment
	 * @return
	 */
	public Integer addComment(Comment comment);
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public Integer deleteComment(Integer id);
	/**
	 * ͨ��id��ȡ����
	 * @param id
	 * @return
	 */
	public Comment findById(Integer id);
	/**
	 * ��������
	 * @param comment
	 * @return
	 */
	public Integer updateComment(Comment comment);
	
	/**
	 * ��ȡĳƪ���µ���������
	 * @param articleid
	 * @return
	 */
	public List<Comment> listByArticleId(Integer articleid);
	
	/**
	 * ĳ���˵���������
	 * @param userid
	 * @return
	 */
	public List<Comment> listByUserId(Integer userid);
	
	/**
	 * ��ȡĳƪ���µ���������
	 * @param articleid
	 * @return
	 */
	public Long getTotalByArticleId(Integer articleid);
	
	/**
	 * ��ȡĳ�û����۵�����
	 * @param userid
	 * @return
	 */
	public Long getTotalByUserId(Integer userid);
}
