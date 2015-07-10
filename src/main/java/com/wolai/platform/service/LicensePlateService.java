package com.wolai.platform.service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.License;

public interface LicensePlateService extends CommonService {

	Page listByUser(String userId);

	void create(License po);
	void update(License po);
}
