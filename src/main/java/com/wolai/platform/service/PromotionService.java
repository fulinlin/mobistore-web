package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Promotion;

public interface PromotionService extends CommonService {

	Page list(int startIndex, int pageSize);
	
	List listRecommended();

	Promotion getRegisterPresent();
	
	void registerPresent(String userId);

}
