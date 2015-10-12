package com.tinypace.mobistore.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CollectionService;
import com.tinypace.mobistore.service.MsgService;

@Service
public class MsgServiceImpl extends CommonServiceImpl implements MsgService {
	@Override
	public Page list(String clientId, int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(StrMsg.class);
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		dc.add(Restrictions.eq("clientId", clientId));
		dc.addOrder(Order.desc("sendTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
}
