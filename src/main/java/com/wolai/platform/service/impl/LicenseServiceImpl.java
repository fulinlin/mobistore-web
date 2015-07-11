package com.wolai.platform.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.License;
import com.wolai.platform.service.LicenseService;

@Service
public class LicenseServiceImpl extends CommonServiceImpl implements LicenseService {

	@Override
	public Page listByUser(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(License.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.addOrder(Order.asc("carNo"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public void create(License po) {
		getDao().saveOrUpdate(po);
		
	}

	@Override
	public void update(License po) {
		getDao().saveOrUpdate(po);
	}
}
