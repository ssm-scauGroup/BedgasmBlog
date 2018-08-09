package blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.MediaDao;
import blog.entity.Media;
import blog.service.MediaService;

@Service("mediaService")
public class MediaServiceImpl implements MediaService {
	@Autowired
	private MediaDao mdo;
	@Override
	public Integer addMedia(Media media) {
		// TODO Auto-generated method stub
		return mdo.addMedia(media);
	}

	@Override
	public Integer deleteMedia(Integer id) {
		// TODO Auto-generated method stub
		return mdo.deleteMedia(id);
	}

	@Override
	public Media findById(Integer id) {
		// TODO Auto-generated method stub
		return mdo.findById(id);
	}

	@Override
	public Integer updateMedia(Media media) {
		// TODO Auto-generated method stub
		return mdo.updateMedia(media);
	}

	@Override
	public List<Media> listByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return mdo.listByUserId(userid);
	}

	@Override
	public Long getTotalByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return mdo.getTotalByUserId(userid);
	}

}
