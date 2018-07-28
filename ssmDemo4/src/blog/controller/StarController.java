package blog.controller;

import java.util.List;

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
import blog.service.StarService;

@Controller
@RequestMapping("/star")
public class StarController {
	
	@Autowired
	private StarService starService;
	
	/**
	 * 根据条件查询关注的人或者粉丝
	 * @param subscriber
	 * @param subscribee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String listStar(@RequestParam(value="subscriber",required=false) String subscriber,
			@RequestParam(value="subscribee",required=false) String subscribee) {
		
		JSONObject jsonObject = new JSONObject();
		
		List<Star> stars = null;
		
		if(subscribee==null&&subscriber==null){
			jsonObject.put("success", false);
			return jsonObject.toString();
		}else if(subscribee!=null){
			stars = starService.findBySubscribee(Integer.parseInt(subscribee));
		}else if(subscriber!=null){
			stars = starService.findBySubscriber(Integer.parseInt(subscriber));
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
	public String subscribePeople(Star star){
		
        int resultTotal = 0;
        
        //新增订阅关系
        resultTotal = starService.addStar(star);
        
        JSONObject result = new JSONObject();
        
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
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
	public String unsubscribePeople(Star star){
		
        int resultTotal = 0;
        
        //删除订阅关系
        resultTotal = starService.deleteStar(star);
        
        JSONObject result = new JSONObject();
        
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        
        return result.toString();
	}

}
