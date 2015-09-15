package com.tinypace.mobistore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.SysLoginAccount;
import com.tinypace.mobistore.entity.SysRLoginAccountRole;
import com.tinypace.mobistore.entity.SysRole;
import com.tinypace.mobistore.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Override
	public List<SysRole> getTopRoleByUserId(String userId) {
		  SysLoginAccount user = (SysLoginAccount) getDao().get(SysLoginAccount.class, userId);
		  DetachedCriteria dc = DetachedCriteria.forClass(SysRLoginAccountRole.class);
		  dc.setFetchMode("role", FetchMode.JOIN);
		  dc.add(Restrictions.eq("loginAccountId", user.getId()));
	      List<SysRLoginAccountRole> userRoles = getDao().findAllByCriteria(dc);
	        List<SysRole> topUserRoles = new ArrayList<SysRole>();
	        if(userRoles.size()==1){
	        	topUserRoles.add(userRoles.get(0).getRole());
	        	return topUserRoles;
	        }
	        for (SysRLoginAccountRole userRole :userRoles) {
	            SysRole role = (SysRole) getDao().get(SysRole.class, userRole.getRoleId());
	            String[] prefixs = role.getRolePrefix().replaceAll("\\{", "").replaceAll("\\}", ",").split(",");
	            if(prefixs.length>0){
		            role = (SysRole) getDao().get(SysRole.class,prefixs[0]);
		            if (!topUserRoles.contains(role)) {
		                topUserRoles.add(role);
		            }
	            }
	        }
	        return topUserRoles;
	}

}
