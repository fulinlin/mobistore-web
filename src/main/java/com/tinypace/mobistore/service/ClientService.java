package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.StrClient;

public interface ClientService extends CommonService {

	StrClient getClientByToken(String token);
}
