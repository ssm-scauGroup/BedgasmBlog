package blog.entity;

/**
 * @author Administrator
 * 订阅实体类
 */
public class Star {
	
	/**
	 * 订阅者id
	 */
	private Integer subscriber;
	/**
	 * 被订阅者id
	 */
	private Integer subscribee;

	public Integer getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Integer subscriber) {
		this.subscriber = subscriber;
	}
	public Integer getSubscribee() {
		return subscribee;
	}
	public void setSubscribee(Integer subscribee) {
		this.subscribee = subscribee;
	}
	@Override
	public String toString() {
		return "Star [subscriber=" + subscriber + ", subscribee=" + subscribee + "]";
	}
	
}
