package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginAccountController {

	@RequestMapping("/1")
	@ResponseBody
	public Map<String,Object> test(){
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("success", true);
		return ret;
	}
}
