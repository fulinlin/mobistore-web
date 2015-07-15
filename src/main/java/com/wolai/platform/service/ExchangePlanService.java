package com.wolai.platform.service;

import java.util.List;

public interface ExchangePlanService extends CommonService {

	List listByPromotion(String promotionId);

}
