package com.wolai.platform.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.Bill.PayType;


public interface PaymentService extends CommonService {

	void successPers(Bill bill, String alipayTradeNo, String alipayTradeStatus, Bill.PayType payType);
	Bill createBillIfNeededPersAndUpdateCouponPers(ParkingRecord parking, String couponId, boolean isPostPay, PayType payType);
	Bill createBillIfNeededWithoutUpdateCouponPers(ParkingRecord parking, boolean isPostPay);
	Bill releaseCashPaidCouponsPers(String billId);

}
