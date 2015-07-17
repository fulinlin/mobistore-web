package com.wolai.platform.controller.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.ExchangeHistory;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.entity.ExchangePlan;
import com.wolai.platform.entity.RewardPoints;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.ExchangeHistoryService;
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
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	ExchangeHistoryService exchangeHistoryService;

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
	
	@RequestMapping(value = "exchangePlan")
	@ResponseBody
	public Map<String, Object> exchangePlan(HttpServletRequest request,
			@RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String exchangePlanId = json.get("exchangePlanId");
		ExchangePlan exchangePlan = (ExchangePlan) exchangePlanService.get(ExchangePlan.class,
				exchangePlanId);
		if (exchangePlan == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}

		ExchangePlanVo vo = new ExchangePlanVo();
		BeanUtilEx.copyProperties(vo, exchangePlan);

		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);

		return ret;
	}
	
	@RequestMapping(value = "pointsExchange")
	@ResponseBody
	public Map<String, Object> pointsExchange(HttpServletRequest request,
			@RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		if (StringUtils.isEmpty(json.get("exchangePlanId")) || StringUtils.isEmpty(json.get("exNumber")) ) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String exchangePlanId = json.get("exchangePlanId");
		int number = Integer.valueOf(json.get("exNumber"));

		ExchangePlan exchangePlan = (ExchangePlan) promotionService.get(ExchangePlan.class, exchangePlanId);
		if (exchangePlan == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "exchangePlan not found");
			return ret;
		}
		
		if (exchangePlan.getNumber() < number) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "remain not enough");
			return ret;
		}
		
		RewardPoints rewardPoints = rewardPointsService.getByUser(user.getId());
		if (rewardPoints.getBalance() <  exchangePlan.getPrice() * number) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "balance not enough");
			return ret;
		}

		for (int i = 0; i < number; i++) {
			Coupon coupon = new Coupon();
			coupon.setOwnerId(user.getId());
			coupon.setStartDate(exchangePlan.getStartTime());
			coupon.setEndDate(exchangePlan.getEndTime());
			coupon.setType(CouponType.MONEY); // 兑换的一定是抵时券
			coupon.setMoney(exchangePlan.getFaceValue());
			coupon.setOrigin("积分兑换");
			coupon.setNote("测试数据");
			couponService.saveOrUpdate(coupon);
		}
		
		exchangePlan.setNumber(exchangePlan.getNumber() - number);
		exchangePlanService.saveOrUpdate(exchangePlan);
		
		rewardPoints.setBalance(rewardPoints.getBalance() - exchangePlan.getPrice() * number);
		rewardPointsService.saveOrUpdate(rewardPoints);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("balance", rewardPoints.getBalance());
		ret.put("remain", exchangePlan.getNumber());

		return ret;
	}
	
	@RequestMapping(value = "snapup")
	@ResponseBody
	public Map<String, Object> snapup(HttpServletRequest request,
			@RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		if (StringUtils.isEmpty(json.get("exchangePlanId"))) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String exchangePlanId = json.get("exchangePlanId");

		ExchangePlan exchangePlan = (ExchangePlan) promotionService.get(ExchangePlan.class, exchangePlanId);
		if (exchangePlan == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "exchangePlan not found");
			return ret;
		}
		
		if (exchangePlan.getNumber() < 1) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "remain not enough");
			return ret;
		}
		
		ExchangeHistory exchangePlanHistory = exchangeHistoryService.getHistoryPers(user.getId(), exchangePlanId);
		if (exchangePlanHistory.getTimes() >= exchangePlan.getTimesLimit()) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("timeLimit", exchangePlan.getTimesLimit());
			ret.put("msg", "only " + exchangePlan.getTimesLimit() + " times allow");
			return ret;
		}

		Coupon coupon = new Coupon();
		coupon.setOwnerId(user.getId());
		coupon.setStartDate(exchangePlan.getStartTime());
		coupon.setEndDate(exchangePlan.getEndTime());
		coupon.setType(CouponType.MONEY); // 兑换的一定是抵时券
		coupon.setMoney(exchangePlan.getFaceValue());
		coupon.setOrigin("限时抢购");
		coupon.setNote("测试数据");
		couponService.saveOrUpdate(coupon);
		
		exchangePlan.setNumber(exchangePlan.getNumber() - 1);
		exchangePlanService.saveOrUpdate(exchangePlan);
		
		exchangePlanHistory.setTimes(exchangePlanHistory.getTimes() + 1);
		exchangeHistoryService.saveOrUpdate(exchangePlanHistory);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("remain", exchangePlan.getNumber());

		return ret;
	}
}
