package com.tinypace.mobistore.service;


import java.util.List;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.entity.StrSearchHistory;
import com.tinypace.mobistore.entity.StrSearchHot;

public interface RecipientService extends CommonService {

	List<StrRecipient> list(String clientId);

}
