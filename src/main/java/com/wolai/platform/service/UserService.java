package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.entity.SysUser;

public interface UserService extends CommonService {
	
	SysUser saveOrUpdate(SysUser user);

	Map registerPers(String phone, String password);
	boolean loginWithToken(String token);
	String loginPers(String phone, String password);
	boolean logoutPers(String token);
	Map<String, Object> updateProfilePers(String phone, String password, String newPassword);

	SysUser getUserByPhone(String phone);
	SysUser getUserByPhoneAndPassword(String phone, String password);
	SysUser getUserByToken(String token);

}