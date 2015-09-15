package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.ParkingRecord;


public interface PaymentAlipayService extends CommonService {
	void callbackPers(Bill bill, String alipayTradeNo, String alipayTradeStatus, String alipayTotal, Bill.PayType payType);
}
