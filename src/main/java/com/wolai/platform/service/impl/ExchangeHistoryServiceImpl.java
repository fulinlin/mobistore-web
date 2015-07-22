package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.ExchangeHistory;
import com.wolai.platform.entity.ExchangePlan;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.ExchangeHistoryService;
import com.wolai.platform.service.ExchangePlanService;

@Service
public class ExchangeHistoryServiceImpl extends CommonServiceImpl implements ExchangeHistoryService {

	@Override
	public ExchangeHistory getHistoryPers(String userId, String exchangePlanId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExchangeHistory.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("exchangePlanId", exchangePlanId));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		List ls = findAllByCriteria(dc);
		
		if (ls.size() > 0) {
			return (ExchangeHistory) ls.get(0);
		} else {
			ExchangeHistory exchangeHistory = new ExchangeHistory();
			exchangeHistory.setUserId(userId);
			exchangeHistory.setExchangePlanId(exchangePlanId);
			exchangeHistory.setTimes(0);
			saveOrUpdate(exchangeHistory);
			return exchangeHistory;
		}
	}
}