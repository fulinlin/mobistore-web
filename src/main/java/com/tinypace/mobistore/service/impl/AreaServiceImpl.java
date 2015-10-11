package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.tinypace.mobistore.entity.SysArea;
import com.tinypace.mobistore.service.AreaService;

@Service
public class AreaServiceImpl extends CommonServiceImpl implements AreaService {
	@Override
	public List<SysArea> list(String type, String proviceId, String cityId) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysArea.class);
		
		if("region".equals(type)) {
			dc.add(Restrictions.eq("level", 3));
			dc.add(Restrictions.eq("parentid", Integer.valueOf(cityId)));
		} else if ("city".equals(type)) {
			dc.add(Restrictions.eq("level", 2));
			dc.add(Restrictions.eq("parentid", Integer.valueOf(proviceId)));
		} else {
			dc.add(Restrictions.eq("level", 1));
			dc.add(Restrictions.eq("parentid", 0));
		}

		List ls = findAllByCriteria(dc);
		return ls;
	}
}
