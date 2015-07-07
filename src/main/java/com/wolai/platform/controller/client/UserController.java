package com.wolai.platform.controller.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant.RespCode;

public class UserController {

	@AuthPassport(validate=false)
	@RequestMapping(value="/signon")
	@ResponseBody
	public Map<String,Object> signon(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String token = vo.get("token");
		String account = vo.get("account");
		String password = vo.get("password");
		
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="/signout")
	@ResponseBody
	public Map<String,Object> signout(HttpServletRequest request){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="/signup")
	@ResponseBody
	public Map<String,Object> signup(HttpServletRequest request){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="/info")
	@ResponseBody
	public Map<String,Object> info(HttpServletRequest request){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="/profile")
	@ResponseBody
	public Map<String,Object> profile(HttpServletRequest request){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		ret.put("success", "true");
		return ret;
	}
}
