package com.wolai.platform.service;


import com.wolai.platform.bean.Page;

public interface MsgService extends CommonService {

	Page listByUser(String userId, int startIndex, int pageSize);
	
}
