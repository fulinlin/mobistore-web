package com.wolai.platform.service.impl;

import org.springframework.stereotype.Service;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.entity.SysRLoginAccountRole;
import com.wolai.platform.entity.SysRole;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;

@Service
public class TestService extends BaseServiceImpl {

	public void saveTestData(){
		SysUser user = new SysUser();
		user.setMobile("13382537375");
		user.setCustomerType(UserType.ENTERPRISE);
		getDao().save(user);
		
		SysRole role = new SysRole();
		role.setCode(Constant.ROLE_ADMIN);
		getDao().save(role);
		
		Enterprise enterprise = new Enterprise();
		enterprise.setUserId(user.getId());
		enterprise.setName("喔来科技");
		enterprise.setAddress("........");
		getDao().save(enterprise);
		
		SysLoginAccount loginAccount = new SysLoginAccount();
		loginAccount.setEmail("xuxiang@tinypace.com");
		loginAccount.setPassword("550e1bafe077ff0b0b67f4e32f29d751");
		loginAccount.setUserId(user.getId());
		loginAccount.setStatus(SysLoginAccount.STATUS_NORMAL);
		getDao().save(loginAccount);
		
		SysRLoginAccountRole accountRole = new SysRLoginAccountRole();
		accountRole.setLoginAccountId(loginAccount.getId());
		accountRole.setRoleId(role.getId());
		getDao().save(accountRole);
	}
}
