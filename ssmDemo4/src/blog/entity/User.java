package blog.entity;

/**
 * @author Administrator
 * �û�ʵ����
 */
public class User {
	/**
	 * �û�id
	 */
	private Integer id;
	/**
	 * �û���
	 */
	private String username;
	/**
	 * �û�����
	 */
	private String password;
	/**
	 * �û��ǳ�(���Ĭ�Ϻ�`username`��ͬ)
	 */
	private String nickname;
	/**
	 * �û�����(����ɿ�����������ע�ἤ���)
	 */
	private String email;
	/**
	 * �û�����ǩ��
	 */
	private String signature;
	/**
	 * �û�ͷ��·��
	 */
	private String profile;
	/**
	 * �û���ɫid(0Ϊ����Ա��1Ϊ��ͨ�û�)
	 */
	private Integer role;
	
	public User() {
		
	}
	
	public User(String username, String password, String nickname, String email, String signature, String profile,
			Integer role) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.signature = signature;
		this.profile = profile;
		this.role = role;
	}
	
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
