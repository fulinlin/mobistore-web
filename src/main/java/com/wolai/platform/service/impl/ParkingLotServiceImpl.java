package com.wolai.platform.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.service.ParkingLotService;

@Service
public class ParkingLotServiceImpl extends CommonServiceImpl implements ParkingLotService {

	@Override
	public Page listByCity(String city, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingLot.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("city", city));
		return findPage(dc, startIndex, pageSize);
	}

	@Override
	public void save(Object o) {
		getDao().save(o);
	}

	@Override
	public Page findAllByPage(ParkingLot parkingLot, int start, int limit) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingLot.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		if(parkingLot !=null){
			if(StringUtils.isNotBlank(parkingLot.getName())){
				dc.add(Restrictions.like("name",parkingLot.getName(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(parkingLot.getAddress())){
				dc.add(Restrictions.like("address",parkingLot.getAddress(),MatchMode.ANYWHERE));
			}
			if(parkingLot.getIsProprietary()!=null){
				dc.add(Restrictions.eq("isProprietary",parkingLot.getIsProprietary()));
			}
			
			if(StringUtils.isNotBlank(parkingLot.getCity())){
				dc.add(Restrictions.eq("city",parkingLot.getCity()));
			}
		}
		return findPage(dc, start, limit);
	}
}
