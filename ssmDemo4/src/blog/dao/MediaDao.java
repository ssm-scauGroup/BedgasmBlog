package blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import blog.entity.Media;

@Repository("mediaDao")
public interface MediaDao {
	
	/**
	 * 添加媒体
	 * @param media
	 * @return
	 */
	public Integer addMedia(Media media);
	
	/**
	 * 删除媒体
	 * @param id
	 * @return
	 */
	public Integer deleteMedia(Integer id);
	
	/**
	 * 根据id查询media
	 * @param id
	 * @return
	 */
	public Media findById(Integer id);
	
	/**
	 * 更新媒体
	 * @param media
	 * @return
	 */
	public Integer updateMedia(Media media);
	
	/**
	 * 根据用户id列出该用户的所有媒体
	 * @param userid
	 * @return
	 */
	public List<Media> listByUserId(Integer userid);
	
	/**
	 * 根据用户id获取该用户上传的媒体数
	 * @param userid
	 * @return
	 */
	public Long getTotalByUserId(Integer userid);

}
