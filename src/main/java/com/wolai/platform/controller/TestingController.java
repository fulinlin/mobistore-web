package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.TestService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.UserVo;

@Controller
@RequestMapping("test/")
public class TestingController extends BaseController{
	@Autowired
	UserService userService;
	
	@Autowired
	TestService testService;
	
	@AuthPassport(validate=false)
	@RequestMapping("7e6d54c2-4db9-459b-8c02-5a0f1a07ff73")
	public String index(HttpServletRequest request, Model model){
		testService.initRemoteUrl(request);
		model.addAttribute("thirdPartPath", Constant.THIRD_PART_SERVER);
		model.addAttribute("webPath", Constant.WEB_PATH);
		
		String token = request.getParameter("token");
		List<SysUser> users = testService.listTestUsers();
		model.addAttribute("users", users);
		
		if (token == null) {
			return "test/index"; 
		}
		SysUser user = userService.getUserByToken(token);
		if (user == null) {
			return "test/index"; 
		}
		model.addAttribute("user", user.getId());
		List<License> carsOutList = testService.listCarsOut(user.getId());
		model.addAttribute("carsOutList", carsOutList);
		
		List<License> carsInList = testService.listCarsIn(user.getId());
		model.addAttribute("carsInList", carsInList);
		
		return "test/index";
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="bound")
	@ResponseBody
	public Map<String,Object> bound(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String url = json.get("url");
		String token = json.get("token");
		String thirdPartPath = json.get("thirdPartPath");
		
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(token) || StringUtils.isEmpty(thirdPartPath)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		String res = testService.bound(url, token, request);
		Constant.WEB_PATH = url;
		Constant.THIRD_PART_SERVER = thirdPartPath;
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("msg", res);
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
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}

		SysUser user = testService.loginNotUpdateTokenPers(phone, password);
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
	@RequestMapping(value="enter")
	@ResponseBody
	public Map<String,Object> enter(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carNo = json.get("carNo");
		if (StringUtils.isEmpty(carNo)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String res = testService.enter(carNo).replaceAll("\\\\", "");
		if (res.startsWith("\"")) {
			res = res.substring(1, res.length() - 1);
		}
		JSONObject map = JSONObject.parseObject(res);
		int code = map.getInteger("code");
		
		ret.put("code", code);
		ret.put("msg", res);
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="payCheck")
	@ResponseBody
	public Map<String,Object> payCheck(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carNo = json.get("carNo");
		if (StringUtils.isEmpty(carNo)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String res = testService.payCheck(carNo);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("msg", res);
		
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="leave")
	@ResponseBody
	public Map<String,Object> leave(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carNo = json.get("carNo");
		String action = json.get("action");
		if (StringUtils.isEmpty(carNo) || StringUtils.isEmpty(action)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String res = testService.leave(carNo, action);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("msg", res);
		
		return ret;
	}
}
