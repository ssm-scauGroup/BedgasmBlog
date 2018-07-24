package blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * 格式化时间
 */
public class FormatDate {
	
	/**
	 * 类似: 2018-07-23 10:10:13 与数据库now()一致
	 * @return
	 */
	public static String formatDate(){
		Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String dateString = formatter.format(currentTime);   
	    return dateString;
	}

}
