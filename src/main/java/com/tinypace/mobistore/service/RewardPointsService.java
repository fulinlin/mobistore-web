package com.tinypace.mobistore.service;

import com.tinypace.mobistore.entity.RewardPoints;

public interface RewardPointsService extends CommonService {

	RewardPoints getByUserPers(String userId);

	void exchange(RewardPoints rewardPoints, String amount, String ruleId);
	
}
