package com.tinypace.mobistore.service;


import com.tinypace.mobistore.bean.Page;

public interface MsgService extends CommonService {

	Page list(String clientId, int startIndex, int pageSize);
}
