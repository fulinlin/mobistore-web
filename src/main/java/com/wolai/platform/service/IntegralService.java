package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.Integral;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;

public interface IntegralService extends CommonService {

	List<Integral> listByUser(String userId);
	


}
