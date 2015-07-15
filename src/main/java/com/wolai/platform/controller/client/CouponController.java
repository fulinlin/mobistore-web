package com.wolai.platform.controller.client;

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

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.CouponVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "coupon/")
public class CouponController extends BaseController {
	@Autowired
	UserService userService;
	
	@Autowired
	CouponService couponService;
	
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String, Object> ret = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();

		Page couponPage = couponService.listByUser(userId, startIndex, pageSize);
		List<CouponVo> couponMoneyVoList = new ArrayList<CouponVo>();
		List<CouponVo> couponTimeVoList = new ArrayList<CouponVo>();
		
		for (Object obj : couponPage.getItems()) {
			Coupon po = (Coupon) obj;
			CouponVo vo = new CouponVo();
			BeanUtilEx.copyProperties(vo, po);
			if (CouponType.MONEY.toString().equals(po.getType())) {
				couponMoneyVoList.add(vo);
			} else if(CouponType.TIME.toString().equals(po.getType())) {
				couponTimeVoList.add(vo);
			}
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("money", couponMoneyVoList);
		data.put("time", couponTimeVoList);
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", data);
		return ret;
	}

	@RequestMapping(value="listMoney")
	@ResponseBody
	public Map<String, Object> listMoney(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		Page couponPage = couponService.listMoneyByUser(userId, startIndex, pageSize);
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
	public Map<String, Object> listTime(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		Page couponPage = couponService.listTimeByUser(userId, startIndex, pageSize);
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
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		Coupon coupon = (Coupon) obj;
		CouponVo vo = new CouponVo();
		BeanUtilEx.copyProperties(vo, coupon);
		return vo;
	}

	@AuthPassport(validate=true)
	@RequestMapping(value="use")
	@ResponseBody
	public Object use(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		SysUser user = userService.getUserByToken(token);
		String userId = user.getId();
		String couponId = json.get("id");
		
		ret = couponService.usePers(couponId, userId);

		return ret;
	}
}
