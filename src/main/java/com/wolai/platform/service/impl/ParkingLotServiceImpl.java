package com.wolai.platform.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.service.ParkingLotService;

@Service
public class ParkingLotServiceImpl extends CommonServiceImpl implements ParkingLotService {

	@Override
	public Page listByCity(String city) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingLot.class);
		dc.add(Restrictions.eq("city", city));
		Page page = findPage(dc, 0, 1000);
		
		return page;
	}

	@Override
	public void save(Object o) {
		getDao().save(o);
	}
	

}
