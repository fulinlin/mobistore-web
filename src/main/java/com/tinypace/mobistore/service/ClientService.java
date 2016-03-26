package com.tinypace.mobistore.service;

import java.util.List;
import java.util.Map;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrCollection;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.SysVerifyCode;

public interface ClientService extends CommonService {

	StrClient getClientByToken(String token);
	StrClient getClientByMobile(String token);

	StrClient signonPers(String mobile, String password, String platform,
			String agent, String deviceToken);
	StrClient signupPers(String mobile, String password, String platform,
			String isWebView, String deviceToken);

	boolean collectIfNeedPers(String clientId, String productId);

	StrCollection isCollected(String userId, String productId);

	Map<String, Long> count(String clientId);

	SysVerifyCode forgetPaswordPers(String mobile);
	StrClient resetPasswordPers(String verifyCode, String mobile, String password,
			String platform, String isWebView, String deviceToken);

}
