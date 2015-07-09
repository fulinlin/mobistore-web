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

import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.UserService;

@Service
public class ParkingLotServiceImpl extends CommonServiceImpl implements ParkingLotService {

	@Override
	public List<ParkingLot> listByCity(String city) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingLot.class);
		dc.add(Restrictions.eq("city", city));
		List<ParkingLot> ls = (List<ParkingLot>) findAllByCriteria(dc);
		
		return ls;
	}

	@Override
	public void save(Object o) {
		getDao().save(o);
	}
	

}
