package com.util;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MyUtil {
	/**
	 * 获得一个以时间字符串为标准的ID，固定长度是17位
	 */
	public static String getStringID(){
		String id=null;
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		id=sdf.format(date);
		return id;
	}
}
