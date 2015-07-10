package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;

public interface ParkingService extends CommonService {
	
	Page packInfo(String id);

	Page packHistory(String userId);

}
