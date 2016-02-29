package com.tinypace.mobistore.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.ProductService;

@Service
public class ProductServiceImpl extends CommonServiceImpl implements ProductService {
	@Override
	public Page find(int pageNumb) {
		int startIndex = pageNumb * Constant.PAGE_SIZE;
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrProduct.class);
//		dc.setFetchMode("message", FetchMode.JOIN);
		dc.addOrder(Order.desc("promotion"));
		Page page = findPage(dc, startIndex, Constant.PAGE_SIZE);
		
		return page;
	}
}
