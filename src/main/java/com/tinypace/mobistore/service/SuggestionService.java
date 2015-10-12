package com.tinypace.mobistore.service;

import java.util.Map;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrCollection;

public interface SuggestionService extends CommonService {
	void saveSuggestion(String id, String content);
}
