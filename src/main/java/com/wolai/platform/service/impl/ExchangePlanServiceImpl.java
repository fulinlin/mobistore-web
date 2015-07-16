package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.ExchangePlan;
import com.wolai.platform.service.ExchangePlanService;

@Service
public class ExchangePlanServiceImpl extends CommonServiceImpl implements ExchangePlanService {

	@Override
	public List listByPromotion(String promotionId) {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(ExchangePlan.class);
		
		dc.add(Restrictions.eq("promotionId", promotionId));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		dc.addOrder(Order.asc("startTime"));
		
		List ls = findAllByCriteria(dc);
		return ls;
	}

}
