package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.bean.Page;

public interface PromotionService extends CommonService {

	Page list(int startIndex, int pageSize);
	
	List listRecommended();

}
