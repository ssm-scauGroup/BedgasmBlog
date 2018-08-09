package com.controller;
import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.domain.Card;
import com.domain.User;
import com.model.Cardinfo;
import com.service.CardService;
import com.util.MyUtil;
@Controller
@RequestMapping("/card")
public class CardController extends BaseController{
	//依赖注入Service进行后台处理
	@Autowired
	private CardService cardService;
	/**
	 * add页面初始化
	 */
	@RequestMapping("/input")
	public String addInput(Model model){
		model.addAttribute("card", new Card());
		return "/card/addCard";
	}
	/**
	 * 查询名片，包括修改、删除的查询
	 */
	@RequestMapping("/query")
	public String query(HttpSession session, Model model, Integer pageCur, String act){
		List<Cardinfo> allCards = cardService.selectAll(getUserName(session));
		int temp = allCards.size();
		model.addAttribute("totalCount", temp);
		int totalPage = 0;
		if (temp == 0) {
			totalPage = 0;//总页数
		} else {
			//返回大于或者等于指定表达式的最小整数
			totalPage = (int) Math.ceil((double) temp / 10);
		}
		if (pageCur == null) {
			pageCur = 1;
		}
		if ((pageCur - 1) * 10 > temp) {
			pageCur = pageCur - 1;
		}
		
		//分页查询
		allCards = cardService.selectAllByPage(pageCur, getUserName(session));
		
		model.addAttribute("allCards", allCards);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageCur", pageCur);
		//删除查询
		if("deleteSelect".equals(act)){
			return "/card/deleteSelect";
		}
		//修改查询
		else if("updateSelect".equals(act)){
			return "/card/updateSelect";
		}else{
			return "/card/queryCards";
		}
	}
	/**
	 * 添加与修改页面
	 */
	@RequestMapping("/add")
	public String add(@ModelAttribute Card card, HttpServletRequest request, HttpSession session, String updateAct){
		/*上传文件的保存位置"/logos"，该位置是指
		workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps，
		发布后使用*/
		//防止文件名重名
		String newFileName = "";
		String fileName = card.getLogoImage().getOriginalFilename(); 
		//选择了文件
		if(fileName.length() > 0){
			String realpath = request.getServletContext().getRealPath("logos");
			//实现文件上传
			String fileType = fileName.substring(fileName.lastIndexOf('.'));
			//防止文件名重名
			newFileName = MyUtil.getStringID() + fileType;
			card.setLogo(newFileName);
			File targetFile = new File(realpath, newFileName); 
			if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        } 
			//上传 
	        try {   
	        	card.getLogoImage().transferTo(targetFile);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		}
		//修改
		if("update".equals(updateAct)){//updateAct不能与act重名，因为使用了转发
			//修改到数据库
	        if(cardService.updateByPrimaryKeySelective(card) > 0){
	        	return "forward:/card/query?act=updateSelect";
	        }else{
	        	return "/card/updateCard";
	        }
			
		}else{//添加
			card.setUsername(getUserName(session));
			//保存到数据库
			if(cardService.insert(card) > 0){
				//转发到查询的controller
				return "forward:/card/query";
			}else{
				return "/card/addCard";
			}
		}
	}
	/**
	 * 查询一个名片
	 */
	@RequestMapping("/selectACard")
	public String selectACard(HttpSession session,Model model, Integer id, String act){
		Cardinfo acard = cardService.selectByPrimaryKey(id);
		System.out.println("acard username "+acard.getUsername());
		System.out.println("session username"+getUserName(session));
		if(!acard.getUsername().equals(getUserName(session))){ //不属于用户的名片不能展示出来
			return "/error/403";
		}
		model.addAttribute("card", acard);
		//修改页面
		if("updateAcard".equals(act)){
			return "/card/updateCard";
		}
		//详情页面
		return "/card/detail";
	}
	/**
	 * 删除一个名片
	 */
	@RequestMapping("/deleteACard")
	public String deleteACard(Integer id){
		cardService.deleteByPrimaryKey(id);
		return "forward:/card/query?act=deleteSelect";
	}
	/**
	 * 删除多个名片
	 */
	@RequestMapping("/deleteCards")
	public String deleteCards(Integer[] ids){
		cardService.deleteCards(ids);
		return "forward:/card/query?act=deleteSelect";
	}
	/**
	 * 获得登录用户的用户名(非处理请求方法)
	 */
	public String getUserName(HttpSession session){
		 User user = (User)session.getAttribute("user");
		 return user.getUsername();
	}
}
