package com.wolai.platform.controller.api.mobi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysVerificationCode;
import com.wolai.platform.service.UserService;
import com.wolai.platform.service.VerificationService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.CommonUtils;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.util.SmsUtil;
import com.wolai.platform.util.StringUtil;
import com.wolai.platform.vo.UserVo;

@Controller
@RequestMapping(Constant.API_MOBI + "user/")
public class UserController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	public static String MSG = "【喔来智能停车系统】您正在注册我们的应用，验证码是%CODE%，请在10分钟内提交改验证码，切勿将该验证码泄露于他人。";
	
	@Autowired
	UserService userService;
	
	@Autowired
	VerificationService verificationService;
	
	@AuthPassport(validate=false)
	@RequestMapping(value="verify")
	@ResponseBody
	public Map<String,Object> verify(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		log.info("===测试===");
		String phone = json.get("phone");
		
		if (StringUtils.isEmpty(phone)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		String vcode = userService.createCode(phone);

		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("verifyCode", vcode);
		
		// 发送短信
		String msg = MSG.replaceAll("%CODE%", vcode);
		SmsUtil.send(phone, msg);
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		String verificationCode = json.get("verificationCode");
		
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
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "Token登录失败");
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
			ret.put("msg", "user not found by this token");
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
		
		String action = json.get("action");
		String phone = json.get("phone");
		String name = json.get("name");
		String password = json.get("password");
		String newPassword = json.get("newPassword");
		
		if (StringUtils.isEmpty(action)) {
			action = "password";
		}
		
		if ("password".equals(action)) {
			if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) 
					|| StringUtils.isEmpty(newPassword)) {
				ret.put("code", RespCode.INTERFACE_FAIL.Code());
				ret.put("msg", "parameters error");
				return ret;
			}
			
			ret = userService.updatePassword(phone, password, newPassword);
		} else if ("profile".equals(action))  {
			if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(name)) {
				ret.put("code", RespCode.INTERFACE_FAIL.Code());
				ret.put("msg", "parameters error");
				return ret;
			}
			
			SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
			ret = userService.updateProfile(user, name);
		} else {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameter type must be 'password' or 'profile'");
			return ret;
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="resetPassword")
	@ResponseBody
	public Map<String,Object> resetPassword(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String phone = json.get("phone");
		String password = json.get("password");
		String verificationCode = json.get("verificationCode");
		
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

		Map<String, Object> map = userService.resetPasswordPers(phone, password);
		return map;
	}
	
	@RequestMapping(value="payType")
	@ResponseBody
	public Map<String,Object> payType(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>();
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		String payType = json.get("payType");
		
		if (StringUtils.isEmpty(payType) || SysUser.PayType.value(payType) == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		userService.setPayTypePers(user, payType);
		
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
}
