package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.SponsorLicense;
import com.wolai.platform.service.CommonService;

/**
 * 赞助车牌Service
 * @author sevenshi
 * @version 2015-07-17
 */
public interface SponsorLicenseService extends CommonService {

    List<SponsorLicense> getSponsorLicensesByIds(String[] ids);
    List<SponsorLicense> getSponsorLicensesByCategory(String[] ids);
}