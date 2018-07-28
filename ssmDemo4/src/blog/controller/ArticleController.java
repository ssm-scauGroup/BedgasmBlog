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

import blog.entity.Article;
import blog.service.ArticleService;
import blog.util.PageBean;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String listByPage(@RequestParam(value="page",required=false) String page,
			@RequestParam(value="rows",required=false) String rows) {
		
		if(page==null){
			page="1";
		}
		if(rows==null){
			rows="5";
		}
		
		PageBean<Article> pageBean = new PageBean<Article>(Integer.parseInt(page), Integer.parseInt(rows));
		
		pageBean = articleService.listByPage(pageBean);
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(pageBean.getResult(),
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = pageBean.getResult().size();
		
		jsonObject.put("total", total);
		
		jsonObject.put("posts", array);
		
		System.out.println(jsonObject.toString());
		
		return jsonObject.toString();
		
	}
	

	/**
	 * 根据标题或者标签搜索
	 * @param keyword
	 * @param tags
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search",produces="application/json;charset=UTF-8")
	public String SearchByTitle(@RequestParam(value="keyword",required=false) String keyword,
							@RequestParam(value="tags",required=false) String tags) {
		
		JSONObject jsonObject = new JSONObject();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		System.out.println("keyword="+keyword);
		System.out.println("tags="+tags);
		
		if(keyword!=null){
			map.put("title", keyword);	
		}
		
		if(tags!=null){
			map.put("tags", tags);
		}
		
		List<Article> articles = articleService.listArticle(map);
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(articles,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = articles.size();
		
		jsonObject.put("total", total);
		jsonObject.put("articles", array);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 保存文章(更新|增加)
	 * @param article
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/save",produces="application/json;charset=UTF-8")
    public String saveArticle(Article article){
        int resultTotal = 0;
        if(article.getId()!=null){
            //更新文章
            resultTotal = articleService.updateArticle(article);
        }else{
            //新增文章
            resultTotal = articleService.addArticle(article);
        }
        
        JSONObject result = new JSONObject();
        
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        
        //ResponseUtil.write(response, result);
        
        return result.toString();

	}
	
	/**
	 * 返回文章详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/detail",produces="application/json;charset=UTF-8")
	public String detialArticle(@RequestParam("id") String id){
		
		Article article = articleService.findById(Integer.parseInt(id));
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("post", article);
		
		System.out.println(jsonObject.toString());
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 列出某位作者的所有文章
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/author",produces="application/json;charset=UTF-8")
	public String listByAuthor(@RequestParam("id") String id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("author",id);
		
		List<Article> articles = articleService.listByAuthor(Integer.parseInt(id));
		
		Integer total = articles.size();
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(articles,
				SerializerFeature.DisableCircularReferenceDetect));
		
		jsonObject.put("author", id);
		jsonObject.put("total",total);
		jsonObject.put("articles",array);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 查询某个分类的所有文章
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/category",produces="application/json;charset=UTF-8")
	public String listByCategory(@RequestParam("id") String id){
		JSONObject jsonObject = new JSONObject();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("blogtypeid",id);
		
		List<Article> articles = articleService.listArticle(map);
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(articles,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = articles.size();
		
		jsonObject.put("total", total);
		jsonObject.put("articles", array);
		
		return jsonObject.toString();
	}
	
	/**
	 * 删除文章(单篇或者多篇)
	 * 多篇传进来的字符串用逗号隔开 如1,2
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String deleteArticle(@RequestParam("id") String id){
		JSONObject jsonObject = new JSONObject();
		
		int resultTotal = 0;
		
        String[] articlesId = id.split(",");
        for(int i = 0; i < articlesId.length; i++) {
            int articleId = Integer.parseInt(articlesId[i]);
            //TODO 先删除博客所关联的评论 现在没有完成评论的功能 
            //commentService.deleteCommentByBlogId(id);
            resultTotal = articleService.deleteArticle(articleId);
        }
        if(resultTotal > 0){
        	jsonObject.put("success", true);
        }else {
        	jsonObject.put("success", false);
		}
        
        return jsonObject.toString();
	}

}
