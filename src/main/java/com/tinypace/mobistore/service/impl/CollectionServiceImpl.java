package com.tinypace.mobistore.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CollectionService;

@Service
public class CollectionServiceImpl extends CommonServiceImpl implements CollectionService {
	@Override
	public Page list(int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrProduct.class);
//		dc.setFetchMode("message", FetchMode.JOIN);
//		dc.add(Restrictions.eq("userId", userId));
//		dc.addOrder(Order.desc("sendTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
}
