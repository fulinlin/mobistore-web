package com.wolai.platform.service;

import com.wolai.platform.bean.Page;

public interface ParkingLotService extends CommonService {
	
	Page listByCity(String city);

	void save(Object o);
}
