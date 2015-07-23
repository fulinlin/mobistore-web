package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.service.PromotionService;

@Service
public class PromotionServiceImpl extends CommonServiceImpl implements PromotionService {

	@Override
	public Page list(int startIndex, int pageSize) {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
		
		dc.add(Restrictions.eq("recommended", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		dc.addOrder(Order.asc("startTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public List listRecommended() {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
		
		dc.add(Restrictions.eq("recommended", true));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		dc.addOrder(Order.asc("startTime"));
		Page page = findPage(dc, 0, 10);
		
		return page.getItems();
	}

}
