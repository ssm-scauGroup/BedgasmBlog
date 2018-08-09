package blog.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;
 
public class SendMail {
 
	private static String from = "13414113153@163.com"; // 发件人邮箱地址
	private static String user = "13414113153@163.com"; // 发件人称号，同邮箱地址
	private static String password = "Rns475TgNzoWzp5s"; // 发件人邮箱客户端授权码
	private static String nickname = "bedgasmblog";
 
	/**
	 * 
	 * @param to
	 * @param text
	 * @param title
	 */
	/* 发送验证信息的邮件 */
	public static boolean sendMail(String to, String text, String title) {
		
		Properties props = new Properties();
		
		props.setProperty("mail.smtp.host", "smtp.163.com"); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", "smtp.163.com"); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
		
		 // 如果使用ssl，则去掉使用25端口的配置，进行如下配置, 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.port", "465");

		
		Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
																
		session.setDebug(true); // 用（你可以在控制台（console)上看到发送邮件的过程）
		MimeMessage message = new MimeMessage(session);  // 用session为参数定义消息对象
		try {
			
			message.setFrom(new InternetAddress(from,nickname,"UTF-8")); // 加载发件人地址
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 加载收件人地址
			
			message.setSubject(title); // 加载标题
			
			Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			
			BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
			
			contentPart.setContent(text, "text/html;charset=utf-8");
			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
			message.saveChanges(); // 保存变化
			
			Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
			
			transport.connect("smtp.163.com", user, password); // 把邮件发送出去
			
			transport.sendMessage(message, message.getAllRecipients());
			
			transport.close();
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
 
	/**
	 * 测试发送
	 * @param args
	 */
//	public static void main(String[] args) { 
//		Random random=new Random();
//		String pinString = random.nextInt(1000000)+"";
//		System.out.println(pinString);
////		String sendString = "您在<a href='http://bedgasmblog.cn'>bedgasmblog</a>网站通过邮件发送重置密码，请输入以下验证码"+
////		"<br/><h2>"+pinString+"</h2>"+"<br/><br/><br/>如果不是您本人操作，请忽略此邮件。<br/>该邮件为系统邮件，请勿回复。";
////		System.out.println(sendString);
//		String sendString = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><title>MAIL</title><link rel=\"stylesheet\" href=\"https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css\"></head><style>.logo {padding-top: 22px;overflow: hidden;}footer {width: 100%;bottom: 0px;padding: 0;font-size: 12px;font-weight: 500;text-transform: uppercase;letter-spacing: 1px;color: #717171;text-align: center;border-top: 1px solid #CECECE;margin-top: 50px;}footer .footer-bottom {padding: 10px 0 40px;}.footer2 {position: absolute;bottom: 0;}</style><body><div align=\"center\"><a href='http://bedgasmblog.cn' align=\"middle\"><img src=\"http://bedgasmblog.cn/bedgasmblog.png\" align=\"middle\" /></a></div><h4 align=\"center\">您在bedgasmblog通过邮件发送重置密码，请点击以下链接重置您的密码</h4><br/><h1 align=\"center\">"+pinString+"</h1><br/><p align=\"center\"><br/>如果不是您本人操作，请忽略此邮件。<br/>该邮件为系统邮件，请勿回复。</p><footer class=\"footer\"><div class=\"footer-bottom\"><h5>感谢使用Bedgasm Blog</h5><i class=\"fa fa-copyright\"></i> Copyright 2018 Bedgasm Blog. All Rights Reserved.<br></div></footer></body></html>";
//		boolean flag = SendMail.sendMail("178965100@qq.com",sendString,"重置您的账户密码");
//		if (flag) {
//			System.out.println("发送成功");
//		}
//		else {
//			System.out.println("发送失败");
//		}
//	}

}
