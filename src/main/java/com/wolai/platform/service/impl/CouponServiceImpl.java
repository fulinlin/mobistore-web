package com.wolai.platform.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.service.CouponService;

@Service
public class CouponServiceImpl extends CommonServiceImpl implements CouponService {

	@Override
	public Page listByUser(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("ownerId", userId));
		Page page = findPage(dc, 0, 1000);
		
		return page;
	}
}
