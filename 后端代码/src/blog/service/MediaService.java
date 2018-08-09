package blog.service;

import java.util.List;

import blog.entity.Media;

public interface MediaService {
	public Integer addMedia(Media media);

	public Integer deleteMedia(Integer id);

	public Media findById(Integer id);

	public Integer updateMedia(Media media);

	public List<Media> listByUserId(Integer userid);

	public Long getTotalByUserId(Integer userid);

}
