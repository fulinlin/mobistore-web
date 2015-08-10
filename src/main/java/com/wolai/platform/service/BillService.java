package com.wolai.platform.service;

import com.wolai.platform.entity.Bill;

public interface BillService extends CommonService {
	
	Bill getBillByParking(String parkingId);
	
	
}
