package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.Promotion;

public interface PromotionService extends CommonService {

	Page list(int startIndex, int pageSize);
	
	List listRecommended();

	Promotion getRegisterPresent();
	
	void registerPresent(String userId);

}
