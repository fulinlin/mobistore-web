package com.tinypace.mobistore.service;


import java.util.List;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrOrderItem;

public interface OrderService extends CommonService {

	Page list(String filter, String clientId, int startIndex, int pageSize);

	StrOrder payPers(String orderId, String recipientName, String recipientPhone,
			String recipientArea, String recipientStreet, String shipAddress);

	List<StrOrderItem> getItems(String orderId);

	void cancelPers(String orderId);

	StrOrder changeRecipientPers(String orderId, String recipientId);
	
}
