package blog.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	/**
	 * 向response对象写入数据
	 * 静态方法外部可以直接访问不需要实例化
	 * @param response
	 * @param obj
	 * @throws Exception
	 */
	public static void write(HttpServletResponse response,Object obj) throws Exception{
		response.setContentType("application/json;charset=utf-8");
		//拿到response的文本输出流对象
		PrintWriter out = response.getWriter();
		//将我们需要返回的json对象写入response中
		out.println(obj.toString());
		//刷新输出流并且关闭
		out.flush();
		out.close();
	}
}
