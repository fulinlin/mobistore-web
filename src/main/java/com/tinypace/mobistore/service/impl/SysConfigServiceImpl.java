package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrCollection;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.StrOrder.Status;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.SysConfig;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.SysConfigService;

@Service
public class SysConfigServiceImpl extends CommonServiceImpl implements SysConfigService {
	@Override
	public SysConfig getConfig() {
		DetachedCriteria dc = DetachedCriteria.forClass(SysConfig.class);
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (SysConfig) ls.get(0);
		} else {
			return null;
		}
	}
}
