package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrAdvert;
import com.tinypace.mobistore.entity.StrCategory;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.AdvertService;
import com.tinypace.mobistore.service.ProductService;

@Service
public class AdvertServiceImpl extends CommonServiceImpl implements AdvertService {
	@Override
	public Page list(int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrAdvert.class);
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		dc.addOrder(Order.asc("id"));
		Page<StrAdvert> page = (Page<StrAdvert>) findPage(dc, startIndex, pageSize);
		
		return page;
	}
}
