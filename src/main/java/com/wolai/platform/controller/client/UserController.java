package com.wolai.platform.controller.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;

@Controller
@RequestMapping("/user")
public class UserController {

	@AuthPassport(validate=false)
	@RequestMapping(value="/info")
	@ResponseBody
	public Map<String,Object> info(HttpServletRequest request){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		ret.put("success", "true");
		return ret;
	}
}
