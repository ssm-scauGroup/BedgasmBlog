package blog.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * 单个文件
 */
public class SingleFile {
	
	private Integer userid;
	private MultipartFile singleFile;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public MultipartFile getSingleFile() {
		return singleFile;
	}
	public void setSingleFile(MultipartFile singleFile) {
		this.singleFile = singleFile;
	}

}
