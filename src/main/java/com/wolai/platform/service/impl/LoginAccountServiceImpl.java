package com.wolai.platform.service.impl;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.service.LoginAccountService;

@Service
public class LoginAccountServiceImpl extends CommonServiceImpl implements LoginAccountService {

	@Override
	public SysLoginAccount authLoginAccount(String email, String password) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysLoginAccount.class);
		dc.setFetchMode("user", FetchMode.JOIN);
		dc.add(Restrictions.eq("email", email));
		dc.add(Restrictions.eq("password", password));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		SysLoginAccount account = (SysLoginAccount) getDao().getByCriteria(dc);
		return account;
	}

}
