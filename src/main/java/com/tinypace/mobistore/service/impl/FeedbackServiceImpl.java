package com.tinypace.mobistore.service.impl;

import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.FeedBack;
import com.tinypace.mobistore.service.FeedbackService;

@Service
public class FeedbackServiceImpl extends CommonServiceImpl implements FeedbackService {

	@Override
	public void create(FeedBack po) {
		getDao().saveOrUpdate(po);
	}
}
