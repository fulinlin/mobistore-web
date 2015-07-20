package com.wolai.platform.service;


import java.util.Date;

import com.wolai.platform.bean.Page;

public interface MsgService extends CommonService {

	Page listByUser(String userId, Date after, Date before, int startIndex, int pageSize);
	
}
