package com.wolai.platform.service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentAlipayService extends CommonService {
	void callbackPers(Bill bill, String alipayTradeNo, String alipayTradeStatus, String alipayTotal, Bill.PayType payType);
}
