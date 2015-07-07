package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.UserService;

@Service
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	@Override
	public SysUser saveOrUpdate(SysUser user) {
		getDao().saveOrUpdate(user);
		
		return user;
	}

	@Override
	public String login(String token, String email, String password) {
		String newToken = null;
		List<SysUser> users;
		
		if (StringUtils.isNotEmpty(token)) {
			DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
			dc.add(Restrictions.eq("authToken", token));
			users = (List<SysUser>) findAllByCriteria(dc);
		} else {
			DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
			dc.add(Restrictions.eq("email", email));
			dc.add(Restrictions.eq("password", password));
			users = (List<SysUser>) findAllByCriteria(dc);
		}
		if (users.size() > 0) {
			SysUser user = users.get(0);
			newToken = UUID.randomUUID().toString();
			user.setAuthToken(newToken);
			user.setLastLoginTime(new Date());
			saveOrUpdate(user);
		} 
		return newToken;
	}
    
}
