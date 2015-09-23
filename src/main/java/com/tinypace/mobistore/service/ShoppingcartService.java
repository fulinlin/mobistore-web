package com.tinypace.mobistore.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tinypace.mobistore.entity.StrShoppingcart;

public interface ShoppingcartService extends CommonService {

	public StrShoppingcart getByClient(String clientId);

	public BigDecimal computerShoopingcartPrice(StrShoppingcart cart);
}
