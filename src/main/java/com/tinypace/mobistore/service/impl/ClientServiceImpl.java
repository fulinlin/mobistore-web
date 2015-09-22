package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.service.ClientService;

@Service
public class ClientServiceImpl extends CommonServiceImpl implements ClientService {
	@Override
	public StrClient getClientByToken(String token) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrClient.class);
		dc.add(Restrictions.eq("authToken", token));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (StrClient) ls.get(0);
		} else {
			return null;
		}
	}
}
