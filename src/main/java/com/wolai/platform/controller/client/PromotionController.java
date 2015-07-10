package com.wolai.platform.controller.client;

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

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.PromotionService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.PromotionVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "promotion/")
public class PromotionController {
	@Autowired
	UserService userService;
	
	@Autowired
	PromotionService promotionService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		SysUser user = userService.getUserByToken(token);
		String userId = user.getId();
		
		List<PromotionVo> vols = new ArrayList<PromotionVo>();
		List<Promotion> ls = promotionService.listByUser(userId);
		for (Promotion promotion : ls) {
			PromotionVo vo = new PromotionVo();
			BeanUtilEx.copyProperties(vo, promotion);
			vols.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vols);
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public Map<String,Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");
		Promotion po = (Promotion) promotionService.get(Promotion.class, id);
		
		if (po == null) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}

		PromotionVo vo = new PromotionVo();
		BeanUtilEx.copyProperties(vo, po);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
}
