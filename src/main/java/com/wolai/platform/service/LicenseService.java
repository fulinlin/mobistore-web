package com.wolai.platform.service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.SysUser;

public interface LicenseService extends CommonService {

	Page listByUser(String userId, int startIndex, int pageSize);

	void create(License po);
	void update(License po);
	
	License getLincense(String carNo);

	License getLicense(String id, String string);
}
