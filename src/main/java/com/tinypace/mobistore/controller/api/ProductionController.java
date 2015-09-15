package com.tinypace.mobistore.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.service.ProductionService;

@Controller
@RequestMapping(Constant.API_WEB + "production/")
public class ProductionController extends BaseController {
	@Autowired
	ProductionService productService;

	@RequestMapping(value = "list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Map<String, String> json) {
//		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);

		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("code", RespCode.SUCCESS.Code());

		return ret;
	}
}
