package blog.entity;

/**
 * @author Administrator
 * 文章实体类
 */
public class Article {
	/**
	 * 文章id
	 */
	private Integer id;
	/**
	 * 文章题目
	 */
	private String title;
	/**
	 * 文章摘要
	 */
	private String summary;
	/**
	 * 文章发布日期
	 */
	private String releaseDate;
	/**
	 * 文章点击数
	 */
	private Integer clickCount;
	/**
	 * 文章被评论数
	 */
	private Integer replyCount;
	/**
	 * 文章正文
	 */
	private String content;
	/**
	 * 文章标签(要求','逗号隔开 以便于格式化)
	 */
	private String tags;
	/**
	 * 文章类别id
	 */
	private Integer blogtypeid;
	
	/**
	 * 作者id
	 */
	private Integer author;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Integer getBlogtypeid() {
		return blogtypeid;
	}
	public void setBlogtypeid(Integer blogtypeid) {
		this.blogtypeid = blogtypeid;
	}

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", summary=" + summary + ", releaseDate=" + releaseDate
				+ ", clickCount=" + clickCount + ", replyCount=" + replyCount + ", content=" + content + ", tags="
				+ tags + ", blogtypeid=" + blogtypeid + ", author=" + author + "]";
	}
	
}
