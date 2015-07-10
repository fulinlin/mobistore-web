package com.wolai.platform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wolai.platform.entity.License;
import com.wolai.platform.service.LicensePlateService;

@Service
public class LicenseServiceImpl extends CommonServiceImpl implements LicensePlateService {

	@Override
	public List<License> listByUser(String userId) {
		return null;
	}
}
