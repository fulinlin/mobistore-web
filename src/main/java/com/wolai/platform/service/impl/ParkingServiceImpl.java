package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.util.TimeUtils;

@Service
public class ParkingServiceImpl extends CommonServiceImpl implements ParkingService {

	@Override
	public ParkingRecord parkInfo(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		dc.add(Restrictions.gt("driveInTime", dt));
		dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc.addOrder(Order.desc("driveInTime"));

		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (ParkingRecord)ls.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Page parkHistory(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc.addOrder(Order.desc("driveInTime"));

		Page ls = findPage(dc, startIndex, pageSize);
		
		return ls;
	}
	
	
}
