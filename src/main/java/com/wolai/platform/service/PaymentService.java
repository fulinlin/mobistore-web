package com.wolai.platform.service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentService extends CommonService {

	Bill createBillIfNeededPers(ParkingRecord parking, String couponId);

	void payPers(Bill bill, String payType, String tradeNo);

	void successPers(Bill bill, String trade_no, String trade_status);
	
}
