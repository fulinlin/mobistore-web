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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.TestService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.UserVo;

@Controller
@RequestMapping("test/")
public class TestingController extends BaseController{
	
	@Autowired
	TestService testService;
	
	@AuthPassport(validate=false)
	@RequestMapping("7e6d54c2-4db9-459b-8c02-5a0f1a07ff73")
	public String prepareLogin(RedirectAttributes attr, Model model){
		
		model.addAttribute("token", "b1d4163f9829453d9aeed855b02b4cbc");
		
		List<SysUser> users = testService.listTestUsers();
		model.addAttribute("user", users.get(1).getId());
		model.addAttribute("users", users);
		
		List<License> carsOutList = testService.listCarsOut();
		model.addAttribute("carsOutList", carsOutList);
		String carToIn = "";
		if (carsOutList.size() > 0) {
			carToIn = carsOutList.get(0).getId();
		}
		model.addAttribute("carToIn", carToIn);
		
		List<License> carsInList = testService.listCarsIn();
		model.addAttribute("carsInList", carsInList);
		String carToOut = "";
		if (carsInList.size() > 0) {
			carToOut = carsInList.get(0).getId();
		}
		model.addAttribute("carToOut", carToOut);
		
		List<ParkingLot> lots = testService.listParkingLot();
		model.addAttribute("lots", lots);
		String lotToIn = "";
		if (lots.size() > 0) {
			lotToIn = carsOutList.get(0).getId();
		}
		model.addAttribute("lotToIn", lotToIn);
		
		return "test/index";
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
	
	@RequestMapping(value="enter")
	@ResponseBody
	public Map<String,Object> enter(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carToIn = json.get("carToIn");
		String lotToIn = json.get("lotToIn");
		if (StringUtils.isEmpty(carToIn) || StringUtils.isEmpty(lotToIn)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		ret = testService.enter(carToIn, lotToIn);
		
		return ret;
	}
	
	@RequestMapping(value="exit")
	@ResponseBody
	public Map<String,Object> exit(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carToOut = json.get("carToOut");
		if (StringUtils.isEmpty(carToOut)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		ret = testService.exit(carToOut);
		
		return ret;
	}
}
