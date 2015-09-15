package com.tinypace.mobistore.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Bill.PayStatus;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.MsgService;
import com.tinypace.mobistore.service.PaymentAlipayService;
import com.tinypace.mobistore.service.PaymentService;

@Service
public class PaymentAlipayServiceImpl extends CommonServiceImpl implements PaymentAlipayService {

	@Autowired
	BillService billService;
	
	@Autowired
	MsgService msgService;
	
	@Autowired
	PaymentService paymentService;
	
	@Override
	public void callbackPers(Bill bill, String trade_no, String trade_status, String alipayTotal, Bill.PayType payType) {
		bill.setPaytype(payType);
		bill.setTradeStatus(trade_status);
		bill.setTradeAmount(new BigDecimal(alipayTotal));
		
		String title = "";
		String msgShort = "";
		String msgFull = "";
		if ("TRADE_SUCCESS".equals(trade_status)) {
			bill.setPayStatus(PayStatus.SUCCESSED);
			
			title = Constant.payment_paySuccess_title;
			msgShort = Constant.payment_paySuccess_msg_short.replaceAll("%AMOUNT%", alipayTotal)
					.replace("%LINCENST%", bill.getCarNo());
			msgFull = Constant.payment_paySuccess_full.replaceAll("%AMOUNT%", alipayTotal)
					.replace("%LINCENST%", bill.getCarNo());
			
			paymentService.payNotice(bill.getId());
			
		} else {
			bill.setPayStatus(PayStatus.FEATURE);
			
			title = Constant.payment_payFail_title;
			msgShort = Constant.payment_payFail_msg_short.replaceAll("%AMOUNT%", String.valueOf(bill.getPayAmount().intValue()))
					.replace("%LINCENST%", bill.getCarNo());
			msgFull = Constant.payment_payFail_full.replaceAll("%AMOUNT%", String.valueOf(bill.getPayAmount().intValue()))
					.replace("%LINCENST%", bill.getCarNo());
		}
		msgService.sendAppMsg(bill.getParkingRecord().getUser(), title, msgShort, msgFull);
		
		bill.setTradeResponseTime(new Date());
		
		saveOrUpdate(bill);
		
		ParkingRecord park = (ParkingRecord) billService.get(ParkingRecord.class, bill.getParkingRecordId());
		park.setIsPaid(true);
		saveOrUpdate(park);
	}
	

}
