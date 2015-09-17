package com.tinypace.mobistore.service;


import com.tinypace.mobistore.bean.Page;

public interface CollectionService extends CommonService {

	Page list(int startIndex, int pageSize);
}
