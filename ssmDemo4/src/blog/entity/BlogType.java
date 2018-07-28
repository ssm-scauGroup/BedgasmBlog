package blog.entity;

/**
 * @author Administrator
 * 文章分类实体类
 */
public class BlogType {
	/**
	 * 文章分类id
	 */
	private Integer id;
	/**
	 * 文章分类名称
	 */
	private String typename;
	/**
	 * 该类别下的文章数
	 */
	private Integer typecount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public Integer getTypecount() {
		return typecount;
	}
	public void setTypecount(Integer typecount) {
		this.typecount = typecount;
	}
	
	@Override
	public String toString() {
		return "BlogType [id=" + id + ", typename=" + typename + ", typecount=" + typecount + "]";
	}
	
}
