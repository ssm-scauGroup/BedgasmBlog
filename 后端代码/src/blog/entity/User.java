package blog.entity;

/**
 * @author Administrator
 * 用户实体类
 */
public class User {
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户昵称(必填，默认和`username`相同)
	 */
	private String nickname;
	/**
	 * 用户邮箱(必填，可考虑用于邮箱注册激活等)
	 */
	private String email;
	/**
	 * 用户个性签名
	 */
	private String signature;
	/**
	 * 用户头像路径
	 */
	private String profile;
	/**
	 * 用户角色id(0为管理员，1为普通用户)
	 */
	private Integer role;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", signature=" + signature + ", profile=" + profile + ", role=" + role + "]";
	}
	
}
