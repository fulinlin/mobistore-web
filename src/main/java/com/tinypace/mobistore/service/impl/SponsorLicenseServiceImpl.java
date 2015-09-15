package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.License;
import com.tinypace.mobistore.entity.SponsorLicense;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.service.SponsorLicenseService;
import com.tinypace.mobistore.service.UserService;

/**
 * 赞助车牌Service
 * @author sevenshi
 * @version 2015-07-17
 */
@Service
public class SponsorLicenseServiceImpl extends CommonServiceImpl implements SponsorLicenseService {
    
	@Autowired
	private UserService userService;
    
    @SuppressWarnings("unchecked")
    public List<SponsorLicense> getSponsorLicensesByIds(String[] ids){
        DetachedCriteria dc = DetachedCriteria.forClass(SponsorLicense.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        dc.add(Restrictions.in("id", ids));
        return (List<SponsorLicense>) findAllByCriteria(dc);
    }
    
    @SuppressWarnings("unchecked")
    public List<SponsorLicense> getSponsorLicensesByCategory(String[] ids){
        DetachedCriteria dc = DetachedCriteria.forClass(SponsorLicense.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        dc.add(Restrictions.in("licenseCategoryId", ids));
        return (List<SponsorLicense>) findAllByCriteria(dc);
    }

	@Override
	public void saveOrUpdate(SponsorLicense sponsorLicense) {
	        if (sponsorLicense.getLicenseId() == null) {
	        	SysUser user = userService.getTempUserPers();
	            //创建车牌
	        	License license = new License();
	            license.setCarNo(sponsorLicense.getCarNo());
	            license.setUserId(user.getId());
	            getDao().saveOrUpdate(license);
	            sponsorLicense.setLicenseId(license.getId());
	        }
	       getDao().saveOrUpdate(sponsorLicense);
	}

}