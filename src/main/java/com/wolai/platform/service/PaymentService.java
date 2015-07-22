package com.wolai.platform.service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentService extends CommonService {

	Bill createBillIfNeeded(ParkingRecord parking, String couponId);

	void pay(Bill bill, String payType, String tradeNo);

	void success(Bill bill, String trade_no, String trade_status);
	
}
