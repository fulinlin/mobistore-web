package com.wolai.platform.controller.api.mobi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.PromotionService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.PromotionVo;

@Controller
@RequestMapping(Constant.API_MOBI + "promotion/")
public class PromotionController extends BaseController {
	@Autowired
	UserService userService;
	
	@Autowired
	PromotionService promotionService;

	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String,Object> ret =new HashMap<String, Object>();

		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		
		List<PromotionVo> vols = new ArrayList<PromotionVo>();
		Page page = promotionService.listByUser(userId, startIndex, pageSize);
		for (Object obj : page.getItems()) {
			Promotion promotion = (Promotion)obj;
			PromotionVo vo = new PromotionVo();
			BeanUtilEx.copyProperties(vo, promotion);
			vols.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vols);
		return ret;
	}

	@RequestMapping(value="detail")
	@ResponseBody
	public Map<String,Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");
		Promotion po = (Promotion) promotionService.get(Promotion.class, id);
		
		if (po == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}

		PromotionVo vo = new PromotionVo();
		BeanUtilEx.copyProperties(vo, po);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
	
	@RequestMapping(value="exchange")
	@ResponseBody
	public Map<String,Object> exchange(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");

		
		ret.put("code", RespCode.SUCCESS.Code());
//		ret.put("data", vo);
		return ret;
	}
	
	@RequestMapping(value="snapup")
	@ResponseBody
	public Map<String,Object> snapup(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");

		
		ret.put("code", RespCode.SUCCESS.Code());
//		ret.put("data", vo);
		return ret;
	}
}
