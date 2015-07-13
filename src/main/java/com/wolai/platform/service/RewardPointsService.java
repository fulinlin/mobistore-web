package com.wolai.platform.service;

import com.wolai.platform.entity.RewardPoints;

public interface RewardPointsService extends CommonService {

	RewardPoints getByUser(String userId);

	void exchange(RewardPoints rewardPoints, String amount, String ruleId);
	
}