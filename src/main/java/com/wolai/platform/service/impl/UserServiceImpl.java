package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.UserService;

@Service
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	@Override
	public SysUser saveOrUpdate(SysUser user) {
		getDao().saveOrUpdate(user);
		
		return user;
	}

	@Override
	public String login(String phone, String password) {
		String newToken = null;
		List<SysUser> users;	

		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("phone", phone));
		dc.add(Restrictions.eq("password", password));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		users = (List<SysUser>) findAllByCriteria(dc);
		
		if (users.size() > 0) {
			SysUser user = users.get(0);
			newToken = UUID.randomUUID().toString();
			user.setAuthToken(newToken);
			user.setLastLoginTime(new Date());
			saveOrUpdate(user);
		} 
		return newToken;
	}
	
	@Override
	public String loginWithToken(String token) {
		String newToken = null;
		List<SysUser> users;
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("authToken", token));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		users = (List<SysUser>) findAllByCriteria(dc);

		return newToken;
	}

	@Override
	public boolean logout(String phone) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("phone", phone));
		List users = (List<SysUser>) findAllByCriteria(dc);
		if (users.size() > 0) {
			SysUser user = (SysUser) users.get(0);
			user.setAuthToken(null);
			saveOrUpdate(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> create(String phone, String password) {
		Map<String, Object> ret = new HashMap<String, Object>(); 
		
		SysUser po = getUserByPhone(phone);
		if (po != null) {
			ret.put("success", false);
			ret.put("msg", "already exist");
			return ret;
		}
		
		SysUser user = new SysUser();
		user.setMobile(phone);
		user.setPassword(password);
		user.setCustomerType(UserType.INDIVIDUAL);
		String newToken = UUID.randomUUID().toString();
		user.setAuthToken(newToken);
		user.setLastLoginTime(new Date());
		saveOrUpdate(user);
		
		ret.put("token", newToken);
		ret.put("success", true);
		return ret;
	}

	@Override
	public SysUser getUserByPhone(String phone) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("mobile", phone));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser) users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser getUserByToken(String token) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("authToken", token));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser) users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser getUserByPhoneAndPassword(String phone, String password) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("mobile", phone));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser) users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> updateProfile(String phone, String oldPhone,String newPassword) {
		Map<String, Object> ret = new HashMap<String, Object>(); 

		SysUser po = getUserByPhoneAndPassword(phone, oldPhone);
		if (po == null) {
			ret.put("success", false);
			ret.put("msg", "user not found");
			return ret;
		}
		
		SysUser user = new SysUser();
		user.setMobile(phone);
		user.setPassword(newPassword);
		user.setCustomerType(UserType.INDIVIDUAL);
		String newToken = UUID.randomUUID().toString();
		user.setAuthToken(newToken);
		user.setLastLoginTime(new Date());
		saveOrUpdate(user);
		
		ret.put("token", newToken);
		ret.put("success", true);
		return ret;
	}
    
}
