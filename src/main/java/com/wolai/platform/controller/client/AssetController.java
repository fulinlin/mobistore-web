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
import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.RewardPoints;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.RewardPointsService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.CouponVo;
import com.wolai.platform.vo.RewardPointsVo;
import com.wolai.platform.vo.ParkingLotVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "asset/")
public class AssetController {
	@Autowired
	UserService userService;
	
	@Autowired
	AssetService assetService;
	@Autowired
	CouponService couponService;
	@Autowired
	RewardPointsService rewardPointsService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		Page couponPage = couponService.listByUser(userId);
		RewardPoints rewardPoints = rewardPointsService.getByUser(userId);
		
		List<CouponVo> couponVoList = new ArrayList<CouponVo>();
		
		
		for (Object obj : couponPage.getItems()) {
			Coupon po = (Coupon) obj;
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, po);
			couponVoList.add(vo);
		}

		RewardPointsVo rewardPointsVo = new RewardPointsVo();
		BeanUtilEx.copyProperties(rewardPointsVo, rewardPoints);
		
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		Map<String, Object> data = new HashMap<String, Object>();
		ret.put("code", RespCode.SUCCESS.Code());
		data.put("coupons", couponVoList);
		data.put("rewardPoints", rewardPointsVo);
		ret.put("data", data);
		return ret;
	}
}
