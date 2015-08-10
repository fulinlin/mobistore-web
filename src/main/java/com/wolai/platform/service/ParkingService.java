package com.wolai.platform.service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.TempParkingRecord;

public interface ParkingService extends CommonService {
	
	ParkingRecord parkInfo(String id);

	Page parkHistory(String userId, int startIndex, int pageSize);
	
	public ParkingRecord getParkingRecordbyExNo(String exNo,String parkingLotId);
	
	public TempParkingRecord getTempParkingRecordbyCarNo(String carNo);
	
	public void savePaingRecord(ParkingRecord record);
	
	public void deleteTempRecord(String exNo);
	
	public void updatePaingRecord(String  carNo);
}
