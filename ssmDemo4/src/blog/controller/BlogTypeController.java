package blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			@RequestParam(value="rows",required=false) String rows){
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
    public String saveBlogType(BlogType blogType){
        int resultTotal = 0;
        if(blogType.getId()!=null){
            //更新文章
            resultTotal = blogTypeService.updateBlogType(blogType);
        }else{
            //新增文章
        	blogType.setTypecount(0);
            resultTotal = blogTypeService.addBlogType(blogType);
        }
        
        JSONObject result = new JSONObject();
        
        if(resultTotal > 0) {
            result.put("success", true);
            result.put("msg","保存成功");
        } else {
            result.put("success", false);
            result.put("msg","保存失败");
        }
        
        //ResponseUtil.write(response, result);
        
        return result.toString();

	}
	
	/**
	 * 根据关键字搜索类别
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search",produces="application/json;charset=UTF-8")
	public String SearchByTitle(@RequestParam(value="keyword",required=false) String keyword) {
		
		JSONObject jsonObject = new JSONObject();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		System.out.println("keyword="+keyword);;
		
		map.put("typename", keyword);	

		List<BlogType> blogTypes = blogTypeService.listBlogType(map);
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(blogTypes,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = blogTypes.size();
		
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
	public String detialBlogType(@RequestParam("id") String id){
		
		BlogType blogType = blogTypeService.findById(Integer.parseInt(id));
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("category", blogType);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 删除类别
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String deleteCategory(@RequestParam("id") String id){
		JSONObject jsonObject = new JSONObject();
		int resultTotal = 0;
		int articleRes=0;
		int blogTypeRes=0;
		Map<String, Object> map = new HashMap<String, Object>();
        String[] blogTypesId = id.split(",");
        for(int i = 0; i < blogTypesId.length; i++) {
            int blogTypeId = Integer.parseInt(blogTypesId[i]);
            //TODO 将为类别的文章的类别设置为'未分类',未分类的类别count加上文章数
            map.put("blogtypeid", blogTypeId);
            List<Article> articles=articleService.listArticle(map);
            for (int j = 0; j < articles.size(); j++) {
            	articles.get(j).setBlogtypeid(1);
            	BlogType blogType=blogTypeService.findById(1);
            	blogType.setTypecount(blogType.getTypecount()+1);
            	blogTypeRes=blogTypeService.updateBlogType(blogType);
            	articleRes = articleService.updateArticle(articles.get(j));
			}
            resultTotal = blogTypeService.deleteBlogType(blogTypeId);
        }
        if(resultTotal > 0 && articleRes>0 && blogTypeRes>0){
        	jsonObject.put("success", true);
        	jsonObject.put("msg", "删除类别成功");
        }else {
        	jsonObject.put("success", false);
        	jsonObject.put("msg", "删除类别失败");
		}
        
        return jsonObject.toString();
	}
}
