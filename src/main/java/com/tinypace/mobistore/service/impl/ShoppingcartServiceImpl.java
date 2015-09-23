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
	public BigDecimal computerShoopingcartPrice(StrShoppingcart cart) {
		BigDecimal amount = new BigDecimal(0);
		for (StrShoppingcartItem i : cart.getItemSet()) {
			amount = amount.add(i.getAmount()) ;
		}
		return amount;
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
		BigDecimal price = product.getDiscountPrice() != null? product.getDiscountPrice(): product.getRetailPrice();
		item.setUnitPrice(price);
		item.setQty(item.getQty() + Integer.valueOf(qty));
		item.setShoppingcartId(cart.getId());
		item.setAmount(item.getUnitPrice().multiply(new BigDecimal(item.getQty())));
		saveOrUpdate(item);
		
		BigDecimal amount = computerShoopingcartPrice(cart);
		cart.setAmount(amount);
		saveOrUpdate(cart);
		
		return cart;
	}
}
