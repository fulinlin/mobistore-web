package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;


public interface PaymentUnionpayService extends CommonService {

	Map<String, String> prepareTrans(int intValue);

	
}
