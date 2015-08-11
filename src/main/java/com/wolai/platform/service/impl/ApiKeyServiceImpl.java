package com.wolai.platform.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SysAPIKey;
import com.wolai.platform.service.ApiKeyService;

@Service
public class ApiKeyServiceImpl extends CommonServiceImpl implements ApiKeyService {

	@Override
	public SysAPIKey getKeyByParinglotId(String parkinglotId) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysAPIKey.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.eq("parkingLotId", parkinglotId));
		
		return (SysAPIKey) getDao().FindFirstByCriteria(dc);
	}

}
