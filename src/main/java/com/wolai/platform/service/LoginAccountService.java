package com.wolai.platform.service;

import com.wolai.platform.entity.SysLoginAccount;

public interface LoginAccountService extends CommonService {

	public SysLoginAccount authLoginAccount(String email,String password);
}
