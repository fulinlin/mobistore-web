package com.tinypace.mobistore.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrOrderItem;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.entity.StrSearchHot;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.ShoppingcartService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ShoppingcartItemVo;
import com.tinypace.mobistore.vo.ShoppingcartVo;

@Service
public class ShoppingcartServiceImpl extends CommonServiceImpl implements ShoppingcartService {
	private static Logger log = LoggerFactory.getLogger(ShoppingcartServiceImpl.class);
	
	@Autowired
	UserService userService;
	
	@Override
	public StrShoppingcart getByClient(String clientId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrShoppingcart.class);
		dc.add(Restrictions.eq("clientId", clientId));
		dc.setFetchMode("itemSet", FetchMode.JOIN);
		dc.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<StrShoppingcart> ls = (List<StrShoppingcart>) findAllByCriteria(dc);
		
		StrShoppingcart cart = null;
		if (ls.size() == 1) {
			cart = ls.get(0);
		} else if (ls.size() == 0) {
			cart = new StrShoppingcart();
			cart.setClientId(clientId);
			saveOrUpdate(cart);
		} else {
			log.error("One client should not have more than one shoppingcart.");
		}
		return cart;
	}

	@Override
	public StrShoppingcart addto(String userId, String productId, String qty) {
		StrShoppingcart cart = getByClient(userId);
		List<StrShoppingcartItem> items = getItems(cart.getId());
		
		StrProduct product = (StrProduct) get(StrProduct.class, productId);
		
		StrShoppingcartItem item = null;
		for (StrShoppingcartItem i : items) {
			if (i.getProductId().equals(productId)) {
				item = i;
				break;
			}
		}
		
		if (item == null) {
			item = new StrShoppingcartItem();
//			cart.getItemSet().add(item);
		}
		
		item.setProductId(productId);
		item.setProduct(product);
		item.setQty(item.getQty() + Integer.valueOf(qty));
		BigDecimal unitPrice = product.getDiscountPrice() != null? product.getDiscountPrice(): product.getRetailPrice();
		
		item.setName(product.getName());
		item.setImage(product.getImage());
		item.setFreight(product.getFreight());
		item.setFreightFreeIfTotalAmount(product.getFreightFreeIfTotalAmount());
		item.setUnitPrice(unitPrice);
		item.setShoppingcartId(cart.getId());
		item.setAmount(item.getUnitPrice().multiply(new BigDecimal(item.getQty())));
		saveOrUpdate(item);
		
		cart = computerShoopingcartPricePers(cart.getId());
		
		return cart;
	}

	@Override
	public StrShoppingcart changeQtyPers(String itemId, Integer itemQty) {
		StrShoppingcartItem item = (StrShoppingcartItem) get(StrShoppingcartItem.class, itemId);
		item.setQty(itemQty);
		item.setAmount(item.getUnitPrice().multiply(new BigDecimal(item.getQty())));
		saveOrUpdate(item);
		
		return computerShoopingcartPricePers(item.getShoppingcartId());
	}
	@Override
	public StrShoppingcart removePers(String id, String itemId) {
		StrShoppingcartItem item = (StrShoppingcartItem) get(StrShoppingcartItem.class, itemId);

		item.setIsDelete(true);
		saveOrUpdate(item);
		
		return computerShoopingcartPricePers(item.getShoppingcartId());
	}

	@Override
	public StrShoppingcart clearPers(String clientId) {
		StrShoppingcart cart = getByClient(clientId);
		List<StrShoppingcartItem> items = getItems(cart.getId());
		Iterator<StrShoppingcartItem> it = items.iterator();
		
		while (it.hasNext()) {
			StrShoppingcartItem i = it.next();
			delete(i);
//			cart.getItemSet().remove(i);
		}
		
		return computerShoopingcartPricePers(cart.getId());
	}
	
	@Override
	public StrOrder checkoutPers(String clientId) {
		StrOrder order = new StrOrder();
		
		StrShoppingcart cart = getByClient(clientId);
//		shoppingcartService.computerShoopingcartPrice(cart.getId());
		order.setClientId(clientId);
		order.setAmount(cart.getAmount());
		order.setFreight(cart.getFreight());
		order.setTotalAmount(cart.getTotalAmount());
		order.setCreateTime(new Date());
		
		StrRecipient recipient = userService.getDefaultRecipient(clientId);
		order.setRecipientId(recipient.getId());
		order.setRecipientName(recipient.getName());
		order.setRecipientPhone(recipient.getPhone());
		order.setRecipientArea(recipient.getProvice() + recipient.getCity() + recipient.getRegion());
		order.setRecipientStreet(recipient.getStreet());
		order.setRecipientAddress(recipient.getAddress());
		order.setCreateTime(new Date());
		
		saveOrUpdate(order);
		
		List<StrShoppingcartItem> items = getItems(cart.getId());
		
		for (StrShoppingcartItem i : items) {
			StrOrderItem orderItem = new StrOrderItem();
			orderItem.setName(i.getName());
			orderItem.setImage(i.getImage());
			
			orderItem.setUnitPrice(i.getUnitPrice());
			orderItem.setQty(i.getQty());
			orderItem.setAmount(i.getAmount());
			orderItem.setOrderId(order.getId());

			saveOrUpdate(orderItem);
		}
		
		clearPers(clientId);
		
		return order;
	}
	
	@Override
	public StrShoppingcart computerShoopingcartPricePers(String cartId) {
		StrShoppingcart cart = (StrShoppingcart) get(StrShoppingcart.class, cartId);
		
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalFreight = new BigDecimal(0);
		
		List<StrShoppingcartItem> items = getItems(cartId);
		
		// 算商品
		for (StrShoppingcartItem i : items) {
			totalAmount = totalAmount.add(i.getUnitPrice().multiply(new BigDecimal(i.getQty()))) ;
		}
		cart.setAmount(totalAmount);
		
		// 算运费
		for (StrShoppingcartItem i : items) {
			BigDecimal freeIf = i.getFreightFreeIfTotalAmount();
			BigDecimal freight = i.getFreight();
			if (freeIf != null && totalAmount.subtract(freeIf).doubleValue() > 0 ) { // 减免运费
				freight = new BigDecimal(0);
			}
			totalFreight = totalFreight.add(freight) ;
		}
		totalAmount = totalAmount.add(totalFreight);
		
		cart.setFreight(totalFreight);
		cart.setTotalAmount(totalAmount);
		saveOrUpdate(cart);
		
		return cart;
	}
	
	@Override
	public List<StrShoppingcartItem> getItems(String cartId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrShoppingcartItem.class);
		dc.add(Restrictions.eq("shoppingcartId", cartId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		
		return ls;
	}

}
