package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.LicenseCategory;
import com.tinypace.mobistore.service.CommonService;

/**
 * 车牌分类Service
 * @author sevenshi
 * @version 2015-07-20
 */
public interface LicenseCategoryService extends CommonService {
    
    List<LicenseCategory>  getLicenseCategories(String  loginAccountId);
}