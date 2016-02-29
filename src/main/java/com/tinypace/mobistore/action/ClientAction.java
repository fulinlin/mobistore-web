package com.tinypace.mobistore.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrClient.AgentType;
import com.tinypace.mobistore.entity.StrSuggestion;
import com.tinypace.mobistore.entity.SysConfig;
import com.tinypace.mobistore.entity.SysVerifyCode;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.SuggestionService;
import com.tinypace.mobistore.service.SysConfigService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ClientVo;

@Controller
@RequestMapping(Constant.API + "client/")
public class ClientAction extends BaseController {
	
	@Autowired
	ClientService clientService;
	@Autowired
	SysConfigService configService;
	@Autowired
	SuggestionService suggestionService;
	
	@AuthPassport(validate=false)
	@RequestMapping(value = "opt/signon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signon(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String mobile = json.get("mobile");
		String password = json.get("password");
		
		String platform = json.get("platform");
		String isWebView = json.get("isWebView");
		String deviceToken = json.get("deviceToken");

		StrClient client = clientService.signonPers(mobile, password, platform, isWebView, deviceToken);
		if (client != null) {
			ret.put("token", client.getAuthToken());
			
			ClientVo vo = new ClientVo();
			BeanUtilEx.copyProperties(vo, client);
			ret.put("data", vo);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "登录失败");
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value = "opt/signup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signup(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String mobile = json.get("mobile");
		String password = json.get("password");
		String platform = json.get("platform");
		String isWebView = json.get("isWebView");
		String deviceToken = json.get("deviceToken");

		StrClient client = clientService.signupPers(mobile, password, platform, isWebView, deviceToken);
		
		if (client != null) {
			ret.put("token", client.getAuthToken());
			
			ClientVo vo = new ClientVo();
			BeanUtilEx.copyProperties(vo, client);
			ret.put("data", vo);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "用户已存在");
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value = "opt/forget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> forget(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String mobile = json.get("mobile");

		SysVerifyCode verifyCode = clientService.forgetPaswordPers(mobile);
		
		if (verifyCode != null) {
			ret.put("data", verifyCode);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "用户不存在");
		}
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value = "opt/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassword(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String verifyCode = json.get("verifyCode");
		String mobile = json.get("mobile");
		String password = json.get("password");
		String platform = json.get("platform");
		String isWebView = json.get("isWebView");
		String deviceToken = json.get("deviceToken");

		StrClient client = clientService.resetPasswordPers(verifyCode, mobile, password, platform, isWebView, deviceToken);
		
		if (client != null) {
			ret.put("token", client.getAuthToken());
			
			ClientVo vo = new ClientVo();
			BeanUtilEx.copyProperties(vo, client);
			ret.put("data", vo);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "重置密码失败");
		}
		
		return ret;
	}
	
	@RequestMapping(value = "opt/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String mobile = json.get("mobile");
		String nickName = json.get("nickName");
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		client.setMobile(mobile);
		client.setNickName(nickName);
		clientService.saveOrUpdate(client);
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
	@RequestMapping(value = "opt/suggest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> suggest(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String content = json.get("content");
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);

		suggestionService.saveSuggestion(client.getId(), content);
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
}
