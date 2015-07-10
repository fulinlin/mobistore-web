package com.wolai.platform.service.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.service.PromotionService;

@Service
public class PromotionServiceImpl extends CommonServiceImpl implements PromotionService {

	@Override
	public Page listByUser(String userId) {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
		
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		
		Page page = findPage(dc, 0, 1000);
		
		return page;
	}

}
