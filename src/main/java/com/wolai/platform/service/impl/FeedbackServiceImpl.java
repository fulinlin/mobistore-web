package com.wolai.platform.service.impl;

import org.springframework.stereotype.Service;

import com.wolai.platform.entity.FeedBack;
import com.wolai.platform.service.FeedbackService;

@Service
public class FeedbackServiceImpl extends CommonServiceImpl implements FeedbackService {

	@Override
	public void create(FeedBack po) {
		getDao().saveOrUpdate(po);
	}
}
