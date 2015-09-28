package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrRecipient;


public interface UserService extends CommonService {

	List<StrRecipient> getRecipients(String clientId);

	StrRecipient getDefaultRecipient(String clientId);

	
}
