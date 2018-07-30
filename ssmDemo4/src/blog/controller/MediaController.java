package blog.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import blog.entity.Media;
import blog.service.MediaService;
import blog.util.RemoveFile;
import blog.util.SingleFile;

@Controller
@RequestMapping("/media")
public class MediaController {
	
	@Autowired
	private MediaService mediaService;
	
	/**
	 * 列出某位用户的媒体库
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listByPage(@RequestParam(value="userid",required=false) String userid) {
		
		JSONObject jsonObject = new JSONObject();
		
		System.out.println(userid);
		
		if(userid==null || userid.equals("")){
			jsonObject.put("success", false);
			jsonObject.put("reason","no userid");
			return jsonObject.toString();
		}
		
		List<Media> medias = mediaService.listByUserId(Integer.parseInt(userid));
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(medias,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = medias.size();
		
		jsonObject.put("success",true);
		
		jsonObject.put("total", total);
		
		jsonObject.put("medias", array);
		
		return jsonObject.toString();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String uploadMedia(SingleFile singleFile,HttpServletRequest request) {
		
		//父文件夹 这里存放文件
		String parentPath = "bedgasmBlogContents";
		
		String realpath = request.getServletContext().getRealPath(parentPath);
		String fileName = singleFile.getSingleFile().getOriginalFilename();
		
		//取出文件后缀名 生成uuid 重命名文件
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uuidFileName = UUID.randomUUID().toString()+"."+suffix;
		
		System.out.println("uuidFileName is "+uuidFileName);
		
		File targetFile = new File(realpath, uuidFileName);
		
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		
		JSONObject jsonObject = new JSONObject();
		
		//上传
		try {
			singleFile.getSingleFile().transferTo(targetFile);
			//数据写进数据库
			int resultTotal = 0;
			Media media = new Media();
			media.setUserid(singleFile.getUserid());
			media.setImagepath(uuidFileName);
			resultTotal = mediaService.addMedia(media);
			
			if(resultTotal > 0){
				jsonObject.put("success", true);
				jsonObject.put("parentPath", parentPath);
				jsonObject.put("filePath", media.getImagepath());
			}else{
				jsonObject.put("success", false);
				System.out.println("添加数据进数据库失败");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsonObject.put("success", false);
			System.out.println("上传失败或者添加数据进数据库失败");
		}
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 删除媒体
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String deleteMedia(@RequestParam("id") String id,HttpServletRequest request){
		
		//父文件夹 这里存放文件
		String parentPath = "bedgasmBlogContents";
		
		String realpath = request.getServletContext().getRealPath(parentPath);
		
		String filename;
		
		JSONObject jsonObject = new JSONObject();
		
		int resultTotal = 0;
		
        String[] mediasId = id.split(",");
        for(int i = 0; i < mediasId.length; i++) {
            int mediaId = Integer.parseInt(mediasId[i]);
            //TODO 前端页面应该提示如果删除了媒体 资源失效 引用到此媒体的文章里面的资源也失效
            try {
            	filename = mediaService.findById(mediaId).getImagepath();
            	File targetFile = new File(realpath, filename);
            	if(!targetFile.exists()){
        			jsonObject.put("success", false);
        			jsonObject.put("msg", "文件不存在");
        			return jsonObject.toString();
            	}
        		boolean flag = RemoveFile.removeSingle(targetFile);
        		if (!flag) {
        			jsonObject.put("success", false);
        			jsonObject.put("msg", "文件删除失败");
        			return jsonObject.toString();
				}
        		
			} catch (Exception e) {
				jsonObject.put("success", false);
				jsonObject.put("msg", "获取文件名失败或者删除失败");
				return jsonObject.toString();
			}
            resultTotal = mediaService.deleteMedia(mediaId);
        }
        if(resultTotal > 0){
        	jsonObject.put("success", true);
        }else {
        	jsonObject.put("success", false);
		}
        
        return jsonObject.toString();
	}
	
}
