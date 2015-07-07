package com.wolai.platform.service;

import com.wolai.platform.entity.SysUser;

public interface UserService extends CommonService {
	
	String login(String token, String email, String password);

	SysUser saveOrUpdate(SysUser user);

}
