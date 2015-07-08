package com.wolai.platform.controller.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.UserService;

@RequestMapping(value="/signon")
public class UserController {
	
	@Autowired
	UserService userService;

	@AuthPassport(validate=true)
	@RequestMapping(value="/signon")
	@ResponseBody
	public Map<String,Object> signon(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String token = vo.get("token");
		String phone = vo.get("phone");
		String password = vo.get("password");

		String newToken = userService.login(token, phone, password);
		if (StringUtils.isNotEmpty(newToken)) {
			ret.put("token", newToken);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
		}
		
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="/signout")
	@ResponseBody
	public Map<String,Object> signout(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = vo.get("phone");

		boolean success = userService.logout(phone);
		if (success) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "user not found");
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="/register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = vo.get("phone");
		String password = vo.get("password");
		String password2 = vo.get("password2");
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || !password.equals(password2)) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "phone or password error");
			return ret;
		}

		Map map = userService.register(phone, password);
		if ((Boolean) map.get("success")) {
			ret.put("token", map.get("token"));
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("msg", map.get("msg"));
			ret.put("code", RespCode.FAIL.Code());
		}
		
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="/profile")
	@ResponseBody
	public Map<String,Object> profile(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret = new HashMap<String, Object>(); 
		
		String phone = vo.get("phone");
		SysUser user = userService.getUserByPhone(phone);
		
		if (user != null) {
			ret.put("code", RespCode.SUCCESS.Code());
			ret.put("phone", user.getMobile());
			ret.put("name", user.getMobile());
		} else {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "not found");
		}
		
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="/updateProfile")
	@ResponseBody
	public Map<String,Object> updateProfile(HttpServletRequest request, @RequestBody Map<String, String> vo){
		Map<String,Object> ret = new HashMap<String, Object>(); 
		
		String phone = vo.get("phone");
		String oldPassword = vo.get("oldPassword");
		String newPassword = vo.get("newPassword");
		String newPassword2 = vo.get("newPassword2");
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(oldPassword) 
				|| StringUtils.isEmpty(newPassword) || !newPassword.equals(newPassword2)) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "phone or password error");
			return ret;
		}
		
		
		Map<String,Object> map = userService.updateProfile(phone, oldPassword, newPassword);
		
		if ((Boolean) map.get("success")) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", map.get("msg"));
		}
		
		return ret;
	}
}
