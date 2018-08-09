package blog.service;

import java.util.List;
import java.util.Map;

import blog.entity.BlackList;

public interface BlackListService {
	
	/**
	 * 添加一条黑名单记录
	 * @param blackList
	 * @return
	 */
	public boolean addBlackList(BlackList blackList);

	/**
	 * 通过id查询黑名单记录
	 * @param id
	 * @return
	 */
	public BlackList findById(Integer id);
	
	/**
	 * 更新黑名单记录
	 * @param blackList
	 * @return
	 */
	public boolean updateBlackList(BlackList blackList);
	
	/**
	 * 根据相关条件查询黑名单记录
	 * @param map
	 * @return
	 */
	public List<BlackList> listBlackList(Map<String, Object> map);

}
