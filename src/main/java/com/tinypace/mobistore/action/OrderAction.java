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
	@RequestMapping(value = "opt/checkout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkout(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		String recipientName = json.get("name");
		String recipientPhone = json.get("phone");
		String recipientProvince = json.get("province");
		String recipientCity = json.get("city");
		String recipientAddress = json.get("address");
		StrOrder order = orderService.checkoutPers(client.getId(), recipientName, 
				recipientPhone, recipientProvince, recipientCity, recipientAddress);
		
		OrderVo vo = genOrderVo(order);
		
		ret.put("data", vo);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	private OrderVo genOrderVo(StrOrder order) {
		
		OrderVo orderVo = new OrderVo();
		BeanUtilEx.copyProperties(orderVo, order);
		Set<OrderItemVo> itemVos = new HashSet<OrderItemVo>();
		orderVo.setItems(itemVos);
		
		for (StrOrderItem po : order.getItemSet()) {
			OrderItemVo vo = new OrderItemVo();
			BeanUtilEx.copyProperties(vo, po);
			
			itemVos.add(vo);
		}
		return orderVo;
	}
}
