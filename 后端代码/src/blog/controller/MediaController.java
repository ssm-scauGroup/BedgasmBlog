package blog.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import blog.entity.User;
import blog.service.MediaService;
import blog.util.RemoveFile;
import blog.util.SingleFile;
import blog.util.UserisLogin;

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
	public String listByPage(@RequestParam(value="userid",required=false) String userid,HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		if(!user.getId().equals(Integer.parseInt(userid)) && user.getRole()==1){
			//既不是本人又不是管理员
			jsonObject.put("success",false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		
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
	
	/**
	 * 上传媒体
	 * @param singleFile
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String uploadMedia(SingleFile singleFile,HttpServletRequest request,HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		System.out.println("file userid is "+singleFile.getUserid());
		System.out.println("file is "+singleFile.getSingleFile());
		System.out.println("userid is "+user.getId());
		
		if(!user.getId().equals(singleFile.getUserid())){
			jsonObject.put("success",false);
			jsonObject.put("msg", "登录用户id与传入进来的文件用户id不符合");
			return jsonObject.toString();
		}
		
		//父文件夹 这里存放文件
		String parentPath = "bedgasmBlogContents";
		
		System.out.println(request.getServletPath());
		
		System.out.println(request.getServerName());
		
		System.out.println(request.getServerPort());
		
		System.out.println(request.getRequestURI());
		
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
	public String deleteMedia(@RequestParam("id") String id,HttpServletRequest request,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		//父文件夹 这里存放文件
		String parentPath = "bedgasmBlogContents";
		
		String realpath = request.getServletContext().getRealPath(parentPath);
		
		String filename;
		
		int resultTotal = 0;
		
        String[] mediasId = id.split(",");
        for(int i = 0; i < mediasId.length; i++) {
            int mediaId = Integer.parseInt(mediasId[i]);
            //TODO 前端页面应该提示如果删除了媒体 资源失效 引用到此媒体的文章里面的资源也失效
            try {
            	Media media = mediaService.findById(mediaId);
            	if(!media.getUserid().equals(user.getId()) && user.getRole()==1){
            		jsonObject.put("success", false);
            		jsonObject.put("msg", "无权限");
            		return jsonObject.toString();
            	}
            	filename = media.getImagepath();
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
        	jsonObject.put("msg", "删除成功");
        }else {
        	jsonObject.put("success", false);
        	jsonObject.put("msg", "删除失败");
		}
        
        return jsonObject.toString();
	}
	
}
