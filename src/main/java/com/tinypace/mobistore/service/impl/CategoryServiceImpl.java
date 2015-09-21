package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrCategory;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CategoryService;

@Service
public class CategoryServiceImpl extends CommonServiceImpl implements CategoryService {
	@Override
	public List<StrCategory> listAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(StrCategory.class);
//		dc.setFetchMode("message", FetchMode.JOIN);
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		dc.addOrder(Order.asc("id"));
		List<StrCategory> ls = (List<StrCategory>) findAllByCriteria(dc);
		
		return ls;
	}
	
	@Override
	public Page listProudct(String categoryId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrProduct.class);

		dc.add(Restrictions.eq("categoryId", categoryId));
		dc.addOrder(Order.desc("promotion"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
}
