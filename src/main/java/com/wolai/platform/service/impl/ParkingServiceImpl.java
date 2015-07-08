package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.TimeUtils;

@Service
public class ParkingServiceImpl extends CommonServiceImpl implements ParkingService {

	@Override
	public ParkingRecord packInfo(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.lt("driveInTime", TimeUtils.getDateBefore(new Date(), 10)));
		dc.add(Restrictions.ne("userId", Constant.ParkStatus.OUT));
		dc.addOrder(Order.desc("driveInTime"));
		
		List<ParkingRecord> ls = (List<ParkingRecord>) findAllByCriteria(dc);
		
		return ls.get(0);
	}
}
