package com.tinypace.mobistore.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrAdvert;
import com.tinypace.mobistore.entity.StrCategory;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.service.ShoppingcartService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.AdvertVo;
import com.tinypace.mobistore.vo.CategoryVo;
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
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		ret.put("data", data);
		
//		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		String pageNumb = json.get("pageNumb");
		if (StringUtils.isBlank(pageNumb)) {
			pageNumb = "0";
		}

		Page page = productService.find(Integer.valueOf(pageNumb));
		List<ProductVo> products = new ArrayList<ProductVo>();
		for (Object obj : page.getItems()) {
			StrProduct po = (StrProduct) obj;
			ProductVo vo = new ProductVo();
			BeanUtilEx.copyProperties(vo, po);
			products.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		data.put("products", products);
		
		return ret;
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		String productId = json.get("productId");
		
		StrProduct po = (StrProduct) productService.get(StrProduct.class, productId);
		
		ProductVo vo = new ProductVo();
		BeanUtilEx.copyProperties(vo, po);
		
		boolean isCollected = clientService.isCollected(user.getId(), productId) != null;
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		ret.put("isCollected", isCollected);
		
		return ret;
	}
	
	@RequestMapping(value = "collect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collect(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String productId = json.get("productId");
		
		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		boolean isCollected = clientService.collectIfNeedPers(user.getId(), productId);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", isCollected);
		
		return ret;
	}
	
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
	
}
