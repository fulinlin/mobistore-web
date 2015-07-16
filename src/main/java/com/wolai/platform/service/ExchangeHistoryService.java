package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.ExchangeHistory;

public interface ExchangeHistoryService extends CommonService {

	ExchangeHistory getHistoryPers(String userId, String exchangePlanId);


}
