package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.Bill;

public interface BillService extends CommonService {
	
	Bill getBillByParking(String parkingId);
	
//	List<Bill> getPostPayCarsFromBill();
//	
//	List<Bill> getPostPayBillByCarNo(String carNo);
//	
	Boolean hasUnPayedBill(String CarNo);
	
	List<Bill> findPaidBillButNotCallBack();
}
