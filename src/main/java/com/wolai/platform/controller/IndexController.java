package com.wolai.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/index")
public class IndexController extends BaseController {
	
	@RequestMapping("/")
	public String index(){
		return "index/index";
	}
}
