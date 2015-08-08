package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SysCarBrand;
import com.wolai.platform.entity.SysCarModel;
import com.wolai.platform.service.CarService;

@Service
public class CarServiceImpl extends CommonServiceImpl implements CarService {

	@Override
	public List<SysCarBrand> listBrand() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysCarBrand.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		
		dc.addOrder(Order.desc("name"));
		List<SysCarBrand> ls = (List<SysCarBrand>) findAllByCriteria(dc);
		
		return ls;
	}
	
	@Override
	public List<SysCarModel> listModelByBrand(String brandId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysCarModel.class);
		dc.add(Restrictions.eq("brandId", brandId));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		
		dc.addOrder(Order.desc("name"));
		List<SysCarModel> ls = (List<SysCarModel>) findAllByCriteria(dc);
		
		return ls;
	}

}
