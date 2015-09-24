package com.tinypace.mobistore.service;


import java.util.List;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrSearchHistory;

public interface SearchService extends CommonService {

	List<String> getHot();

	List<String> getHistory(String id);
}
