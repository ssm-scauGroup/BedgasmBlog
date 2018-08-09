package blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TryCatchFinally;

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

import blog.entity.Article;
import blog.entity.Comment;
import blog.entity.User;
import blog.service.ArticleService;
import blog.service.CommentService;
import blog.service.UserService;
import blog.util.UserisLogin;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 根据条件查询评论(某篇文章|某个用户)
	 * @param articleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listCommentByArticleId(@RequestParam(value="articleid",required=false) 
						String articleid,@RequestParam(value="userid",required=false) 
						String userid,HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(articleid!=null){
			Article article = articleService.findById(Integer.parseInt(articleid));
			if(article==null){
				jsonObject.put("success", false);
				jsonObject.put("msg", "没有这篇文章");
				return jsonObject.toString();
			}
			map.put("articleid",articleid);
		}
		if(userid!=null){
			User user = userService.findById(Integer.parseInt(userid));
			if(user==null){
				jsonObject.put("success", false);
				jsonObject.put("msg", "用户不存在");
				return jsonObject.toString();
			}
			map.put("userid",userid);
		}
		
		List<Comment> comments = commentService.listComment(map);
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(comments,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = comments.size();
		
		jsonObject.put("success", true);
		
		jsonObject.put("total", total);
		
		jsonObject.put("comments", array);
		
		return jsonObject.toString();
	}
	
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String deleteComment(@RequestParam("id") String id,HttpSession session){
		JSONObject jsonObject = new JSONObject();
		
		int resultTotal = 0;
		int res=0;
		int i=0;
        String[] commentsId = id.split(",");
        
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			jsonObject.put("success",false);
			jsonObject.put("msg", "用户没有登录");
			return jsonObject.toString();
		}
        
        for(i = 0; i < commentsId.length; i++) {
            int commentId = Integer.parseInt(commentsId[i]);
            Comment comment = commentService.findById(commentId);
            System.out.println("comment is "+comment);
            if(comment==null){
            	jsonObject.put("success",false);
            	jsonObject.put("msg","没有此评论");
            	return jsonObject.toString();
            }
            System.out.println("comment userid is "+comment.getUserid());
            if(!comment.getUserid().equals(user.getId()) && user.getRole()==1){
            	//既不是作者也不是管理员
            	jsonObject.put("success",false);
            	jsonObject.put("msg","您没有权利删除此评论");
            	return jsonObject.toString();
            }
            Article article = articleService.findById(comment.getArticleid());
            article.setReplyCount(article.getReplyCount()-1);
            res = articleService.updateArticle(article);
            resultTotal = commentService.deleteComment(commentId);
        }
        if(resultTotal > 0 && res > 0){
        	jsonObject.put("success", true);
        	jsonObject.put("msg", "删除成功");
        }else {
        	jsonObject.put("success", false);
        	jsonObject.put("msg", "删除失败");
		}
        
        return jsonObject.toString();
	}
	
	/**
	 * 查询具体某一条评论
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/detail",produces="application/json;charset=UTF-8")
	public String detialComment(@RequestParam("id") String id,HttpSession session){
		
		Comment comment = commentService.findById(Integer.parseInt(id));
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("success", true);
		
		jsonObject.put("comment", comment);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 保存评论
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save",produces="application/json;charset=UTF-8")
    public String saveArticle(Comment comment,HttpSession session){
		
        int resultTotal = 0; //标识评论更新
        
        int res = 0;	//标识文章更新
        
        JSONObject result = new JSONObject();
        
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			result.put("success",false);
			result.put("msg", "用户没有登录");
			return result.toString();
		}
        
		if(!user.getId().equals(comment.getUserid())){
			result.put("success", false);
			result.put("msg", "不能以别人的名义发表评论");
			return result.toString();
		}
		
        //增加对应文章的replayCount
        Article article = articleService.findById(comment.getArticleid());
        
        if(article==null){
        	result.put("success", false);
        	result.put("msg", "传入的评论对应文章id不存在");
        	return result.toString();
        }
        
        try {
        	resultTotal = commentService.addComment(comment);
		} catch (Exception e) {
			// TODO: handle exception
			result.put("success", false);
			result.put("msg", "保存过程中出现错误");
			return result.toString();
		}
        
        article.setReplyCount(article.getReplyCount()+1);
        
        res = articleService.updateArticle(article);
        
        if(resultTotal > 0 && res > 0) {
            result.put("success", true);
            result.put("msg", "评论成功");
        } else {
            result.put("success", false);
            result.put("msg", "评论失败");
        }
        
        return result.toString();

	}
	
	/**
	 * 查询用户的评论总数
	 * @param userid
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/gettotalbyuserid",produces="application/json;charset=UTF-8")
	public String gettotalbyuserid(@RequestParam(value = "userid", required = true) String userid,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		if (userid==null || userid.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "没有传入参数或者参数值为空");
			return jsonObject.toString();
		}
		
		Long commentTotal;
		
		try {
			commentTotal = commentService.getTotalByUserId(Integer.parseInt(userid));
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
			jsonObject.put("msg", "发生错误");
			return jsonObject.toString();
		}
		
		jsonObject.put("success", true);
		
		jsonObject.put("total", commentTotal);
		
		jsonObject.put("msg", "共有"+commentTotal+"条评论");
		
		return jsonObject.toString();
	}
	
	
	/**
	 * 查询某篇文章的评论总数
	 * @param articleid
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/gettotalbyarticleid",produces="application/json;charset=UTF-8")
	public String gettotalbyarticleid(@RequestParam(value = "articleid", required = true) String articleid,HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		if (articleid==null || articleid.equals("")) {
			jsonObject.put("success", false);
			jsonObject.put("msg", "没有传入参数或者参数值为空");
			return jsonObject.toString();
		}
		
		Long commentTotal;
		
		try {
			commentTotal = commentService.getTotalByArticleId(Integer.parseInt(articleid));
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("success", false);
			jsonObject.put("msg", "发生错误");
			return jsonObject.toString();
		}
		
		jsonObject.put("success", true);
		
		jsonObject.put("total", commentTotal);
		
		jsonObject.put("msg", "共有"+commentTotal+"条评论");
		
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/test500",produces="application/json;charset=UTF-8")
	public String test500(HttpSession session){
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("test500", 1/0);
		
		return jsonObject.toString();
	}
}
