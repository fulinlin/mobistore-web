package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.StrClient;

public interface ClientService extends CommonService {

	StrClient getClientByToken(String token);

	StrClient signonPers(String mobile, String password, String platform,
			String agent, String deviceToken);
}
