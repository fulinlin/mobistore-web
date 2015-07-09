package com.wolai.platform.controller.client;

import java.util.ArrayList;
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
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.ParkingLotVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "parking/")
public class ParkingController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ParkingService parkingService;
	
	@AuthPassport(validate=true)
	@RequestMapping(value="packInfo")
	@ResponseBody
	public ParkingLotVo packInfo(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		
		SysUser user = userService.getUserByToken(token);
		String userId = user.getId();
		ParkingRecord po = parkingService.packInfo(userId);

		ParkingLotVo vo = new ParkingLotVo();
		BeanUtilEx.copyProperties(vo, po);
		
		return vo;
	}
	
}
