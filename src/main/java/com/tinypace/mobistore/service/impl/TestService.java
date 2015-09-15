package com.tinypace.mobistore.service.impl;

import org.springframework.stereotype.Service;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.Enterprise;
import com.tinypace.mobistore.entity.SysLoginAccount;
import com.tinypace.mobistore.entity.SysRLoginAccountRole;
import com.tinypace.mobistore.entity.SysRole;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.entity.SysUser.UserType;

@Service("testService")
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
		loginAccount.setEnterpriseId(enterprise.getId());
		getDao().save(loginAccount);
		
		SysRLoginAccountRole accountRole = new SysRLoginAccountRole();
		accountRole.setLoginAccountId(loginAccount.getId());
		accountRole.setRoleId(role.getId());
		getDao().save(accountRole);
	}
}
