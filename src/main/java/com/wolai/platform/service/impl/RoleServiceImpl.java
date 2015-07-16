package com.wolai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.entity.SysRLoginAccountRole;
import com.wolai.platform.entity.SysRole;
import com.wolai.platform.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Override
	public List<SysRole> getTopRoleByUserId(String userId) {
		  SysLoginAccount user = (SysLoginAccount) getDao().get(SysLoginAccount.class, userId);
		  DetachedCriteria dc = DetachedCriteria.forClass(SysRLoginAccountRole.class);
		  dc.add(Restrictions.eq("loginAccountId", user.getId()));
	      List<SysRLoginAccountRole> userRoles = getDao().findAllByCriteria(dc);
	        List<SysRole> topUserRoles = new ArrayList<SysRole>();
	        
	        for (SysRLoginAccountRole userRole :userRoles) {
	            SysRole role = (SysRole) getDao().get(SysRole.class, userRole.getRoleId());
	            String[] prefixs = role.getRolePrefix().replaceAll("\\{", "").replaceAll("\\}", ",").split(",");
	            role = (SysRole) getDao().get(SysRole.class,prefixs[0]);
	            if (!topUserRoles.contains(role)) {
	                topUserRoles.add(role);
	            }
	        }
	        return topUserRoles;
	}

}
