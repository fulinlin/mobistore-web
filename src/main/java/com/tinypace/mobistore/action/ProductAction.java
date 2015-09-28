package com.tinypace.mobistore.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.service.ShoppingcartService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "product/")
public class ProductAction extends BaseController {
	@Autowired
	ClientService clientService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ShoppingcartService shoppingcartService;
	
//	@RequestMapping(value = "model", method = RequestMethod.GET)
//	@ResponseBody
//	public List<ProductVo> query(@RequestParam String startIndex, @RequestParam String pageSize, 
//			HttpServletRequest request) {
//		
//		Page page = productService.list(0, 10);
//		
//		List<ProductVo> ls = new ArrayList<ProductVo>();
//		
//		for (Object obj : page.getItems()) {
//			StrProduct po = (StrProduct) obj;
//			ProductVo vo = new ProductVo();
//			BeanUtilEx.copyProperties(vo, po);
//			
//			ls.add(vo);
//		}
//
//		return ls;
//	}
//	
//	@RequestMapping(value = "model/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public ProductVo get(@PathVariable String id, HttpServletRequest request) {
//		StrProduct po = (StrProduct) productService.get(StrProduct.class, id);
//		
//		ProductVo vo = new ProductVo();
//		BeanUtilEx.copyProperties(vo, po);
//		
//		return vo;
//	}
//	
//	@RequestMapping(value = "model", method = RequestMethod.POST)
//	@ResponseBody
//	public StrProduct save(HttpServletRequest request, @RequestBody ProductVo vo) {
//		StrProduct po = new StrProduct();
//		BeanUtilEx.copyProperties(po, vo);
//		productService.saveOrUpdate(po);
//		return po;
//	}
//	
//	@RequestMapping(value = "model/{id}", method = RequestMethod.POST)
//	@ResponseBody
//	public StrProduct update(HttpServletRequest request, @PathVariable String id, @RequestBody ProductVo vo) {
//		StrProduct po = new StrProduct();
//		BeanUtilEx.copyProperties(po, vo);
//		productService.saveOrUpdate(po);
//		return po;
//	}
//	
//	@RequestMapping(value = "model/{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public Map<String, Object> remove(HttpServletRequest request, @PathVariable String id, @RequestBody ProductVo vo) {
//		StrProduct po = (StrProduct) productService.get(StrProduct.class, id);
//		productService.delete(po);
//		return null;
//	}

	@RequestMapping(value = "opt/get", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		String productId = json.get("productId");
		
		StrProduct po = (StrProduct) productService.get(StrProduct.class, productId);
		
		ProductVo vo = new ProductVo();
		BeanUtilEx.copyProperties(vo, po);
		
		boolean isCollected = clientService.isCollected(user.getId(), productId);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		ret.put("isCollected", isCollected);
		
		return ret;
	}
	
	@RequestMapping(value = "opt/collect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collect(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String productId = json.get("productId");
		
		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		boolean justCollected = clientService.collectIfNeedPers(user.getId(), productId);
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
}
