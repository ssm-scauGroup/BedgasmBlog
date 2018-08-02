package blog.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	//增加小时 1小时 传入 1 3天传入24*3 7天 传入 24*7 一个月传入24*30 一年传入 24*365 永久的话 传入 24*365*1000 1000年
	public static String formatDate(Integer hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

}
