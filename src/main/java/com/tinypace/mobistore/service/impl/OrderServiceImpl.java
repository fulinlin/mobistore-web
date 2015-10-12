package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrOrderItem;
import com.tinypace.mobistore.entity.StrOrder.Status;
import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.service.OrderService;
import com.tinypace.mobistore.service.ShoppingcartService;

@Service
public class OrderServiceImpl extends CommonServiceImpl implements OrderService {
	@Autowired
	ShoppingcartService shoppingcartService;
	
	@Override
	public Page list(String filter, String clientId, int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrOrder.class);
		dc.setFetchMode("message", FetchMode.JOIN);
		dc.add(Restrictions.eq("clientId", clientId));
		
		if ("waitPay".equals(filter)) {
			dc.add(Restrictions.eq("status", Status.INIT));
		} else if ("waitShip".equals(filter)) {
			dc.add(Restrictions.eq("status", Status.PAID));
		} else if ("waitReceive".equals(filter)) {
			dc.add(Restrictions.eq("status", Status.SHIPPING));
		} else if ("waitRate".equals(filter)) {
			dc.add(Restrictions.eq("status", Status.RECEIVED));
		} 
		
		dc.addOrder(Order.desc("createTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
	
	@Override
	public StrOrder payPers(String orderId, String recipientName, String recipientPhone, String recipientArea, String recipientStreet, String shipAddress) {

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

	@Override
	public void cancelPers(String orderId) {
		StrOrder order = (StrOrder) get(StrOrder.class, orderId);
		order.setStatus(Status.CANCEL);
		saveOrUpdate(order);
	}

	@Override
	public StrOrder changeRecipientPers(String orderId, String recipientId) {
		StrOrder order = (StrOrder) get(StrOrder.class, orderId);
		StrRecipient recipient = (StrRecipient) get(StrRecipient.class, recipientId);
		
		order.setRecipientName(recipient.getName());
		order.setRecipientPhone(recipient.getPhone());
		order.setRecipientArea(recipient.getProvice() + recipient.getCity() + recipient.getRegion());
		order.setRecipientStreet(recipient.getStreet());
		order.setRecipientAddress(recipient.getAddress());
		order.setCreateTime(new Date());
		
		saveOrUpdate(order);
		
		return order;
	}
}
