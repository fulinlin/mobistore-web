package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SponsorLicense;
import com.wolai.platform.service.SponsorLicenseService;

/**
 * 赞助车牌Service
 * @author sevenshi
 * @version 2015-07-17
 */
@Service
public class SponsorLicenseServiceImpl extends CommonServiceImpl implements SponsorLicenseService {
    
    
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

}