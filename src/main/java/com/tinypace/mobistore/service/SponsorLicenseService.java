package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.SponsorLicense;
import com.tinypace.mobistore.service.CommonService;

/**
 * 赞助车牌Service
 * @author sevenshi
 * @version 2015-07-17
 */
public interface SponsorLicenseService extends CommonService {

    List<SponsorLicense> getSponsorLicensesByIds(String[] ids);
    List<SponsorLicense> getSponsorLicensesByCategory(String[] ids);
    
    void saveOrUpdate(SponsorLicense license);
}