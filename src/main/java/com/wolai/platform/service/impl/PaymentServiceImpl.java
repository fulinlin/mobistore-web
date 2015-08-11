package com.wolai.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.util.StringUtil;

@Service
public class PaymentServiceImpl extends CommonServiceImpl implements PaymentService {

	@Autowired
	BillService billService;
	
	@Autowired
	CouponService couponService;
	
	@Override
	public Bill createBillIfNeededPersAndUpdateCouponPers(ParkingRecord parking, String couponId, boolean isPostPay) {
		return this.createBillIfNeededPers(parking, couponId, isPostPay, true);
	}
	
	@Override
	public Bill createBillIfNeededWithoutUpdateCouponPers(ParkingRecord parking, boolean isPostPay) {
		return this.createBillIfNeededPers(parking, null, isPostPay, false);
	}
	
	private Bill createBillIfNeededPers(ParkingRecord parking, String newCouponId, boolean isPostPay, boolean needUpdateCoupon) {
		String parkingId = parking.getId();
		
		Bill bill = billService.getBillByParking(parkingId);
		if (bill == null) {
			bill = new Bill();
			bill.setCarNo(parking.getCarNo());
			bill.setParkingRecordId(parkingId);
			bill.setLicensePlateId(parking.getCarNoId());
			bill.setCreateTime(new Date());
		}
		if(!isPostPay) { // 后付费账单一直保留为true
			bill.setIsPostPay(isPostPay);
		}
		
		if (needUpdateCoupon) {
			bill.setCouponId(newCouponId);
			
			// 解冻和冻结相关
			String oldCouponId = bill.getCouponId();
			couponService.holdCouponPers(newCouponId, oldCouponId);
		}
		
		String validCouponId = bill.getCouponId();
		Coupon validCoupon = (Coupon) couponService.get(Coupon.class, validCouponId);
		
		// TODO: 用validCoupon调用新利泊计费接口，更新费用数据
		BigDecimal totalAmount = new BigDecimal(0.02);
		BigDecimal payAmount = new BigDecimal(0.01);
		
//		if (Coupon.CouponType.MONEY.toString().equals(validCoupon.getType().toString())) {
//			payAmount = payAmount.subtract(new BigDecimal(validCoupon.getMoney()));
//			if (payAmount.intValue() < 0) {
//				payAmount = new BigDecimal(0);
//			}
//		}

		saveOrUpdate(parking);
		
		bill.setTotalAmount(totalAmount);
		bill.setPayAmount(payAmount);
		saveOrUpdate(bill);
		
		return bill;
	}

	@Override
	public void successPers(Bill bill, String trade_no, String trade_status, Bill.PayType payType) {
		bill.setPaytype(payType);
		bill.setTradeStatus(trade_status);
		bill.setPayStatus(PayStatus.SUCCESSED);
		bill.setTradeSuccessTime(new Date());
		saveOrUpdate(bill);
		
		ParkingRecord park = (ParkingRecord) billService.get(ParkingRecord.class, bill.getParkingRecordId());
		park.setIsPaid(true);
		saveOrUpdate(park);
	}
}
