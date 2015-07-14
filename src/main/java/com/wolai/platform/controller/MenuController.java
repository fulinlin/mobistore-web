package com.wolai.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/menu")
public class MenuController extends BaseController {

	@RequestMapping("/menu")
	public  String tree(){
		return "/menu/menu";
	}
}
