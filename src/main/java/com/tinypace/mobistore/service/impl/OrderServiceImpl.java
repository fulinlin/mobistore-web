package com.tinypace.mobistore.service.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrOrderItem;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.OrderService;
import com.tinypace.mobistore.service.ShoppingcartService;

@Service
public class OrderServiceImpl extends CommonServiceImpl implements OrderService {
	@Autowired
	ShoppingcartService shoppingcartService;
	
	@Override
	public Page list(int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrProduct.class);
//		dc.setFetchMode("message", FetchMode.JOIN);
//		dc.add(Restrictions.eq("userId", userId));
//		dc.addOrder(Order.desc("sendTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
	
	@Override
	public StrOrder checkoutPers(String clientId, String recipientName, String recipientPhone, String recipientProvince, String recipientCity, String shipAddress) {
		StrOrder order = new StrOrder();
		
		StrShoppingcart cart = shoppingcartService.getByClient(clientId);
		shoppingcartService.computerShoopingcartPrice(cart);
		order.setClientId(clientId);
		order.setAmount(cart.getAmount());
		order.setFreight(cart.getFreight());
		order.setTotalAmount(cart.getTotalAmount());
		order.setCreateTime(new Date());
		
		order.setRecipientName(recipientName);
		order.setRecipientPhone(recipientPhone);
		order.setRecipientProvince(recipientProvince);
		order.setRecipientCity(recipientCity);
		order.setRecipientAddress(shipAddress);
		order.setCreateTime(new Date());
		saveOrUpdate(order);
		
		for (StrShoppingcartItem i : cart.getItemSet()) {
			StrOrderItem orderItem = new StrOrderItem();
			orderItem.setName(i.getName());
			orderItem.setImage(i.getImage());
			
			orderItem.setUnitPrice(i.getUnitPrice());
			orderItem.setQty(i.getQty());
			orderItem.setAmount(i.getAmount());
			orderItem.setOrderId(order.getId());
			
			order.getItemSet().add(orderItem);
			saveOrUpdate(orderItem);
		}
		
		return null;
	}
}
