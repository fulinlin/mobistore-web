package com.wolai.platform.service;

import com.wolai.platform.bean.Page;

public interface ParkingService extends CommonService {
	
	Page packInfo(String id);

	Page packHistory(String userId);

}
