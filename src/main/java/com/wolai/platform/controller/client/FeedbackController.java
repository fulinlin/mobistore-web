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
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.FeedBack;
import com.wolai.platform.entity.Integral;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.FeedbackService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.CouponVo;
import com.wolai.platform.vo.FeedBackVo;
import com.wolai.platform.vo.IntegralVo;
import com.wolai.platform.vo.ParkingLotVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "feedback/")
public class FeedbackController {
	@Autowired
	UserService userService;
	
	@Autowired
	FeedbackService feedbackService;
	
	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public FeedBackVo submit(HttpServletRequest request, @RequestBody Map<String, String> json){
		String id = json.get("id");
		FeedBack po = new FeedBack();
		FeedBack po = (FeedBack) feedbackService.get(ParkingLot.class, id);

		FeedBackVo vo = new FeedBackVo();
		BeanUtilEx.copyProperties(vo, po);
		
		return vo;
	}
}
