package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.bean.Page;

public interface CouponService extends CommonService {

	Page listByUser(String userId, int startIndex, int pageSize);

	Page listMoneyByUser(String userId, int startIndex, int pageSize);

	Page listTimeByUser(String userId, int startIndex, int pageSize);

	Map<String, Object> usePers(String id, String userId);

	long countMoneyByUser(String userId);

	long countTimeByUser(String userId);
	
}
