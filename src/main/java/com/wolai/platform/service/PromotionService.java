package com.wolai.platform.service;

import com.wolai.platform.bean.Page;

public interface PromotionService extends CommonService {

	Page listByUser(String userId);
	


}
