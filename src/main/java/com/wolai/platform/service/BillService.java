package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.Bill;

public interface BillService extends CommonService {
	
	Bill getBillByParking(String parkingId);
	
	List<Bill> getPostPayCarsFromBill();
	
	List<Bill> getPostPayBillByCarNo(String carNo);
	
	Boolean hasUnPayedBill(String CarNo);
}
