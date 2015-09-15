package com.tinypace.mobistore.controller.api.mobi;

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

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.Coupon;
import com.tinypace.mobistore.entity.RewardPoints;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.service.AssetService;
import com.tinypace.mobistore.service.CouponService;
import com.tinypace.mobistore.service.RewardPointsService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.CouponVo;
import com.tinypace.mobistore.vo.RewardPointsVo;

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
		
		RewardPoints rewardPoints = rewardPointsService.getByUserPers(userId);

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
