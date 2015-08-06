package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentUnionpayService extends CommonService {

	Map<String, String> prepareTrans(String wolaiTradeNo, int intValue);

	Map<String, String> bound(String orderId, String accNo, String certifId, String cvn, String expired);

	Map<String, String> unbound(String orderId);

	
}
