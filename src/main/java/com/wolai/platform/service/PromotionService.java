package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.Promotion;

public interface PromotionService extends CommonService {

	Page listByUser(String userId);
	


}
