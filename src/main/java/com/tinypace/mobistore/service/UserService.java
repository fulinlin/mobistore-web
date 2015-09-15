package com.tinypace.mobistore.service;

import java.util.Map;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.Enterprise;
import com.tinypace.mobistore.entity.SysUser;

public interface UserService extends CommonService {
	
	SysUser saveOrUpdate(SysUser user);

	Map registerPers(String phone, String password);
	SysUser loginWithToken(String token);
	SysUser loginPers(String phone, String password,String deviceType, String deviceToken);
	SysUser logoutPers(String token);
	Map<String, Object> updatePassword(String phone, String password, String newPassword);

	SysUser getUserByPhone(String phone);
	SysUser getUserByPhoneAndPassword(String phone, String password);
	SysUser getUserByToken(String token);

	String createCode(String phone);

	Map<String, Object> resetPasswordPers(String phone, String password);

	Page<SysUser> findAllByPage(SysUser user,int start,int limit);

	boolean validateMobile(String mobile,String userId);
	
	Enterprise getEnterpriceInfo(String userId);
	
	SysUser getTempUserPers();

	void setPayTypePers(SysUser user, String payType);

	Map<String, Object> updateProfile(SysUser user, String nickName);

	
}
