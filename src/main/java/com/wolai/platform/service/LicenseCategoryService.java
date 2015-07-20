package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.LicenseCategory;
import com.wolai.platform.service.CommonService;

/**
 * 车牌分类Service
 * @author sevenshi
 * @version 2015-07-20
 */
public interface LicenseCategoryService extends CommonService {
    
    List<LicenseCategory>  getLicenseCategories(String  loginAccountId);
}