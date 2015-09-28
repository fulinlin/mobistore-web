package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrShoppingcart;
import com.tinypace.mobistore.entity.StrShoppingcartItem;

public interface ShoppingcartService extends CommonService {

	public StrShoppingcart getByClient(String clientId);

	public StrShoppingcart addto(String id, String productId, String qty);

	public StrShoppingcart changeQtyPers(String userId, String itemId, Integer itemQty);

	public StrShoppingcart clearPers(String clientId);
	
	public StrOrder checkoutPers(String clientId);

	List<StrShoppingcartItem> getItems(String cartId);

	StrShoppingcart computerShoopingcartPricePers(String cartId);

}
