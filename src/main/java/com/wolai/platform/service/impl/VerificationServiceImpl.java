package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
		Date now = new Date();
		long time = now.getTime() - (5 * 60 * 1000); // 10分钟前
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysVerificationCode.class);
		dc.add(Restrictions.eq("mobile", phone));
		dc.add(Restrictions.ge("createTime", new Date(time)));
		dc.addOrder(Order.desc("createTime"));
		//TODO:
//		dc.add(Restrictions.eq("code", code));

		List<SysVerificationCode> ls = (List<SysVerificationCode>) findAllByCriteria(dc);
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}
}
