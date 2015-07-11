package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.service.CouponService;

@Service
public class CouponServiceImpl extends CommonServiceImpl implements CouponService {

	@Override
	public Page listByUser(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("ownerId", userId));
		dc.add(Restrictions.eq("isUsed", false));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public Page listMoneyByUser(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("ownerId", userId));
		dc.add(Restrictions.eq("type", CouponType.MONEY));
		dc.add(Restrictions.eq("isUsed", false));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public Page listTimeByUser(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("ownerId", userId));
		dc.add(Restrictions.eq("type", CouponType.TIME));
		dc.add(Restrictions.eq("isUsed", false));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public Map<String, Object> usePers(String couponId, String userId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Date now = new Date();
		
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("id", couponId));
		dc.add(Restrictions.eq("ownerId", userId));
		dc.add(Restrictions.le("startDate", now));
		dc.add(Restrictions.ge("endDate", now));
		dc.add(Restrictions.eq("isUsed", false));
		
		List<Coupon> coupons = (List<Coupon>) findAllByCriteria(dc);
		if (coupons.size() < 1) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "coupon not available");
			return ret;
		}
		
		Coupon coupon = coupons.get(0);
		coupon.setIsUsed(true);
//		getDao().saveOrUpdate(coupon);
		
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}

	@Override
	public long countMoneyByUser(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("ownerId", userId));
		dc.add(Restrictions.eq("type", CouponType.MONEY));
		dc.add(Restrictions.eq("isUsed", false));
		return getDao().count(dc);
	}

	@Override
	public long countTimeByUser(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
		dc.add(Restrictions.eq("ownerId", userId));
		dc.add(Restrictions.eq("type", CouponType.TIME));
		dc.add(Restrictions.eq("isUsed", false));
		return getDao().count(dc);
	}
}
