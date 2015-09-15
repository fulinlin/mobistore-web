package com.tinypace.mobistore.service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.SysLoginAccount;

public interface LoginAccountService extends CommonService {

	public SysLoginAccount authLoginAccount(String email,String password);
	
	Page<SysLoginAccount> findAllByPage(SysLoginAccount loginAccount,int start,int limit);
	
	public void saveOrUpdate(SysLoginAccount loginaccount);
}
