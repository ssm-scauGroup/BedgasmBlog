package blog.entity;

/**
 * @author Administrator
 * 媒体实体类
 */
public class Media {
	
	/**
	 * 媒体id(默认自增)
	 */
	private Integer id;
	/**
	 * 媒体路径
	 */
	private String imagepath;
	/**
	 * 上传媒体的用户id
	 */
	private Integer userid;
	
	/**
	 * 上传日期
	 */
	private String releaseDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@Override
	public String toString() {
		return "Media [id=" + id + ", imagepath=" + imagepath + ", userid=" + userid + "]";
	}

}
