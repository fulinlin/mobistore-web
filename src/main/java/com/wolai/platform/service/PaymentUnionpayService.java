package com.wolai.platform.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wolai.platform.entity.UnionpayCardBound;


public interface PaymentUnionpayService extends CommonService {

	Map<String, String> prepareTrans(String wolaiTradeNo, int intValue);

	Map<String, String> boundPers(String userId, String orderId, String accNo, String certifId, String cvn, String expired);

	Map<String, String> unboundPers(String userId, String orderId);

	UnionpayCardBound createBoundRecordPers(String userId, String accNo, String wolaiTradeNo);

	UnionpayCardBound boundQueryByCard(String accNo);

	UnionpayCardBound boundQueryByUser(String userId);

	Map<String, String> postPayConsume(String wolaiTradeNo, String accNo, BigDecimal amount);

	boolean postPayBillSattlement(String billId,String userId);

	Map<String, String> getUnionpayResp(HttpServletRequest request);
	void unionpayCallbackPers(Map<String, String> resp);

	Map<String, String> postPayQuery(String orderId, String queryId);
	
}
