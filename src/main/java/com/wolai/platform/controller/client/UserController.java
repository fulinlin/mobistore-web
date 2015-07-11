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
import com.wolai.platform.service.VerificationService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.UserVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "user/")
public class UserController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	VerificationService verificationService;
	
	@AuthPassport(validate=false)
	@RequestMapping(value="register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		String verificationCode = json.get("verificationCode ");
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(verificationCode)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		if (verificationService.checkCode(phone, verificationCode) == null) {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "无效的验证码");
			return ret;
		}

		Map<String, Object> map = userService.registerPers(phone, password);
		return map;
	}

	@AuthPassport(validate=false)
	@RequestMapping(value="login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		SysUser user = userService.loginPers(phone, password);
		if (user != null) {
			ret.put("token", user.getAuthToken());
			ret.put("code", RespCode.SUCCESS.Code());
			
			UserVo vo = new UserVo();
			BeanUtilEx.copyProperties(vo, user);
			ret.put("data", vo);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "登录失败");
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="loginWithToken")
	@ResponseBody
	public Map<String,Object> loginWithToken(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		if (StringUtils.isEmpty(token)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		SysUser user = userService.loginWithToken(token);
		if (user != null) {
			UserVo vo = new UserVo();
			BeanUtilEx.copyProperties(vo, user);
			ret.put("data", vo);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "loginWithToken fail");
		}
		
		return ret;
	}

	@RequestMapping(value="logout")
	@ResponseBody
	public Map<String,Object> logout(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 

		SysUser user = userService.logoutPers(token);
		if (user != null) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
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
			UserVo vo = new UserVo();
			BeanUtilEx.copyProperties(vo, user);
			
			ret.put("code", RespCode.SUCCESS.Code());
			ret.put("data", vo);
		} else {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
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
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		ret = userService.updateProfilePers(phone, password, newPassword);
		
		return ret;
	}
}
