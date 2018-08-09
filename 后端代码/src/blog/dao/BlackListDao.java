package blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import blog.entity.BlackList;

/**
 * @author Administrator
 * 黑名单Dao类
 */
@Repository("blackListDao")
public interface BlackListDao {
	
	/**
	 * 添加一条黑名单记录
	 * @param blackList
	 * @return
	 */
	public Integer addBlackList(BlackList blackList);

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
	public Integer updateBlackList(BlackList blackList);
	
	/**
	 * 根据相关条件查询黑名单记录
	 * @param map
	 * @return
	 */
	public List<BlackList> listBlackList(Map<String, Object> map);
	
	
}
