package com.wolai.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.HttpServerConstants;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Coupon.CouponStatus;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysAPIKey;
import com.wolai.platform.service.ApiKeyService;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.util.Encodes;
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
	
	@Autowired
	private MsgService msgService;
	
	@Override
	public Bill createBillIfNeededPersAndUpdateCouponPers(ParkingRecord parking, String couponId, boolean isPostPay, PayType payType) {
		return this.createBillIfNeededPers(parking, couponId, isPostPay, true, payType);
	}
	
	@Override
	public Bill createBillIfNeededWithoutUpdateCouponPers(ParkingRecord parking, boolean isPostPay) {
		return this.createBillIfNeededPers(parking, null, isPostPay, false, null);
	}
	
	private Bill createBillIfNeededPers(ParkingRecord parking, String newCouponId, boolean isPostPay, boolean needUpdateCoupon, PayType payType) {
		if (StringUtils.isEmpty(newCouponId)) {
			newCouponId = null;
		}
		
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
		Coupon validCoupon = null;
		Object validObj = couponService.get(Coupon.class, validCouponId);
		if (validObj != null) {
			validCoupon = (Coupon)validObj;
		}
		
		// TODO: 用validCoupon调用新利泊计费接口，更新费用数据
		PayQueryResponseVo payQueryResponseVo = getPayAmountFromThirdPartServer(parking, bill, validCoupon);
		BigDecimal totalAmount = payQueryResponseVo.getExpenses();
		BigDecimal payAmount = payQueryResponseVo.getAccruedExpenses();
		
		if (validCoupon != null && Coupon.CouponType.MONEY.equals(validCoupon.getType())) {
			payAmount = totalAmount.subtract(new BigDecimal(validCoupon.getMoney()));
			if (payAmount.doubleValue() <= 0) {
				payAmount = new BigDecimal(0);
			}
		}
		
		bill.setTotalAmount(totalAmount);
		bill.setPayAmount(payAmount);
		if (payType != null) {
			bill.setPaytype(payType);
		}
		saveOrUpdate(bill);
		
		return bill;
	}

	@Override
	public void alipayCallbackPers(Bill bill, String trade_no, String trade_status, String alipayTotal, Bill.PayType payType) {
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
	
	@Override
	public void payNonePers(Bill bill, String couponId) {
		bill.setPaytype(Bill.PayType.CASH);
		bill.setTradeStatus("SUCCESS");
		bill.setTradeAmount(bill.getPayAmount());
		bill.setPayStatus(PayStatus.SUCCESSED);
		bill.setTradeResponseTime(new Date());
		saveOrUpdate(bill);
		
		Coupon coupon  = (Coupon) couponService.get(Bill.class, couponId);
		if(coupon!=null){
			coupon.setStatus(CouponStatus.USED);
			saveOrUpdate(coupon);
		}
		
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
	
	private PayQueryResponseVo getPayAmountFromThirdPartServer (ParkingRecord parking, Bill bill, Coupon coupon) {
		if (Constant.THIRD_PART_SERVER == null) {
			SysAPIKey key = apiKeyService.getKeyByParinglotId(parking.getParkingLotId());
			Constant.THIRD_PART_SERVER = key.getUrl()+":"+key.getPort()+key.getRootPath();
		}
		
		PayQueryVo vo = new PayQueryVo();
		vo.setCarNo(bill.getCarNo());
		if (coupon != null && CouponType.TIME.equals(coupon.getType())) {
			vo.setCouponTime(coupon.getTime());
		} else {
			vo.setCouponTime(Long.valueOf(0));
		}
		
		vo.setEnterTime(parking.getDriveInTime().getTime());
		vo.setExNo(parking.getExNo());
		vo.setTimestamp(new Date().getTime());
		
		PayQueryResponseVo response = null;
		try{
			log.info("===请求新利泊计费服务===");
			log.info(vo.toString());
			String result = WebClientUtil.post(Constant.THIRD_PART_SERVER + HttpServerConstants.POST_PAY_QUERY, JSON.toJSONString(vo));
			JSONObject json = JSONObject.parseObject(result);
			response =JSON.parseObject(Encodes.getObject(json.getString("sign")).toJSONString(), PayQueryResponseVo.class);
			log.info(response.toString());
		} catch(Exception e){
			log.info(e.getStackTrace().toString());
		}
		return response;
	}

}
