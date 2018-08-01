package blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import blog.entity.Comment;

/**
 * @author Administrator
 * 璇勮Dao绫�
 */
@Repository("commentDao")
public interface CommentDao {
	/**
	 * 澧炲姞璇勮
	 * @param comment
	 * @return
	 */
	public Integer addComment(Comment comment);
	/**
	 * 鍒犻櫎璇勮
	 * @param id
	 * @return
	 */
	public Integer deleteComment(Integer id);
	/**
	 * 閫氳繃id鑾峰彇璇勮
	 * @param id
	 * @return
	 */
	public Comment findById(Integer id);
	/**
	 * 鏇存柊璇勮
	 * @param comment
	 * @return
	 */
	public Integer updateComment(Comment comment);
	
	/**
	 * 鏍规嵁鏉′欢鏌ヨ璇勮
	 * @param articleid
	 * @return
	 */
	public List<Comment> listComment(Map<String, Object> map);
	
	/**
	 * 鑾峰彇鏌愮瘒鏂囩珷鐨勮瘎璁烘�绘暟
	 * @param articleid
	 * @return
	 */
	public Long getTotalByArticleId(Integer articleid);
	
	/**
	 * 鑾峰彇鏌愮敤鎴疯瘎璁虹殑鎬绘暟
	 * @param userid
	 * @return
	 */
	public Long getTotalByUserId(Integer userid);
}
