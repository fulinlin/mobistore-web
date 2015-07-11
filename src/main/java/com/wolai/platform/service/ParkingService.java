package com.wolai.platform.service;

import com.wolai.platform.bean.Page;

public interface ParkingService extends CommonService {
	
	Page parkInfo(String id);

	Page parkHistory(String userId, int startIndex, int pageSize);

}
