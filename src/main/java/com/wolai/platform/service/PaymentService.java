package com.wolai.platform.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.Bill.PayType;


public interface PaymentService extends CommonService {
	Bill createBillIfNeededPersAndUpdateCouponPers(ParkingRecord parking, String couponId, boolean isPostPay, PayType payType);
	Bill createBillIfNeededWithoutUpdateCouponPers(ParkingRecord parking, boolean isPostPay);
	Bill releaseCashPaidCouponsPers(String billId);
	void payNonePers(Bill bill, String couponId);

}
