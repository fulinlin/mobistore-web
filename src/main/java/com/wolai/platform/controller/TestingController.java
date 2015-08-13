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

import com.wolai.platform.annotation.AuthPassport;
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
//		String carToIn = "";
//		if (carsOutList.size() > 0) {
//			carToIn = carsOutList.get(0).getId();
//		}
//		model.addAttribute("carToIn", carToIn);
		
		List<License> carsInList = testService.listCarsIn(user.getId());
		model.addAttribute("carsInList", carsInList);
//		String carToOut = "";
//		if (carsInList.size() > 0) {
//			carToOut = carsInList.get(0).getId();
//		}
//		model.addAttribute("carToOut", carToOut);
		
//		List<ParkingLot> lots = testService.listParkingLot();
//		model.addAttribute("lots", lots);
//		String lotToIn = "";
//		if (lots.size() > 0) {
//			lotToIn = carsOutList.get(0).getId();
//		}
//		model.addAttribute("lotToIn", lotToIn);
		
		return "test/index";
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="bound")
	@ResponseBody
	public Map<String,Object> bound(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String url = json.get("url");
		String token = json.get("token");
		
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(token)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		String res = testService.bound(url, token);
		
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
		
		String res = testService.enter(carNo);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("msg", res);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="exit")
	@ResponseBody
	public Map<String,Object> exit(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carNo = json.get("carNo");
		if (StringUtils.isEmpty(carNo)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String res = testService.exit(carNo);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("msg", res);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
