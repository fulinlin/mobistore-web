package com.tinypace.mobistore.service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.License;

public interface LicenseService extends CommonService {

	Page listByUser(String userId, int startIndex, int pageSize);

	void create(License po);
	void update(License po);
	
	License getLincense(String carNo);

	License getLicense(String id, String string);
}
