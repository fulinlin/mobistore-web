package com.tinypace.mobistore.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.SysUser;

@Controller("indexController")
@RequestMapping("/")
public class ClientController extends BaseController {
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,SysUser user,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,Model model){
//		if(pageNo==null){
//			pageNo=1;
//		}
//		
//		if(pageSize==null){
//			pageSize=limit;
//		}
//		
//		user.setCustomerType(UserType.INDIVIDUAL);
//		
//		page = userService.findAllByPage(user, (pageNo-1)*pageSize, pageSize);
//		model.addAttribute("page", page);
//		model.addAttribute("user", user);
		return "index";
	}
}
