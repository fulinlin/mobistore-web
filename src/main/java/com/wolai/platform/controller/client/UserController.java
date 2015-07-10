package com.wolai.platform.controller.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.UserService;

@Controller
@RequestMapping(Constant.API_CLIENT + "user/")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@AuthPassport(validate=false)
	@RequestMapping(value="register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		Map map = userService.registerPers(phone, password);
		if ((Boolean) map.get("success")) {
			ret.put("token", map.get("token"));
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("msg", map.get("msg"));
			ret.put("code", RespCode.FAIL.Code());
		}
		
		return ret;
	}

	@AuthPassport(validate=false)
	@RequestMapping(value="login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		String newToken = userService.loginPers(phone, password);
		if (StringUtils.isNotEmpty(newToken)) {
			ret.put("token", newToken);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="loginWithToken")
	@ResponseBody
	public Map<String,Object> loginWithToken(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		if (StringUtils.isEmpty(token)) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		boolean success = userService.loginWithToken(token);
		if (success) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
		}
		
		return ret;
	}

	@RequestMapping(value="logout")
	@ResponseBody
	public Map<String,Object> logout(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 

		boolean success = userService.logoutPers(token);
		if (success) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "user not found");
		}
		
		return ret;
	}
	
	@RequestMapping(value="profile")
	@ResponseBody
	public Map<String,Object> profile(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret = new HashMap<String, Object>(); 
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
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
	@RequestMapping(value="updateProfile")
	@ResponseBody
	public Map<String,Object> updateProfile(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		String newPassword = json.get("newPassword");
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) 
				|| StringUtils.isEmpty(newPassword)) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Map<String,Object> map = userService.updateProfilePers(phone, password, newPassword);
		if ((Boolean) map.get("success")) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", map.get("msg"));
		}
		
		return ret;
	}
}
