package blog.entity;

/**
 * @author Administrator
 * 黑名单实体类
 */
public class BlackList {
	
	/**
	 * id 作为标识 无特殊意义
	 */
	private Integer id;
	
	/**
	 * 被拉黑的用户名
	 */
	private String username;
	
	/**
	 * 被拉黑的用户id
	 */
	private Integer userid;
	
	/**
	 * 封用户原因
	 */
	private String msg;
	
	/**
	 * 该记录的状态 0为已经失效，1为还没失效
	 */
	private Integer msgstatus;
	
	/**
	 * 拉黑日期
	 */
	private String blockDate;
	
	/**
	 * 解封日期
	 */
	private String deblockDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getMsgstatus() {
		return msgstatus;
	}

	public void setMsgstatus(Integer msgstatus) {
		this.msgstatus = msgstatus;
	}

	public String getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(String blockDate) {
		this.blockDate = blockDate;
	}

	public String getDeblockDate() {
		return deblockDate;
	}

	public void setDeblockDate(String deblockDate) {
		this.deblockDate = deblockDate;
	}

	@Override
	public String toString() {
		return "BlackList [id=" + id + ", username=" + username + ", userid=" + userid + ", msg=" + msg + ", msgstatus="
				+ msgstatus + ", blockDate=" + blockDate + ", deblockDate=" + deblockDate + "]";
	}
	
}
