package blog.entity;

/**
 * @author Administrator
 * ý��ʵ����
 */
public class Media {
	
	/**
	 * ý��id(Ĭ������)
	 */
	private Integer id;
	/**
	 * ý��·��
	 */
	private String imagepath;
	/**
	 * �ϴ�ý����û�id
	 */
	private Integer userid;
	
	/**
	 * �ϴ�����
	 */
	private String releaseDate;
	
	public Media(){
		
	}
		
	public Media(String imagepath, Integer userid) {
		super();
		this.imagepath = imagepath;
		this.userid = userid;
	}

	public Media(Integer id, String imagepath, Integer userid, String releaseDate) {
		super();
		this.id = id;
		this.imagepath = imagepath;
		this.userid = userid;
		this.releaseDate = releaseDate;
	}
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
