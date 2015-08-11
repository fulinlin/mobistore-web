package com.wolai.platform.service;

import com.wolai.platform.entity.SysAPIKey;

public interface ApiKeyService extends CommonService {

	public SysAPIKey getKeyByParinglotId(String parkinglotId);
}
