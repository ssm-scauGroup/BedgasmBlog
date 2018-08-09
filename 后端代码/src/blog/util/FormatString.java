package blog.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Random;

public class FormatString {
	
	private static Base64.Encoder encoder = Base64.getEncoder();
	
	/**
	 * String 转 base64
	 * @param string
	 * @return
	 */
	public static String strToBase64(String string){
		
		byte[] textByte = null;
		try {
			textByte = string.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String encodedText = encoder.encodeToString(textByte);
	
		return encodedText;
	}
	
	/**
	 * 测试
	 * @param args
	 */
//	public static void main(String[] args) {
//		//随机六位数字
//		Random random=new Random();
//		String pinString = random.nextInt(100000000)+"";
//		System.out.println(pinString);
//		System.out.println(FormatString.strToBase64(pinString));
//	}

	
	
	
}
