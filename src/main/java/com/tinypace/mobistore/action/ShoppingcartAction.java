package com.tinypace.mobistore.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.service.ShoppingcartService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.OrderVo;
import com.tinypace.mobistore.vo.RecipientVo;
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
	public Map<String, Object> info(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		StrShoppingcart cart = shoppingcartService.getByClient(client.getId());
		
		ShoppingcartVo carVo = genShoppingcartVo(cart, client);
		ret.put("data", carVo);
		ret.put("code", 1);
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/addto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addto(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		String productId = json.get("productId");
		String qty = json.get("qty");
		
		StrShoppingcart cart = shoppingcartService.addto(client.getId(), productId, qty);
		StrShoppingcart cart1 = (StrShoppingcart) shoppingcartService.get(StrShoppingcart.class, cart.getId());
		
		ShoppingcartVo carVo = genShoppingcartVo(cart, client);
		ret.put("data", carVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/changeQty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeQty(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		String itemId = json.get("itemId");
		String itemQty = json.get("itemQty");
		
		StrShoppingcart cart = shoppingcartService.changeQtyPers(itemId, Integer.valueOf(itemQty));
		
		ShoppingcartVo carVo = genShoppingcartVo(cart, client);
		ret.put("data", carVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> remove(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		String itemId = json.get("itemId");
		
		StrShoppingcart cart = shoppingcartService.removePers(client.getId(), itemId);
		
		ShoppingcartVo carVo = genShoppingcartVo(cart, client);
		ret.put("data", carVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/clear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clear(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		StrShoppingcart cart = shoppingcartService.clearPers(client.getId());
		
		ShoppingcartVo carVo = genShoppingcartVo(cart, client);
		ret.put("data", carVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/checkout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkout(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);

		StrOrder order = shoppingcartService.checkoutPers(client.getId());
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("orderId", order.getId());
		return ret;
	}
	
	private ShoppingcartVo genShoppingcartVo(StrShoppingcart cart, StrClient client) {
		
		ShoppingcartVo cartVo = new ShoppingcartVo();
		BeanUtilEx.copyProperties(cartVo, cart);
		
		Set<ShoppingcartItemVo> itemVos = new HashSet<ShoppingcartItemVo>();
		cartVo.setItems(itemVos);
		
		List<StrShoppingcartItem> items = shoppingcartService.getItems(cart.getId());
		for (StrShoppingcartItem po : items) {
			ShoppingcartItemVo vo = new ShoppingcartItemVo();
			BeanUtilEx.copyProperties(vo, po);
			
			itemVos.add(vo);
		}
		
		Set<RecipientVo> addressVos = new HashSet<RecipientVo>();
		cartVo.setAddresses(addressVos);
		for (StrRecipient po : client.getAddressSet()) {
			RecipientVo vo = new RecipientVo();
			BeanUtilEx.copyProperties(vo, po);
			
			addressVos.add(vo);
		}
		
		return cartVo;
	}
}
