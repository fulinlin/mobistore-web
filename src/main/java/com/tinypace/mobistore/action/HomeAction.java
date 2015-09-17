package com.tinypace.mobistore.action;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrCategory;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CategoryService;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.CategoryVo;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "home/")
public class HomeAction extends BaseController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "opt/index", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> index(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<StrCategory> pos = categoryService.listAll();
		
		List<CategoryVo> vos = new ArrayList<CategoryVo>();
		
		for (StrCategory po : pos) {
			CategoryVo vo = new CategoryVo();
			BeanUtilEx.copyProperties(vo, po);
			
			vos.add(vo);
		}
		
		ret.put("code", 1);
		ret.put("data", vos);
		return ret;
	}
}
