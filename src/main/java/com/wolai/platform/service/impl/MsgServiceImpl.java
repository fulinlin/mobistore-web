package com.wolai.platform.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.SysMessageSend;
import com.wolai.platform.service.MsgService;

@Service
public class MsgServiceImpl extends CommonServiceImpl implements MsgService {

	@Override
	public Page listByUser(String userId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysMessageSend.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.addOrder(Order.desc("sendTime"));
		Page page = findPage(dc, 0, 1000);
		
		return page;
	}

}
