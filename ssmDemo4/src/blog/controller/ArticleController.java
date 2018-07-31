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
import blog.service.CommentService;
import blog.util.PageBean;
import blog.util.UserisLogin;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private BlogTypeService blogTypeService;
	
	@Autowired
	private CommentService commentService;

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	public String listByPage(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows) {

		if (page == null) {
			page = "1";
		}
		if (rows == null) {
			rows = "5";
		}

		PageBean<Article> pageBean = new PageBean<Article>(Integer.parseInt(page), Integer.parseInt(rows));

		pageBean = articleService.listByPage(pageBean);

		JSONObject jsonObject = new JSONObject();

		JSONArray array = JSON.parseArray(
				JSONObject.toJSONString(pageBean.getResult(), SerializerFeature.DisableCircularReferenceDetect));

		Integer total = pageBean.getResult().size();
		
		jsonObject.put("success", true);

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
	@RequestMapping(value = "/search", produces = "application/json;charset=UTF-8")
	public String SearchByTitle(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "tags", required = false) String tags) {

		JSONObject jsonObject = new JSONObject();

		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("keyword=" + keyword);
		System.out.println("tags=" + tags);

		if (keyword != null) {
			map.put("title", keyword);
		}

		if (tags != null) {
			map.put("tags", tags);
		}

		List<Article> articles = articleService.listArticle(map);

		JSONArray array = JSON
				.parseArray(JSONObject.toJSONString(articles, SerializerFeature.DisableCircularReferenceDetect));

		Integer total = articles.size();
		
		jsonObject.put("success", true);

		jsonObject.put("total", total);
		jsonObject.put("articles", array);

		return jsonObject.toString();

	}

	/**
	 * 保存文章(更新|增加)
	 * 
	 * @param article
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
	public String saveArticle(Article article,HttpSession session) {
		
		JSONObject result = new JSONObject();
		int resultTotal = 0;
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			result.put("success",false);
			result.put("msg", "用户没有登录");
			return result.toString();
		}
		
		if (article.getId() != null) {
			
			// 更新文章
			
			Article oldArticle = articleService.findById(article.getId());
			if (oldArticle==null) {
				result.put("success", false);
				result.put("msg", "文章不存在");
				return result.toString();
			}
			
			if(oldArticle.getAuthor()!=user.getId() || user.getId()!=article.getAuthor()){
				result.put("success", false);
				result.put("msg", "文章不是自己的，或者将自己的文章作者id改为别人的id");
				return result.toString();
			}
			
			//如果传入为空或者传入的和原来的一样
			
			if (article.getBlogtypeid() == null || oldArticle.getBlogtypeid()==article.getBlogtypeid()) {
				
				resultTotal = articleService.updateArticle(article);
			
			} else {
				try {
					BlogType newBlogType=blogTypeService.findById(article.getBlogtypeid());
					if (newBlogType==null) {
						result.put("success", false);
						result.put("msg", "更新出错:没有这个文章类别");
						return result.toString();
					}
					newBlogType.setTypecount(newBlogType.getTypecount() + 1);
					BlogType oldBlogType = blogTypeService.findById(oldArticle.getBlogtypeid());
					oldBlogType.setTypecount(oldBlogType.getTypecount() - 1);
					blogTypeService.updateBlogType(newBlogType);
					blogTypeService.updateBlogType(oldBlogType);
					resultTotal = articleService.updateArticle(article);
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", "更新文章出现错误");
					return result.toString();
				}

			}
		} else {
			
			// 新增文章
			
			if (user.getId()!=article.getAuthor()) {
				result.put("success", false);
				result.put("msg", "不能以别人的名义发表文章");
				return result.toString();
			}
			BlogType blogType = blogTypeService.findById(article.getBlogtypeid());
			
			if(blogType==null){
				result.put("success", false);
				result.put("msg", "没有这个文章类别");
				return result.toString();
			}
			
			resultTotal = articleService.addArticle(article);
			
			// 更新类别文章数目
			blogType.setTypecount(blogType.getTypecount() + 1);
			
			blogTypeService.updateBlogType(blogType);

		}
		if (resultTotal > 0) {
			//
			result.put("success", true);
			result.put("msg","保存成功");
		} else {
			result.put("success", false);
			result.put("msg","保存失败");
		}

		// ResponseUtil.write(response, result);

		return result.toString();

	}

	/**
	 * 返回文章详情
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", produces = "application/json;charset=UTF-8")
	public String detialArticle(@RequestParam("id") String id) {

		Article article = articleService.findById(Integer.parseInt(id));

		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("success", true);

		jsonObject.put("post", article);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}

	/**
	 * 列出某位作者的所有文章
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/author", produces = "application/json;charset=UTF-8")
	public String listByAuthor(@RequestParam("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("author", id);

		List<Article> articles = articleService.listByAuthor(Integer.parseInt(id));

		Integer total = articles.size();

		JSONObject jsonObject = new JSONObject();

		JSONArray array = JSON
				.parseArray(JSONObject.toJSONString(articles, SerializerFeature.DisableCircularReferenceDetect));
		
		jsonObject.put("success", true);
		jsonObject.put("author", id);
		jsonObject.put("total", total);
		jsonObject.put("articles", array);

		return jsonObject.toString();

	}

	/**
	 * 查询某个分类的所有文章
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/category", produces = "application/json;charset=UTF-8")
	public String listByCategory(@RequestParam("id") String id) {
		JSONObject jsonObject = new JSONObject();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("blogtypeid", id);

		List<Article> articles = articleService.listArticle(map);

		JSONArray array = JSON
				.parseArray(JSONObject.toJSONString(articles, SerializerFeature.DisableCircularReferenceDetect));

		Integer total = articles.size();
		
		jsonObject.put("success", true);
		jsonObject.put("total", total);
		jsonObject.put("articles", array);

		return jsonObject.toString();
	}

	/**
	 * 删除文章(单篇或者多篇) 多篇传进来的字符串用逗号隔开 如1,2
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
	public String deleteArticle(@RequestParam("id") String id,HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();

		int resultTotal = 0;

		String[] articlesId = id.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
		
		for (int i = 0; i < articlesId.length; i++) {
			int articleId = Integer.parseInt(articlesId[i]);
			Article article = articleService.findById(articleId);
			if(article==null){
				jsonObject.put("success", false);
				jsonObject.put("msg","文章不存在");
				return jsonObject.toString();
			}
			if(article.getAuthor()!=user.getId() && user.getRole()==1){
				//既不是作者也不是管理员
				jsonObject.put("success", false);
				jsonObject.put("msg","您没有权限删除此文章");
				return jsonObject.toString();
			}
			
			//文章所属类别的 typecount-1
			BlogType blogType = blogTypeService.findById(article.getBlogtypeid());
			blogType.setTypecount(blogType.getTypecount()-1);
			blogTypeService.updateBlogType(blogType);
			
			map.put("articleid", articleId);
			List<Comment> comments = commentService.listComment(map);
			for (int j = 0; j < comments.size(); j++) {
				try {
					commentService.deleteComment(comments.get(j).getId());
				} catch (Exception e) {
					// TODO: handle exception
					jsonObject.put("success", false);
					jsonObject.put("msg", "删除文章评论失败");
					return jsonObject.toString();
				}
				
			}
			resultTotal = articleService.deleteArticle(articleId);
		}
		if (resultTotal > 0 ) {
			jsonObject.put("success", true);
			jsonObject.put("msg", "删除成功");
		} else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "删除失败");
		}

		return jsonObject.toString();
	}

}
