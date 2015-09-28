package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrShoppingcart;

public interface ShoppingcartService extends CommonService {

	public StrShoppingcart getByClient(String clientId);

	public StrShoppingcart computerShoopingcartPrice(StrShoppingcart cart);

	public StrShoppingcart addto(String id, String productId, String qty);

	public StrShoppingcart changeQtyPers(String userId, String itemId, Integer itemQty);

	public StrShoppingcart clearPers(String clientId);

}
