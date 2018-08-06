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

import blog.entity.Star;
import blog.entity.User;
import blog.service.StarService;
import blog.service.UserService;
import blog.util.UserisLogin;

@Controller
@RequestMapping("/star")
public class StarController {
	
	@Autowired
	private StarService starService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 根据条件查询关注的人或者粉丝
	 * @param subscriber
	 * @param subscribee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listStar(@RequestParam(value="subscriber",required=false) String subscriber,
			@RequestParam(value="subscribee",required=false) String subscribee,HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();
		
		List<Star> stars = null;
		
		if(subscribee==null&&subscriber==null){
			jsonObject.put("success", false);
			jsonObject.put("msg", "没有传入参数");
			return jsonObject.toString();
		}else if(subscribee!=null && !subscribee.equals("")){
			stars = starService.findBySubscribee(Integer.parseInt(subscribee));
		}else if(subscriber!=null && !subscriber.equals("")){
			stars = starService.findBySubscriber(Integer.parseInt(subscriber));
		}else{
			jsonObject.put("success", false);
			jsonObject.put("msg", "传入参数的值为空或其他错误情况");
			return jsonObject.toString();
		}
		
		JSONArray array = JSON.parseArray(JSONObject.toJSONString(stars,
				SerializerFeature.DisableCircularReferenceDetect));
		
		Integer total = stars.size();
		
		jsonObject.put("success",true);
		
		jsonObject.put("total", total);
		
		jsonObject.put("data", array);
		
		return jsonObject.toString();
		
	}
	
	/**
	 * 关注别人
	 * @param star
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/subscribe",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String subscribePeople(Star star,HttpSession session){
		
        int resultTotal = 0;
        
        JSONObject result = new JSONObject();
        
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			result.put("success",false);
			result.put("msg", "用户没有登录");
			return result.toString();
		}
		
		if(!user.getId().equals(star.getSubscriber())){
			result.put("success",false);
			result.put("msg", "登录用户id与传值进来的用户id不符合");
			return result.toString();
		}
		
		User subscribee = userService.findById(star.getSubscribee());
		
		System.out.println(subscribee);
		
		if(subscribee==null){
			//如果被关注的人不存在，要返回东西
			result.put("success", false);
			result.put("msg", "你要关注的用户id不存在哟~");
			return result.toString();
		}
        
        //新增订阅关系
        resultTotal = starService.addStar(star);
          
        if(resultTotal > 0) {
            result.put("success", true);
            result.put("msg", "关注成功");
        } else {
            result.put("success", false);
            result.put("msg", "关注失败");
        }
        
        return result.toString();
	}
	
	/**
	 * 取消关注别人
	 * @param star
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/unsubscribe",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String unsubscribePeople(Star star,HttpSession session){
		
        int resultTotal = 0;

        JSONObject result = new JSONObject();
        
		User user = UserisLogin.getUser(session);
		
		if(user==null){
			result.put("success",false);
			result.put("msg", "用户没有登录");
			return result.toString();
		}
		
		if(!user.getId().equals(star.getSubscriber())){
			result.put("success",false);
			result.put("msg", "登录用户id与传值进来的用户id不符合");
			return result.toString();
		}
		
		User subscribee = userService.findById(star.getSubscribee());
		
		System.out.println(subscribee);
		
		if(subscribee==null){
			//如果被关注的人不存在，要返回东西
			result.put("success", false);
			result.put("msg", "你要取消关注的用户id不存在哟~");
			return result.toString();
		}
        
        //删除订阅关系
        resultTotal = starService.deleteStar(star);
        
        if(resultTotal > 0) {
            result.put("success", true);
            result.put("msg", "取消关注成功");
        } else {
            result.put("success", false);
            result.put("msg", "取消关注失败");
        }
        
        return result.toString();
	}

}
