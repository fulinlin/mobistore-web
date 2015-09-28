package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
	public StrOrder make(String orderId, String recipientName, String recipientPhone, String recipientArea, String recipientStreet, String shipAddress) {
//		order.setRecipientName(recipientName);
//		order.setRecipientPhone(recipientPhone);
//		order.setRecipientArea(recipientArea);
//		order.setRecipientStreet(recipientStreet);
//		order.setRecipientAddress(shipAddress);
		
		return null;
	}
	
	@Override
	public List<StrOrderItem> getItems(String orderId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrOrderItem.class);
		dc.add(Restrictions.eq("orderId", orderId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		
		return ls;
	}
}
