package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.SysAPIKey;

public interface ApiKeyService extends CommonService {

	public SysAPIKey getKeyByParinglotId(String parkinglotId);
}
