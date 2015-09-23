package com.tinypace.mobistore.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.service.ShoppingcartService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ShoppingcartItemVo;
import com.tinypace.mobistore.vo.ShoppingcartVo;

@Controller
@RequestMapping(Constant.API + "shoppingcart/")
public class ShoppingcartAction extends BaseController {
	@Autowired
	ProductService productService;
	
	@Autowired
	ShoppingcartService shoppingcartService;

	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doSomething(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		StrShoppingcart cart = shoppingcartService.getByClient(user.getId());
		
		ShoppingcartVo carVo = genShoppingcartVo(cart);
		ret.put("data", carVo);
		ret.put("code", 1);
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/addto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addto(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient user = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		String productId = json.get("productId");
		String qty = json.get("qty");
		
		StrShoppingcart cart = shoppingcartService.addto(user.getId(), productId, qty);
		
		ShoppingcartVo carVo = genShoppingcartVo(cart);
		ret.put("data", carVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	private ShoppingcartVo genShoppingcartVo(StrShoppingcart cart) {
		
		ShoppingcartVo carVo = new ShoppingcartVo();
		BeanUtilEx.copyProperties(carVo, cart);
		Set<ShoppingcartItemVo> itemVos = new HashSet<ShoppingcartItemVo>();
		carVo.setItems(itemVos);
		
		for (StrShoppingcartItem po : cart.getItemSet()) {
			ShoppingcartItemVo vo = new ShoppingcartItemVo();
			BeanUtilEx.copyProperties(vo, po);
			
			itemVos.add(vo);
		}
		return carVo;
	}
}
