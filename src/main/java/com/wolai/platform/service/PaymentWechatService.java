package com.wolai.platform.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.UnionpayCardBound;


public interface PaymentWechatService extends CommonService {

	Map<String, Object> preparePay(String wolaiTradeNo, int totalFee) throws Exception;
	Map<String, Object> query(String wolaiTradeNo) throws Exception;
	void callbackPers(Bill bill, String returnCode, String returnMsg, String tradeStatus, String totalFee, String wolaiTradeNo);
	
	
}
