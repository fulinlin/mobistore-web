package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.entity.SysDict;

@Controller
@RequestMapping("/login")
public class LoginAccountController {
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> test(String userName,String Password){
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("success", true);
		
		SysDict ditc = new SysDict();
		ditc.setCode("123");
		ditc.setValue("1111");
		
		return ret;
	}
}
