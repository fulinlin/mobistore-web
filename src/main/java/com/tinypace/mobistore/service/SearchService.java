package com.tinypace.mobistore.service;


import java.util.List;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrSearchHistory;
import com.tinypace.mobistore.entity.StrSearchHot;

public interface SearchService extends CommonService {

	List<String> getHot();

	List<String> getHistory(String id);

	List<StrProduct> search(String keywords, String category);

	List<StrSearchHot> getMatchedKeywords(String keywords);
}
