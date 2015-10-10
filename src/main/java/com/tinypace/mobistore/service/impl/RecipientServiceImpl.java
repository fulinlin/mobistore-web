package com.tinypace.mobistore.service.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.service.RecipientService;

@Service
public class RecipientServiceImpl extends CommonServiceImpl implements RecipientService {
	@Override
	public List<StrRecipient> list(String clientId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrRecipient.class);
		dc.add(Restrictions.eq("clientId", clientId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		return ls;
	}
}
