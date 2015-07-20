package com.wolai.platform.service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingLot;

public interface ParkingLotService extends CommonService {
	
	Page listByCity(String city, int startIndex, int pageSize);

	void save(Object o);
	
	Page findAllByPage(ParkingLot parkingLot,int start,int limit);
	
}
