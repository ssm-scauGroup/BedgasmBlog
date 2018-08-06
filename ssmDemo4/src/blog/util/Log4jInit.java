package blog.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author Administrator
 * 初始化 log4j配置 log4j的配置文件名字 不能是默认的 log4j.properites 不然他会在文件夹根目录也生成日志文件
 */
public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Log4jInit() {
    }

    public void init(ServletConfig config) throws ServletException {
        String prefix = config.getServletContext().getRealPath("/");
        //System.out.println("prefix is "+prefix);
        String file = config.getInitParameter("log4j");
        //System.out.println("file is "+file);
        String filePath = prefix + file;
        Properties props = new Properties();
        try {
            FileInputStream istream = new FileInputStream(filePath);
            props.load(istream);
            istream.close();
            props.setProperty("bloglog",prefix);
            PropertyConfigurator.configure(props);//装入log4j配置信息
        } catch (IOException e) {
            toPrint("Could not read configuration file [" + filePath + "].");
            toPrint("Ignoring configuration file [" + filePath + "].");
            return;
        }
    }

    public static void toPrint(String content) {
        System.out.println(content);
    }
}