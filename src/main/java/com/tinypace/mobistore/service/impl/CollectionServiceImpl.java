package com.tinypace.mobistore.service.impl;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrCollection;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CollectionService;

@Service
public class CollectionServiceImpl extends CommonServiceImpl implements CollectionService {

	public Page list(String clientId, int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrCollection.class);
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		dc.add(Restrictions.eq("clientId", clientId));
		dc.setFetchMode("product", FetchMode.JOIN);
		dc.addOrder(Order.desc("collectTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
}
