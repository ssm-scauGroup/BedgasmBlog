package blog.entity;

/**
 * @author Administrator
 * 链接实体类
 */
public class Link {
	
	/**
	 * 链接id
	 */
	private Integer id;
	/**
	 * 链接名字
	 */
	private String linkname;
	/**
	 * 链接url
	 */
	private String linkurl;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkname() {
		return linkname;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	
	@Override
	public String toString() {
		return "Link [id=" + id + ", linkname=" + linkname + ", linkurl=" + linkurl + "]";
	}
	
}
