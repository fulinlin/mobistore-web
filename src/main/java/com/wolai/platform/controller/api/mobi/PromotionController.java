package com.wolai.platform.controller.api.mobi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.wolai.platform.entity.Coupon;
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
	public Map<String,Object> list(HttpServletRequest request, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String,Object> ret =new HashMap<String, Object>();
		Map<String,Object> data =new HashMap<String, Object>();

		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		
		List<PromotionVo> vols = new ArrayList<PromotionVo>();
		List<PromotionVo> recommendedVols = new ArrayList<PromotionVo>();
		
		Page page = promotionService.list(startIndex, pageSize);
		for (Object obj : page.getItems()) {
			Promotion promotion = (Promotion)obj;
			PromotionVo vo = new PromotionVo();
			BeanUtilEx.copyProperties(vo, promotion);
			vols.add(vo);
		}
		
		List ls = promotionService.listRecommended();
		for (Object obj : ls) {
			Promotion promotion = (Promotion)obj;
			PromotionVo vo = new PromotionVo();
			BeanUtilEx.copyProperties(vo, promotion);
			
			recommendedVols.add(vo);
		}
		
		data.put("promotions", vols);
		data.put("promotionsTotalPages", page.getTotalPages());
		data.put("recommendedPromotions", recommendedVols);
		data.put("recommendedPromotionsTotalPages", 1);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", data);
		return ret;
	}

	@RequestMapping(value="detail")
	@ResponseBody
	public Map<String,Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		
		String id = json.get("id");
		if (StringUtils.isEmpty(id)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = promotionService.get(Promotion.class, id);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		Promotion po = (Promotion) obj;

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
