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
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String filter = json.get("filter");
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		Page page = orderService.list(filter, client.getId(), 0, 10);
		
		List<OrderVo> ls = new ArrayList<OrderVo>();
		
		for (Object obj : page.getItems()) {
			StrOrder po = (StrOrder) obj;
			OrderVo vo = genOrderVo(po);
			
			ls.add(vo);
		}
		
		ret.put("data", ls);
		ret.put("code", 1);
		return ret;
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> info(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String orderId = json.get("orderId");
		StrOrder order = (StrOrder) orderService.get(StrOrder.class, orderId);
		
		OrderVo orderVo = genOrderVo(order);
		ret.put("data", orderVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@RequestMapping(value = "pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pay(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String orderId = json.get("orderId");
		String recipientId = json.get("recipientId");
		
		StrOrder order = (StrOrder) orderService.get(StrOrder.class, orderId);
		
		OrderVo orderVo = genOrderVo(order);
		ret.put("data", orderVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@RequestMapping(value = "changeRecipient", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeRecipient(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String orderId = json.get("orderId");
		String recipientId = json.get("recipientId");
		StrOrder order = orderService.changeRecipientPers(orderId, recipientId);
		
		OrderVo orderVo = genOrderVo(order);
		ret.put("data", orderVo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancel(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String orderId = json.get("orderId");
		orderService.cancelPers(orderId);
		ret.put("code", RespCode.SUCCESS.Code());
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
