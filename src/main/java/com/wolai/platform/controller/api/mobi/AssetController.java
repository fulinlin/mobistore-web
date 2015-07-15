package com.wolai.platform.controller.api.mobi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.RewardPoints;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.RewardPointsService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.CouponVo;
import com.wolai.platform.vo.RewardPointsVo;

@Controller
@RequestMapping(Constant.API_MOBI + "asset/")
public class AssetController extends BaseController {
	@Autowired
	UserService userService;
	
	@Autowired
	AssetService assetService;
	@Autowired
	CouponService couponService;
	@Autowired
	RewardPointsService rewardPointsService;

	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();

		long moneyCount = couponService.countMoneyByUser(userId);
		long timeCount = couponService.countTimeByUser(userId);
		
		RewardPoints rewardPoints = rewardPointsService.getByUser(userId);

		RewardPointsVo rewardPointsVo = new RewardPointsVo();
		BeanUtilEx.copyProperties(rewardPointsVo, rewardPoints);
		
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		Map<String, Object> data = new HashMap<String, Object>();
		ret.put("code", RespCode.SUCCESS.Code());
		
		data.put("rewardPoints", rewardPointsVo);
		data.put("moneyCouponCount", moneyCount);
		data.put("timeCouponCount", timeCount);
		ret.put("data", data);
		return ret;
	}
}
