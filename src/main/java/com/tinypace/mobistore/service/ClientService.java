package com.tinypace.mobistore.service;


import com.tinypace.mobistore.bean.Page;

public interface ClientService extends CommonService {

	Page list(int startIndex, int pageSize);
}
