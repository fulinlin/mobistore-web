package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.Enterprise;

public interface EnterpriseService extends CommonService {

    Enterprise getEnterprise(String userId);
    
    void saveOrUpdate(Enterprise enterprise);
}
