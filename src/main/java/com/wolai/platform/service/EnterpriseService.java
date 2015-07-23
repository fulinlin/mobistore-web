package com.wolai.platform.service;

import com.wolai.platform.entity.Enterprise;

public interface EnterpriseService extends CommonService {

    Enterprise getEnterprise(String userId);
}
