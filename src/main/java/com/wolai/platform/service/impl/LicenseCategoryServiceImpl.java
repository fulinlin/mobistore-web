package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.LicenseCategory;
import com.wolai.platform.service.LicenseCategoryService;

/**
 * 车牌分类Service
 * @author sevenshi
 * @version 2015-07-20
 */
@Service
public class LicenseCategoryServiceImpl extends CommonServiceImpl implements LicenseCategoryService {

    @Override
    public List<LicenseCategory> getLicenseCategories(String loginAccountId) {
        DetachedCriteria dc = DetachedCriteria.forClass(LicenseCategory.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        dc.add(Restrictions.eq("loginAccountId", loginAccountId));
        @SuppressWarnings("unchecked")
        List<LicenseCategory> list =  (List<LicenseCategory>) findAllByCriteria(dc);
        return list;
    }
}