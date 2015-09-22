package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.service.ShoppingcartService;

@Service
public class ShoppingcartServiceImpl extends CommonServiceImpl implements ShoppingcartService {
	private static Logger log = LoggerFactory.getLogger(ShoppingcartServiceImpl.class);
	
	@Override
	public StrShoppingcart getByClient(String clientId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrShoppingcart.class);
		dc.add(Restrictions.eq("clientId", clientId));
		dc.setFetchMode("itemSet", FetchMode.JOIN);
		List<StrShoppingcart> ls = (List<StrShoppingcart>) findAllByCriteria(dc);
		
		if (ls.size() > 1) {
			log.error("One client should not have more than one shoppingcart.");
		}
		StrShoppingcart cart = ls.get(0);
		
		return cart;
	}
}
