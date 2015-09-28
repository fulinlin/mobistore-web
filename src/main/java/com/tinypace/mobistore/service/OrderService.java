package com.tinypace.mobistore.service;


import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrOrder;

public interface OrderService extends CommonService {

	Page list(int startIndex, int pageSize);
	
	public StrOrder checkoutPers(String clientId, String recipientName, String recipientPhone, String recipientProvince, String recipientCity, String shipAddress);

}
