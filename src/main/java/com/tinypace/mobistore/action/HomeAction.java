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

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrAdvert;
import com.tinypace.mobistore.entity.StrCategory;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.AdvertService;
import com.tinypace.mobistore.service.CategoryService;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.AdvertVo;
import com.tinypace.mobistore.vo.CategoryVo;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "home/")
public class HomeAction extends BaseController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AdvertService advertService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "opt/index", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> index(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<StrCategory> pos = categoryService.listAll();
		List<CategoryVo> categories = new ArrayList<CategoryVo>();
		for (StrCategory po : pos) {
			CategoryVo vo = new CategoryVo();
			BeanUtilEx.copyProperties(vo, po);
			categories.add(vo);
		}
		
		Page page1 = advertService.list(0, 5);
		List<AdvertVo> adverts = new ArrayList<AdvertVo>();
		for (Object obj : page1.getItems()) {
			StrAdvert po = (StrAdvert) obj;
			AdvertVo vo = new AdvertVo();
			BeanUtilEx.copyProperties(vo, po);
			adverts.add(vo);
		}
		
		Page page2 = productService.list(0, 10);
		List<ProductVo> products = new ArrayList<ProductVo>();
		for (Object obj : page2.getItems()) {
			StrProduct po = (StrProduct) obj;
			ProductVo vo = new ProductVo();
			BeanUtilEx.copyProperties(vo, po);
			products.add(vo);
		}
		
		ret.put("code", 1);
		ret.put("categories", categories);
		ret.put("adverts", adverts);
		ret.put("products", products);
		
		return ret;
	}
}
