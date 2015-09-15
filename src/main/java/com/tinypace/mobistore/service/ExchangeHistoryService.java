package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.ExchangeHistory;

public interface ExchangeHistoryService extends CommonService {

	ExchangeHistory getHistoryPers(String userId, String exchangePlanId);


}
