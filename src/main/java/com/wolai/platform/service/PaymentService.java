package com.wolai.platform.service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentService extends CommonService {

	Bill createBillIfNeededPers(ParkingRecord parking, String couponId);
	void successPers(Bill bill, String alipayTradeNo, String alipayTradeStatus, Bill.PayType payType);
	
}
