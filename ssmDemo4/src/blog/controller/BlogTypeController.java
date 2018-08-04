package blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import blog.entity.*;
import blog.service.ArticleService;
import blog.service.BlogTypeService;
import blog.util.PageBean;
import blog.util.UserisLogin;

@Controller
@RequestMapping("/category")
public class BlogTypeController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private BlogTypeService blogTypeService;
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String listByPage(@RequestParam(value="page",required=false) String page,
			@RequestParam(value="rows",required=false) String rows,HttpSession session){
		if(page==null){
			page="1";
		}
		if(rows==null){
			rows="5";
		}
		
		PageBean<BlogType> pageBean = new PageBean<BlogType>(Integer.parseInt(page), Integer.parseInt(rows));
		
		pageBean = blogTypeService.listByPage(pageBean);
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(pageBean.getResult(),
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = pageBean.getResult().size();
		
		jsonObject.put("success", true);
		
		jsonObject.put("total", total);
		
		jsonObject.put("categories", array);
		
		System.out.println(jsonObject.toString());
		
		return jsonObject.toString();
	}
	
	/**
	 * 保存类别(包括更新|增加)
	 * @param blogType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save",produces="application/json;charset=UTF-8")
    public String saveBlogType(BlogType blogType,HttpSession session){
		
		JSONObject result = new JSONObject();
		
		int resultTotal = 0;
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			result.put("success",false);
			result.put("msg", "用户没有登录");
			return result.toString();
		}
		
		if(user.getRole()==1){
			result.put("success", false);
			result.put("msg", "无权限");
			return result.toString();
		}
        
        if(blogType.getId()!=null){
            //更新文章
            resultTotal = blogTypeService.updateBlogType(blogType);
        }else{
            //新增文章
        	blogType.setTypecount(0);
            resultTotal = blogTypeService.addBlogType(blogType);
        }
        
        if(resultTotal > 0) {
            result.put("success", true);
            result.put("msg","保存成功");
        } else {
            result.put("success", false);
            result.put("msg","保存失败");
        }
        
        return result.toString();

	}
	
	/**
	 * 根据关键字搜索类别
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search",produces="application/json;charset=UTF-8")
	public String SearchByTitle(@RequestParam(value="keyword",required=false) String keyword,HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		System.out.println("keyword="+keyword);;
		
		map.put("typename", keyword);	

		List<BlogType> blogTypes = blogTypeService.listBlogType(map);
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(blogTypes,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = blogTypes.size();
		
		jsonObject.put("success", true);
		jsonObject.put("total", total);
		jsonObject.put("categories", array);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 类别信息详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/detail",produces="application/json;charset=UTF-8")
	public String detialBlogType(@RequestParam("id") String id,HttpSession session){
		
		BlogType blogType = blogTypeService.findById(Integer.parseInt(id));
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("success", true);
		
		jsonObject.put("category", blogType);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 删除类别(只有管理员可以删除)
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String deleteCategory(@RequestParam("id") String id,HttpSession session){
		JSONObject jsonObject = new JSONObject();
		int resultTotal = 0;
		Map<String, Object> map = new HashMap<String, Object>();
        String[] blogTypesId = id.split(",");
        
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		//设置成只有管理员才可以删除
		if(user.getRole()==1){
			jsonObject.put("success",false);
			jsonObject.put("msg", "无权限");
			return jsonObject.toString();
		}
        
        for(int i = 0; i < blogTypesId.length; i++) {
            int blogTypeId = Integer.parseInt(blogTypesId[i]);
            
            if(blogTypeId==1){
            	jsonObject.put("success", false);
            	jsonObject.put("msg", "未分类类别不能删除");
            	return jsonObject.toString();
            }
            
            if(blogTypeService.findById(blogTypeId)==null){
            	jsonObject.put("success", false);
            	jsonObject.put("msg", "类别不存在");
            	return jsonObject.toString();
            }
            
            //将为类别的文章的类别设置为'未分类',未分类的类别count加上文章数
            
            map.put("blogtypeid", blogTypeId);
            List<Article> articles=articleService.listArticle(map);
            for (int j = 0; j < articles.size(); j++) {
            	articles.get(j).setBlogtypeid(1);
            	BlogType blogType=blogTypeService.findById(1);
            	blogType.setTypecount(blogType.getTypecount()+1);
            	blogTypeService.updateBlogType(blogType);
            	articleService.updateArticle(articles.get(j));
			}
            resultTotal = blogTypeService.deleteBlogType(blogTypeId);
        }
        if(resultTotal > 0){
        	jsonObject.put("success", true);
        	jsonObject.put("msg", "删除类别成功");
        }else {
        	jsonObject.put("success", false);
        	jsonObject.put("msg", "删除类别失败");
		}
        
        return jsonObject.toString();
	}
}
