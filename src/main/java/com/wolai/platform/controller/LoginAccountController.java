package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.entity.SysDict;
import com.wolai.platform.service.ParkingLotService;

@Controller
@RequestMapping("/login")
public class LoginAccountController {

	@Autowired
	private ParkingLotService parkingLotService;
	
	@RequestMapping("/1")
	@ResponseBody
	public Map<String,Object> test(){
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("success", true);
		
		SysDict ditc = new SysDict();
		ditc.setCode("123");
		ditc.setValue("1111");
		parkingLotService.save(ditc);
		
		return ret;
	}
}
