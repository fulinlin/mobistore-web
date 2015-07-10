package com.wolai.platform.service;

import com.wolai.platform.entity.Integral;

public interface IntegralService extends CommonService {

	Integral getByUser(String userId);
	
}
