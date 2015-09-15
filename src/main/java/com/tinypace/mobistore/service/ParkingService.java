package com.tinypace.mobistore.service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.entity.TempParkingRecord;
import com.tinypace.mobistore.vo.ParkingVo;

public interface ParkingService extends CommonService {
	
	ParkingRecord parkInfo(String id);

	Page parkHistory(String userId, int startIndex, int pageSize);
	
	public ParkingRecord getParkingRecordbyExNo(String exNo,String parkingLotId);
	
	public TempParkingRecord getTempParkingRecordbyCarNo(String carNo);
	
	public void savePaingRecord(ParkingRecord record);
	
	public void deleteTempRecord(String exNo);
	
	public void updatePaingRecord(String  carNo);

	void setBillInfoForPark(ParkingVo vo, Bill bill);
}
