package com.tinypace.mobistore.service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.ParkingLot;

public interface ParkingLotService extends CommonService {
	
	Page listByCity(String city, int startIndex, int pageSize);

	void save(Object o);
	
	Page findAllByPage(ParkingLot parkingLot,int start,int limit);
	
}
