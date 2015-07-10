package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.bean.Page;

public interface CouponService extends CommonService {

	Page listByUser(String userId);

	Page listMoneyByUser(String userId);

	Page listTimeByUser(String userId);

	Map<String, Object> usePers(String id, String userId);
	
}
