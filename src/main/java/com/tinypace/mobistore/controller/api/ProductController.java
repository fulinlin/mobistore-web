package com.tinypace.mobistore.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.entity.MsProduct;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "product/")
public class ProductController extends BaseController {
	@Autowired
	ProductService productService;

	@RequestMapping(value = "model", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductVo> query(@RequestParam String startIndex, @RequestParam String pageSize, 
			HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<String, Object>();
//		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		Page page = productService.list(0, 10);
		
		List<ProductVo> ls = new ArrayList<ProductVo>();
		
		for (Object obj : page.getItems()) {
			MsProduct po = (MsProduct) obj;
			ProductVo vo = new ProductVo();
			BeanUtilEx.copyProperties(vo, po);
			
			ls.add(vo);
		}

		return ls;
	}
	
	@RequestMapping(value = "model/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MsProduct get(@PathVariable String id, HttpServletRequest request) {
		MsProduct po = (MsProduct) productService.get(MsProduct.class, id);
		
		return po;
	}
	
	@RequestMapping(value = "model", method = RequestMethod.POST)
	@ResponseBody
	public MsProduct save(HttpServletRequest request, @RequestBody ProductVo vo) {
		MsProduct po = new MsProduct();
		BeanUtilEx.copyProperties(po, vo);
		productService.saveOrUpdate(po);
		return po;
	}
	
	@RequestMapping(value = "model/{id}", method = RequestMethod.POST)
	@ResponseBody
	public MsProduct update(HttpServletRequest request, @PathVariable String id, @RequestBody ProductVo vo) {
		MsProduct po = new MsProduct();
		BeanUtilEx.copyProperties(po, vo);
		productService.saveOrUpdate(po);
		return po;
	}
	
	@RequestMapping(value = "model/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> remove(HttpServletRequest request, @PathVariable String id, @RequestBody ProductVo vo) {
		MsProduct po = (MsProduct) productService.get(MsProduct.class, id);
		productService.delete(po);
		return null;
	}

	@RequestMapping(value = "opt/doSomething", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doSomething(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("code", 1);
		ret.put("msg", "成功");
		return ret;
	}
}
