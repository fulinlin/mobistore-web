package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.entity.SysUser;

public interface UserService extends CommonService {
	
	String login(String token, String email, String password);

	SysUser saveOrUpdate(SysUser user);

	boolean logout(String token);

	Map register(String phone, String password);

	SysUser getUserByPhone(String phone);
	SysUser getUserByPhoneAndPassword(String phone, String password);

	Map<String, Object> updateProfile(String phone, String oldPassword, String newPassword);

	SysUser getUserByToken(String token);

}
