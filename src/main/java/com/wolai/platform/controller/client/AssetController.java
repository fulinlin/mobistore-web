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
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Integral;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.IntegralService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.CouponVo;
import com.wolai.platform.vo.IntegralVo;
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
	IntegralService integralService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		List<Coupon> couponList = couponService.listByUser(userId);
		List<Integral> integralList = integralService.listByUser(userId);
		
		List<CouponVo> couponVoList = new ArrayList<CouponVo>();
		List<IntegralVo> integralVoList = new ArrayList<IntegralVo>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (Coupon po : couponList) {
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, po);
			couponVoList.add(vo);
		}
		for (Integral po : integralList) {
			IntegralVo vo = new IntegralVo();
			BeanUtilEx.copyProperties(vo, po);
			integralVoList.add(vo);
		}
		
		map.put("coupons", couponVoList);
		map.put("integrals", integralVoList);
		return map;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public Object detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		String itemId = json.get("itemId");
		String itemType = json.get("itemType");
		
		if ("coupon".equals(itemType)) {
			Coupon coupon = (Coupon) couponService.get(Coupon.class, itemId);
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, coupon);
			return vo;
		} else if("integral".equals(itemType)) {
			Integral integral = (Integral) integralService.get(Coupon.class, itemId);
			IntegralVo vo = new IntegralVo();
			BeanUtilEx.copyProperties(vo, integral);
			return vo;
		}
		
		Map<String,Object> ret =new HashMap<String, Object>(); 
		ret.put("code", RespCode.FAIL.Code());
		ret.put("msg", "not found");
		return ret;
	}
}
