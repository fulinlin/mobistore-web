package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.SysUser;

public interface UserService extends CommonService {
	
	SysUser saveOrUpdate(SysUser user);

	Map registerPers(String phone, String password);
	SysUser loginWithToken(String token);
	SysUser loginPers(String phone, String password);
	SysUser logoutPers(String token);
	Map<String, Object> updateProfilePers(String phone, String password, String newPassword);

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
}
