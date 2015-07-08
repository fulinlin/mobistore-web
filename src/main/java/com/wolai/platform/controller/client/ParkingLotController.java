package com.wolai.platform.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.UserService;

@RequestMapping(Constant.API_CLIENT + "parkingLot/")
public class ParkingLotController {
	
	@Autowired
	ParkingLotService parkingLotService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String city = vo.get("city");

		List<ParkingLot> ls = parkingLotService.listByCity(city);
		
		return ls;
	}
}
