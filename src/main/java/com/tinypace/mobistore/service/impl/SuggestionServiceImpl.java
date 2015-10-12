package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrCollection;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.StrSuggestion;
import com.tinypace.mobistore.entity.StrOrder.Status;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.SuggestionService;

@Service
public class SuggestionServiceImpl extends CommonServiceImpl implements SuggestionService {

	@Override
	public void saveSuggestion(String id, String content) {
		StrSuggestion suggestion = new StrSuggestion();
		suggestion.setContent(content);;
		suggestion.setClientId(id);
		saveOrUpdate(suggestion);
	}
}
