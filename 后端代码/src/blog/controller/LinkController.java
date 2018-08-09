package blog.controller;

import java.util.List;

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

import blog.entity.Link;
import blog.entity.User;
import blog.service.LinkService;
import blog.util.UserisLogin;

@Controller
@RequestMapping("/links")
public class LinkController {
	
	@Autowired
	private LinkService linkService;
	
	/**
	 * 列出网站的友情链接
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listByPage(HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();
		
		List<Link> links = linkService.listLink();
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(links,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = links.size();
		
		jsonObject.put("success", true);
		
		jsonObject.put("total", total);
		
		jsonObject.put("links", array);
		
		System.out.println(jsonObject.toString());
		
		return jsonObject.toString();
		
	}
	
	
	/**
	 * 保存友情链接
	 * @param link
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public String saveLink(Link link,HttpSession session){
        int resultTotal = 0;
        
        JSONObject result = new JSONObject();
        
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			result.put("success",false);
			result.put("msg", "用户没有登录");
			return result.toString();
		}
		
		if(user.getRole()==1){
			result.put("success",false);
			result.put("msg", "无权限");
			return result.toString();
		}
        try {
            if(link.getId()!=null){
                //更新友情链接
                resultTotal = linkService.updateLink(link);
            }else{
                //新增友情链接
                resultTotal = linkService.addLink(link);
            }
		} catch (Exception e) {
			// TODO: handle exception
			result.put("success", false);
			result.put("msg", "保存失败");
			return result.toString();
		}

        
        if(resultTotal > 0) {
            result.put("success", true);
            result.put("msg", "保存成功");
        } else {
            result.put("success", false);
            result.put("msg", "保存失败");
        }
        
        return result.toString();

	}
	
	/**
	 * 删除友情链接(单条|多条)
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String deleteLink(@RequestParam("id") String id,HttpSession session){
		JSONObject jsonObject = new JSONObject();
		
		int resultTotal = 0;
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		if(user.getRole()==1){
			jsonObject.put("success",false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
		
        String[] linksId = id.split(",");
        for(int i = 0; i < linksId.length; i++) {
            int linkId = Integer.parseInt(linksId[i]);
            resultTotal = linkService.deleteLink(linkId);
        }
        if(resultTotal > 0){
        	jsonObject.put("success", true);
        }else {
        	jsonObject.put("success", false);
		}
        
        return jsonObject.toString();
	}
	
}
