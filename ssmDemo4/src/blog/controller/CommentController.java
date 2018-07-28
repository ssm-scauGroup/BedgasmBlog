package blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import blog.entity.Comment;
import blog.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 根据条件查询评论(某篇文章|某个用户)
	 * @param articleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listCommentByArticleId(@RequestParam(value="articleid",required=false) 
						String articleid,@RequestParam(value="userid",required=false) 
						String userid) {
		JSONObject jsonObject = new JSONObject();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(articleid!=null){
			map.put("articleid",articleid);
		}
		if(userid!=null){
			map.put("userid",userid);
		}
		
		List<Comment> comments = commentService.listComment(map);
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(comments,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = comments.size();
		
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
	public String deleteComment(@RequestParam("id") String id){
		JSONObject jsonObject = new JSONObject();
		
		int resultTotal = 0;
		
        String[] commentsId = id.split(",");
        for(int i = 0; i < commentsId.length; i++) {
            int commentId = Integer.parseInt(commentsId[i]);
            //TODO 需要更新对应文章的replayCount 减去相应的数字
            resultTotal = commentService.deleteComment(commentId);
        }
        if(resultTotal > 0){
        	jsonObject.put("success", true);
        }else {
        	jsonObject.put("success", false);
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
	public String detialComment(@RequestParam("id") String id){
		
		Comment comment = commentService.findById(Integer.parseInt(id));
		
		JSONObject jsonObject = new JSONObject();
		
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
    public String saveArticle(Comment comment){
        int resultTotal = 0;
        
        resultTotal = commentService.addComment(comment);
        
        //TODO 增加对应文章的replayCount
        
        JSONObject result = new JSONObject();
        
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        
        return result.toString();

	}
	
	
}
