package blog.entity;

/**
 * @author Administrator
 * ����ʵ����
 */
public class Comment {
	/**
	 * ������id
	 */
	private Integer id;
	/**
	 * ��������
	 */
	private String content;
	/**
	 * ��������
	 */
	private String commentDate;
	/**
	 * �����û�id
	 */
	private Integer userid;
	/**
	 * �����۵�����id
	 */
	private Integer articleid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getArticleid() {
		return articleid;
	}
	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", commentDate=" + commentDate + ", userid=" + userid
				+ ", articleid=" + articleid + "]";
	}
	
}