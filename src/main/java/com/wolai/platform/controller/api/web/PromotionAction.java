package com.wolai.platform.controller.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.entity.ExchangePlan;
import com.wolai.platform.entity.RewardPoints;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.PromotionService;
import com.wolai.platform.service.ExchangePlanService;
import com.wolai.platform.service.RewardPointsService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.PromotionVo;
import com.wolai.platform.vo.ExchangePlanVo;

@Controller
@RequestMapping(Constant.API_WEB + "promotion/")
public class PromotionAction extends BaseController {
	@Autowired
	UserService userService;

	@Autowired
	PromotionService promotionService;

	@Autowired
	ExchangePlanService exchangePlanService;
	
	@Autowired
	RewardPointsService rewardPointsService;

	@RequestMapping(value = "detail")
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request,
			@RequestBody Map<String, String> json) {
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);

		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> date = new HashMap<String, Object>();
		ret.put("data", date);

		String id = json.get("id");
		Promotion promotion = (Promotion) promotionService.get(Promotion.class,
				id);
		if (promotion == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}

		List<ExchangePlan> exchanges = exchangePlanService.listByPromotion(promotion.getId());

		PromotionVo promotionVo = new PromotionVo();
		BeanUtilEx.copyProperties(promotionVo, promotion);
		date.put("promotion", promotionVo);

		List<ExchangePlanVo> vols = new ArrayList<ExchangePlanVo>();
		for (ExchangePlan po : exchanges) {
			ExchangePlanVo vo = new ExchangePlanVo();
			BeanUtilEx.copyProperties(vo, po);
			vols.add(vo);
		}
		promotionVo.setExchangePlanList(vols);
		
		RewardPoints rewardPoints = rewardPointsService.getByUser(user.getId());
		date.put("rewardPoints", rewardPoints.getBalance());

		ret.put("code", RespCode.SUCCESS.Code());

		return ret;
	}
}
