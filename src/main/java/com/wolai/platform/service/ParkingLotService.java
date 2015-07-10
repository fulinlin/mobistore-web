package com.wolai.platform.service;

import java.util.List;
import java.util.Map;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;

public interface ParkingLotService extends CommonService {
	
	Page listByCity(String city);

	void save(Object o);
}
