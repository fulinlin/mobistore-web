package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;

public interface LicensePlateService extends CommonService {

	List<License> listByUser(String userId);
	


}
