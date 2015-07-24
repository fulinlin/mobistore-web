package com.wolai.platform.service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.SysLoginAccount;

public interface LoginAccountService extends CommonService {

	public SysLoginAccount authLoginAccount(String email,String password);
	
	Page<SysLoginAccount> findAllByPage(SysLoginAccount loginAccount,int start,int limit);
	
	public void saveOrUpdate(SysLoginAccount loginaccount);
}
