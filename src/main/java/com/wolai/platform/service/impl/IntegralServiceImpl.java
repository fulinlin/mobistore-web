package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Integral;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.IntegralService;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.UserService;

@Service
public class IntegralServiceImpl extends CommonServiceImpl implements IntegralService {

	@Override
	public List<Integral> listByUser(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Integral.class);
		dc.add(Restrictions.eq("ownerId", userId));
		List<Integral> coupons = (List<Integral>) findAllByCriteria(dc);
		
		return coupons;
	}
}