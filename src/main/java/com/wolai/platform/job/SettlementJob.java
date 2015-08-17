package com.wolai.platform.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.SysAPIKey;
import com.wolai.platform.entity.UnionpayCardBound;
import com.wolai.platform.service.ApiKeyService;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.PaymentUnionpayService;
import com.wolai.platform.util.WebClientUtil;
import com.wolai.platform.vo.PayQueryResponseVo;
import com.wolai.platform.vo.PayQueryVo;

@Service
public class SettlementJob {

	@Autowired
	BillService billService;
	
	@Autowired
	PaymentUnionpayService paymentUnionpayService;
	
	@Autowired
	ApiKeyService apiKeyService;
	
	/**
	 * 检查未付费后付款账单，并计算应付费用并扣除相应金额
	 */
	public void excute(){
		// 查询所有未付费车牌
//		List<Bill> bills = billService.getPostPayCarsFromBill();
//		for(Bill bill:bills){
//			//String carNo = bill.getCarNo();
//			// 根据车牌查询相关账单
//			//List<Bill> billsForPay = billService.getPostPayBillByCarNo(carNo);
//			UnionpayCardBound  bound = paymentUnionpayService.boundQueryByUser(bill.getLicensePlate().getUserId());
//				// 如果是确认后付费的情况下，用户可能曾经选择过优惠券
//				if(bill.getCoupon()!=null){
//					/*
//					 * 如果客户选择的是抵时券的话则请求一次计费接口
//					 */
//					if(CouponType.TIME.equals(bill.getCoupon().getType())){
//						SysAPIKey key = apiKeyService.getKeyByParinglotId(bill.getParkingRecord().getParkingLotId());
//						PayQueryVo vo = new PayQueryVo();
//						vo.setCarNo(bill.getCarNo());
//						vo.setCouponTime(bill.getCoupon().getTime());
//						vo.setEnterTime(bill.getParkingRecord().getDriveInTime().getTime());
//						vo.setExNo(bill.getParkingRecord().getExNo());
//						vo.setTimestamp(new Date().getTime());
//						// 尝试获取计费价格，如果不成功留着下次再处理
//						try{
//							String result = WebClientUtil.post(key.getUrl()+":"+key.getPort()+key.getRootPath(), JSON.toJSONString(vo));
//							PayQueryResponseVo response = JSON.parseObject(result, PayQueryResponseVo.class);
//							// 如果用了抵时券和没用抵时券应收款是一致的话则不使用抵时券
//							if(response.getAccruedExpenses().equals(response.getExpenses())){
//								bill.setCouponId(null);
//								bill.setPayAmount(response.getExpenses());
//							}else{
//								bill.setPayAmount(response.getAccruedExpenses());
//							}
//							billService.saveOrUpdate(bill);
//						}catch(Exception e){
//							continue;
//						}
//					}else{
//						bill.setPayAmount(bill.getTotalAmount().subtract(new BigDecimal(bill.getCoupon().getMoney())));
//						if(bill.getPayAmount().compareTo(new BigDecimal(0))<1){
//							bill.setPayAmount(new BigDecimal(0));
//							bill.setPaytype(PayType.UNIONPAY);
//							bill.setPayStatus(PayStatus.SUCCESSED);
//						}
//						billService.saveOrUpdate(bill);
//						if(PayStatus.SUCCESSED.equals(bill.getPayStatus())){
//							continue;
//						}
//					}
//					paymentUnionpayService.postPayConsume(bill.getId(),bound.getAccNo(),bill.getPayAmount());
//				}
//		}
	}
	
}
