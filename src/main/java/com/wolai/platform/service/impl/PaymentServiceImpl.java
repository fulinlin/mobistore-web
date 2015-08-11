package com.wolai.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysAPIKey;
import com.wolai.platform.service.ApiKeyService;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.util.StringUtil;
import com.wolai.platform.util.WebClientUtil;
import com.wolai.platform.vo.PayQueryResponseVo;
import com.wolai.platform.vo.PayQueryVo;

@Service
public class PaymentServiceImpl extends CommonServiceImpl implements PaymentService {
	private static Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	ApiKeyService apiKeyService;
	
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

		if(!bill.getIsPostPay()) { // 后付费账单永久保留为true
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
		PayQueryResponseVo payQueryResponseVo = getPayAmountFromThirdPart(parking, bill, validCoupon);
		BigDecimal totalAmount = payQueryResponseVo.getExpenses();
		BigDecimal payAmount = payQueryResponseVo.getAccruedExpenses();
		
		if (Coupon.CouponType.MONEY.equals(validCoupon.getType())) {
			payAmount = payAmount.subtract(new BigDecimal(validCoupon.getMoney()));
			if (payAmount.intValue() < 0) {
				payAmount = new BigDecimal(0);
			}
		}
		
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

	@Override
	public Bill releaseCashPaidCouponsPers(String billId) {
		Bill bill = (Bill) get(Bill.class, billId);
		if (StringUtil.isNotBlank(bill.getCouponId())) {
			Coupon old = (Coupon) get(Coupon.class, bill.getCouponId());
			old.setStatus(Coupon.CouponStatus.INIT);
			saveOrUpdate(old);
		}
		bill.setPayAmount(new BigDecimal(0));
		bill.setCouponId(null);
		bill.setPaytype(PayType.CASH);
		bill.setPayStatus(PayStatus.SUCCESSED);
		saveOrUpdate(bill);
		return bill;
	}
	
	private PayQueryResponseVo getPayAmountFromThirdPart (ParkingRecord parking, Bill bill, Coupon coupon) {
		SysAPIKey key = apiKeyService.getKeyByParinglotId(parking.getParkingLotId());
		PayQueryVo vo = new PayQueryVo();
		vo.setCarNo(bill.getCarNo());
		if (CouponType.TIME.equals(coupon.getType())) {
			vo.setCouponTime(coupon.getTime());
		} else {
			vo.setCouponTime(Long.valueOf(0));
		}
		
		vo.setEnterTime(parking.getDriveInTime().getTime());
		vo.setExNo(parking.getExNo());
		vo.setTimestamp(new Date().getTime());
		
		PayQueryResponseVo response = null;
		try{
			String result = WebClientUtil.post(key.getUrl()+":"+key.getPort()+key.getRootPath(), JSON.toJSONString(vo));
			response = JSON.parseObject(result, PayQueryResponseVo.class);
			
		} catch(Exception e){
			log.error(e.getStackTrace().toString());
		}
		return response;
	}
}
