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
import com.wolai.platform.entity.Integral;
import com.wolai.platform.entity.License;
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
@RequestMapping(Constant.API_CLIENT + "coupon/")
public class CouponController {
	@Autowired
	UserService userService;
	
	@Autowired
	CouponService couponService;
	
	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		Page couponPage = couponService.listByUser(userId);
		List<CouponVo> couponVoList = new ArrayList<CouponVo>();
		
		for (Object obj : couponPage.getItems()) {
			Coupon po = (Coupon) obj;
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, po);
			couponVoList.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", couponVoList);
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="listMoney")
	@ResponseBody
	public Map<String, Object> listMoney(HttpServletRequest request, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		Page couponPage = couponService.listMoneyByUser(userId);
		List<CouponVo> couponVoList = new ArrayList<CouponVo>();
		
		for (Object obj : couponPage.getItems()) {
			Coupon po = (Coupon) obj;
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, po);
			couponVoList.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", couponVoList);
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="listTime")
	@ResponseBody
	public Map<String, Object> listTime(HttpServletRequest request, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		Page couponPage = couponService.listTimeByUser(userId);
		List<CouponVo> couponVoList = new ArrayList<CouponVo>();
		
		for (Object obj : couponPage.getItems()) {
			Coupon po = (Coupon) obj;
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, po);
			couponVoList.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", couponVoList);
		return ret;
	}


	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public Object detail(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String id = json.get("id");
		
		Object obj = couponService.get(Coupon.class, id);
		if (obj == null) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		Coupon coupon = (Coupon) obj;
		CouponVo vo = new CouponVo();
		BeanUtilEx.copyProperties(vo, coupon);
		return vo;

	}
}
