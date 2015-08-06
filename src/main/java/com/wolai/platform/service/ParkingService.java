package com.wolai.platform.service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingRecord;

public interface ParkingService extends CommonService {
	
	ParkingRecord parkInfo(String id);

	Page parkHistory(String userId, int startIndex, int pageSize);
	
	public ParkingRecord getParkingRecordbyExNo(String exNo);
	
	public void saveNotWolaiPaingRecord(ParkingRecord record);
	
	
}
