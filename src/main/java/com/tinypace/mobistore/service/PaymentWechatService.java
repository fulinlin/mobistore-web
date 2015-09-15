package com.tinypace.mobistore.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.UnionpayCardBound;


public interface PaymentWechatService extends CommonService {

	Map<String, Object> preparePay(String wolaiTradeNo, int totalFee, String ip) throws Exception;
	Map<String, Object> query(String wolaiTradeNo) throws Exception;
	void callbackPers(Bill bill, String returnCode, String returnMsg, String tradeStatus, String tradeAmount, String wolaiTradeNo);
	
	
}
