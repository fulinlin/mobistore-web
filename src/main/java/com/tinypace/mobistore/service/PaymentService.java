package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Bill.PayType;
import com.tinypace.mobistore.entity.ParkingRecord;


public interface PaymentService extends CommonService {
	Bill createBillIfNeededPersAndUpdateCouponPers(ParkingRecord parking, String couponId, boolean isPostPay, PayType payType);
	Bill createBillIfNeededWithoutUpdateCouponPers(ParkingRecord parking, boolean isPostPay);
	Bill releaseCashPaidCouponsPers(String billId);
	void payNonePers(Bill bill, String couponId);

	void payNotice(String billId);
}
