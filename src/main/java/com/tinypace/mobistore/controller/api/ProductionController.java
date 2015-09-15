package com.tinypace.mobistore.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.entity.MsProduction;
import com.tinypace.mobistore.service.ProductionService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ProductionVo;

@Controller
@RequestMapping(Constant.API)
public class ProductionController extends BaseController {
	@Autowired
	ProductionService productService;

	@RequestMapping(value = "production", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, 
			@RequestParam String startIndex, @RequestParam String pageSize) {
		Map<String, Object> ret = new HashMap<String, Object>();
//		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		Page page = productService.list(0, 10);
		
		List<ProductionVo> ls = new ArrayList<ProductionVo>();
		
		for (Object obj : page.getItems()) {
			MsProduction po = (MsProduction) obj;
			ProductionVo vo = new ProductionVo();
			BeanUtilEx.copyProperties(vo, po);
			
			ls.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", ls);

		return ret;
	}
}
