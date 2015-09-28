package com.tinypace.mobistore.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrOrderItem;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.OrderService;
import com.tinypace.mobistore.service.ProductService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.OrderItemVo;
import com.tinypace.mobistore.vo.OrderVo;
import com.tinypace.mobistore.vo.ProductVo;
import com.tinypace.mobistore.vo.ShoppingcartItemVo;
import com.tinypace.mobistore.vo.ShoppingcartVo;

@Controller
@RequestMapping(Constant.API + "order/")
public class OrderAction extends BaseController {
	@Autowired
	OrderService orderService;
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> info(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String orderId = json.get("orderId");
		StrOrder order = (StrOrder) orderService.get(StrOrder.class, orderId);
		
		OrderVo orderVo = genOrderVo(order);
		ret.put("data", orderVo);
		ret.put("code", 1);
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value = "opt/make", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> make(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String orderId = json.get("orderId");
		String recipientName = json.get("name");
		String recipientPhone = json.get("phone");
		String recipientArea = json.get("area");
		String recipientStreet = json.get("street");
		String recipientAddress = json.get("address");
		
		StrOrder order = (StrOrder) orderService.get(StrOrder.class, orderId);
		
		OrderVo orderVo = genOrderVo(order);
		ret.put("data", orderVo);
		ret.put("code", 1);
		return ret;
	}
	
	private OrderVo genOrderVo(StrOrder order) {
		
		OrderVo orderVo = new OrderVo();
		BeanUtilEx.copyProperties(orderVo, order);
		Set<OrderItemVo> itemVos = new HashSet<OrderItemVo>();
		orderVo.setItems(itemVos);
		
		List<StrOrderItem> items = orderService.getItems(order.getId());
		for (StrOrderItem po : items) {
			OrderItemVo vo = new OrderItemVo();
			BeanUtilEx.copyProperties(vo, po);
			
			itemVos.add(vo);
		}
		return orderVo;
	}
}
