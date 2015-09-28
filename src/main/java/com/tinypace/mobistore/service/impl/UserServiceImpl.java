package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.entity.StrShoppingcartItem;
import com.tinypace.mobistore.service.UserService;

@Service
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	@Override
	public List<StrRecipient> getRecipients(String clientId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrRecipient.class);
		dc.add(Restrictions.eq("clientId", clientId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		
		return ls;
	}
	
	@Override
	public StrRecipient getDefaultRecipient(String clientId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrRecipient.class);
		dc.add(Restrictions.eq("clientId", clientId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.eq("defaultt", true));

		List<StrRecipient> ls = (List<StrRecipient>) findAllByCriteria(dc);
		
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}

}
