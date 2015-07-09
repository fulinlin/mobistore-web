package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.entity.SysUser;

public interface UserService extends CommonService {
	
	String login(String email, String password);
	String loginWithToken(String token);

	SysUser saveOrUpdate(SysUser user);

	boolean logout(String token);

	Map create(String phone, String password);

	SysUser getUserByPhone(String phone);
	SysUser getUserByPhoneAndPassword(String phone, String password);

	Map<String, Object> updateProfile(String phone, String oldPassword, String newPassword);

	SysUser getUserByToken(String token);

}
