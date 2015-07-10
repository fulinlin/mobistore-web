package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;

public interface CouponService extends CommonService {

	Page listByUser(String userId);

	Page listMoneyByUser(String userId);

	Page listTimeByUser(String userId);
	
}
