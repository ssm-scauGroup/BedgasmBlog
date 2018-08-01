package blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * 日期工具类
 */
public class FormatDate {
	
	/**
	 * 日期格式如: 2018-07-23 10:10:13 与mysql now()一样
	 * @return
	 */
	public static String formatDate(){
		Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String dateString = formatter.format(currentTime);
	    return dateString;
	}

}
