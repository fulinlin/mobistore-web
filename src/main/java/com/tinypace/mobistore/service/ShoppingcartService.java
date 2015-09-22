package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.StrShoppingcart;

public interface ShoppingcartService extends CommonService {

	public StrShoppingcart getByClient(String clientId);
}
