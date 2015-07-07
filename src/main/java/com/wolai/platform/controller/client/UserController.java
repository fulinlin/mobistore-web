package com.wolai.platform.controller.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.UserService;

public class UserController {
	
	@Autowired
	UserService userService;

	@AuthPassport(validate=false)
	@RequestMapping(value="/signon")
	@ResponseBody
	public Map<String,Object> signon(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String token = vo.get("token");
		String email = vo.get("email");
		String password = vo.get("password");

		String newToken = userService.login(token, email, password);
		if (StringUtils.isNotEmpty(newToken)) {
			ret.put("token", newToken);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
		}
		
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
