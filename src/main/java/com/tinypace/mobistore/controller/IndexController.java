package com.tinypace.mobistore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinypace.mobistore.entity.SysUser;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,SysUser user,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,Model model){

		return "index";
	}
	
//	@RequestMapping("demo")
//	public String home(HttpServletRequest request,SysUser user,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,Model model){
//
//		return "demo";
//	}
}
