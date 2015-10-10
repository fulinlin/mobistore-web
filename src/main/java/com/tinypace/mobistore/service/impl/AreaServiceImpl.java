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
	public List<SysArea> list(String type) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysArea.class);
		
		if ("provice".equals(type)) {
			dc.add(Restrictions.eq("parentid", 0));
		}

		List ls = findAllByCriteria(dc);
		return ls;
	}
}
