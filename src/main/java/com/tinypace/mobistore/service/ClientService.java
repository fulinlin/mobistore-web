package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrCollection;

public interface ClientService extends CommonService {

	StrClient getClientByToken(String token);

	StrClient signonPers(String mobile, String password, String platform,
			String agent, String deviceToken);

	boolean collectIfNeedPers(String clientId, String productId);

	StrCollection isCollected(String userId, String productId);
}
