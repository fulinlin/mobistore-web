package com.wolai.platform.service.impl;

import java.util.Date;

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
	public Page packInfo(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		dc.add(Restrictions.gt("driveInTime", dt));
		dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc.addOrder(Order.desc("driveInTime"));

		Page ls = findPage(dc, 0, 3);
		
		return ls;
	}

	@Override
	public Page packHistory(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc.addOrder(Order.desc("driveInTime"));

		Page ls = findPage(dc, 0, 10);
		
		return ls;
	}
}
