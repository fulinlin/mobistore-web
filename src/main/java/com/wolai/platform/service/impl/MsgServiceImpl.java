package com.wolai.platform.service.impl;

import java.util.Date;

import org.hibernate.FetchMode;
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
	public Page listByUser(String userId, Date after, Date before, int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysMessageSend.class);
		dc.setFetchMode("message", FetchMode.JOIN);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.add(Restrictions.ge("sendTime", after));
		dc.add(Restrictions.le("sendTime", before));
		
		dc.addOrder(Order.desc("sendTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

}
