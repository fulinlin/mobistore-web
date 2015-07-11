package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysVerificationCode;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.VerificationService;

@Service
public class VerificationServiceImpl extends CommonServiceImpl implements VerificationService {

	@Override
	public SysVerificationCode checkCode(String phone, String code) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysVerificationCode.class);
		dc.add(Restrictions.eq("mobile", phone));
		dc.add(Restrictions.eq("code", code));

		List<SysVerificationCode> ls = (List<SysVerificationCode>) findAllByCriteria(dc);
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}
}
