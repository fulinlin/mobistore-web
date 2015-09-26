package com.tinypace.mobistore.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.ShoppingcartService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ShoppingcartItemVo;
import com.tinypace.mobistore.vo.ShoppingcartVo;

@Service
public class ShoppingcartServiceImpl extends CommonServiceImpl implements ShoppingcartService {
	private static Logger log = LoggerFactory.getLogger(ShoppingcartServiceImpl.class);
	
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
	public StrShoppingcart computerShoopingcartPrice(StrShoppingcart cart) {
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalFreight = new BigDecimal(0);
		
		// 算商品
		for (StrShoppingcartItem i : cart.getItemSet()) {
			totalAmount = totalAmount.add(i.getUnitPrice().multiply(new BigDecimal(i.getQty()))) ;
		}
		cart.setAmount(totalAmount);
		
		// 算运费
		for (StrShoppingcartItem i : cart.getItemSet()) {
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
	public StrShoppingcart addto(String userId, String productId, String qty) {
		StrShoppingcart cart = getByClient(userId);
		StrProduct product = (StrProduct) get(StrProduct.class, productId);
		
		StrShoppingcartItem item = null;
		for (StrShoppingcartItem i : cart.getItemSet()) {
			if (i.getProductId().equals(productId)) {
				item = i;
				break;
			}
		}
		
		if (item == null) {
			item = new StrShoppingcartItem();
			cart.getItemSet().add(item);
		}
		
		item.setProductId(productId);
		item.setProduct(product);
		item.setQty(item.getQty() + Integer.valueOf(qty));
		BigDecimal unitPrice = product.getDiscountPrice() != null? product.getDiscountPrice(): product.getRetailPrice();
		
		item.setName(product.getName());
		item.setFreight(product.getFreight());
		item.setFreightFreeIfTotalAmount(product.getFreightFreeIfTotalAmount());
		item.setUnitPrice(unitPrice);
		item.setShoppingcartId(cart.getId());
		saveOrUpdate(item);
		
		cart = computerShoopingcartPrice(cart);
		saveOrUpdate(cart);
		
		return cart;
	}

	@Override
	public StrShoppingcart changeQtyPers(String userId, String itemId, Integer itemQty) {
		StrShoppingcart cart = getByClient(userId);
		StrShoppingcartItem item = (StrShoppingcartItem) get(StrShoppingcartItem.class, itemId);
		item.setQty(itemQty);
		saveOrUpdate(item);
		
		return computerShoopingcartPrice(cart);
	}
}
