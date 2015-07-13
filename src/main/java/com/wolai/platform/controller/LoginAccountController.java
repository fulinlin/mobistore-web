package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.service.LoginAccountService;

@Controller
@RequestMapping("/login")
public class LoginAccountController {

	@Autowired
	private LoginAccountService  loginAccountService;
	
	@AuthPassport(validate=false)
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> test(String userName,String password){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		SysLoginAccount account=  loginAccountService.authLoginAccount(userName.trim(), password.trim());
		if(account!=null){
			//TODO
		}
		ret.put("success", true);
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping("/prepareLogin")
	public ModelAndView prepareLogin(){
		ModelAndView mv = new ModelAndView("login/prepareLogin");
		mv.addObject("isValidateCodeLogin", true);
		return mv;
	}
}
