package com.wolai.platform.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.util.StringUtils;

@Service
public class PaymentServiceImpl extends CommonServiceImpl implements PaymentService {

	@Autowired
	BillService billService;
	
	@Override
	public Bill createBillIfNeededPers(ParkingRecord parking, String couponId) {
		String parkingId = parking.getId();
		
		Bill bill = billService.getBillByParking(parkingId);
		
		if (bill == null) {
			bill = new Bill();
			bill.setCarNo(parking.getCarNo());
			bill.setParkingRecordId(parkingId);
			bill.setLicensePlateId(parking.getParkingLotId());
		}
		
		if (StringUtils.isNotEmpty(couponId)) {
			bill.setCouponId(couponId);
		}
		
		// TODO: 调用新利泊计费接口，更新费用数据
		BigDecimal money = new BigDecimal(10);
		BigDecimal paidMoney = new BigDecimal(8);
		
		parking.setMoney(money);
		parking.setPaidMoney(paidMoney);
		saveOrUpdate(parking);
		
		bill.setMoney(paidMoney);
		saveOrUpdate(bill);
		
		return bill;
	}

	@Override
	public void payPers(Bill bill, String payType, String trade_no) {
		bill.setTradeNo(trade_no);;
		bill.setPaytype(PayType.valueOf(PayType.class, payType));
		bill.setPayStatus(Bill.PayStatus.SEND);
		saveOrUpdate(bill);
	}

	@Override
	public void successPers(Bill bill, String trade_no, String trade_status) {
		
		bill.setTradeStatus(trade_status);
		bill.setPayStatus(PayStatus.SUCCESSED);
		saveOrUpdate(bill);
	}
}
