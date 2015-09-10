package com.wolai.platform.service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentService extends CommonService {
	Bill createBillIfNeededPersAndUpdateCouponPers(ParkingRecord parking, String couponId, boolean isPostPay, PayType payType);
	Bill createBillIfNeededWithoutUpdateCouponPers(ParkingRecord parking, boolean isPostPay);
	Bill releaseCashPaidCouponsPers(String billId);
	void payNonePers(Bill bill, String couponId);

	void payNotice(String billId);
}
