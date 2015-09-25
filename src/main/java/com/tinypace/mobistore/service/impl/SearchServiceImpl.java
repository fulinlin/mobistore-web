package com.tinypace.mobistore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrSearchHistory;
import com.tinypace.mobistore.entity.StrSearchHot;
import com.tinypace.mobistore.service.SearchService;
import com.tinypace.mobistore.util.StringUtil;

@Service
public class SearchServiceImpl extends CommonServiceImpl implements SearchService {

	@Override
	public List<String> getHot() {
		DetachedCriteria dc = DetachedCriteria.forClass(StrSearchHot.class);
		dc.addOrder(Order.desc("times"));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		Page page = findPage(dc, 0, 5);
		
		List<String> hots = new ArrayList<String>();
		for (Object obj: page.getItems()) {
			StrSearchHot hot = (StrSearchHot) obj;
			hots.add(hot.getKeywords());
		}
		
		return hots;
	}

	@Override
	public List<String> getHistory(String clientId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrSearchHistory.class);
		dc.add(Restrictions.eq("clientId", clientId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		dc.addOrder(Order.desc("searchTime"));
		Page page = findPage(dc, 0, 5);
		
		List<String> histories = new ArrayList<String>();
		for (Object obj: page.getItems()) {
			StrSearchHistory hot = (StrSearchHistory) obj;
			histories.add(hot.getKeywords());
		}
		
		return histories;
	}

	@Override
	public List<StrProduct> search(String keywords) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrProduct.class);
		
		if (!StringUtil.IsEmpty(keywords)) {
			dc.add(Restrictions.like("name", "%" + keywords + "%"));
		}
		
		dc.addOrder(Order.desc("name"));
		Page page = findPage(dc, 0, 20);
		
		return page.getItems();
	}

	@Override
	public List<StrSearchHot> getMatchedKeywords(String keywords) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrSearchHot.class);
		dc.add(Restrictions.like("keywords", "%" + keywords + "%"));
		dc.addOrder(Order.desc("times"));
		Page page = findPage(dc, 0, 20);
		
		return page.getItems();
	}
}
