package com.wolai.platform.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl extends CommonServiceImpl implements EnterpriseService {

    @Override
    @SuppressWarnings("unchecked")
    public Enterprise getEnterprise(String userId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Enterprise.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        dc.add(Restrictions.eq("userId", userId));
        List<Enterprise> list = (List<Enterprise>) findAllByCriteria(dc);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

	public void saveOrUpdate(Enterprise enterprise) {
		SysLoginAccount account = null;
		if(StringUtils.isNotBlank(enterprise.getId())){
			SysUser user = enterprise.getUser();
			user.setCustomerType(UserType.ENTERPRISE);
			getDao().save(user);
			
			enterprise.setUserId(user.getId());
			
			account=new SysLoginAccount();
			account.setUserId(user.getId());
			account.setEmail(user.getEmail());
			
		}
		enterprise.setUser(null);
		getDao().saveOrUpdate(enterprise);
	}
}
