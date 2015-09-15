package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.Coupon;
import com.tinypace.mobistore.entity.ExchangePlan;
import com.tinypace.mobistore.entity.Promotion;
import com.tinypace.mobistore.entity.Coupon.CouponType;
import com.tinypace.mobistore.entity.Promotion.ExchangeCode;
import com.tinypace.mobistore.service.CouponService;
import com.tinypace.mobistore.service.ExchangePlanService;
import com.tinypace.mobistore.service.PromotionService;

@Service
public class PromotionServiceImpl extends CommonServiceImpl implements PromotionService {
	
	@Autowired
	ExchangePlanService exchangePlanService;
	
	@Autowired
	CouponService couponService;

	@Override
	public Page list(int startIndex, int pageSize) {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
		dc.add(Restrictions.ne("code", ExchangeCode.REGISTER_PRESENT));
		dc.add(Restrictions.eq("recommended", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		dc.addOrder(Order.asc("startTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public List listRecommended() {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
		dc.add(Restrictions.ne("code", ExchangeCode.REGISTER_PRESENT));
		dc.add(Restrictions.eq("recommended", true));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		dc.addOrder(Order.asc("startTime"));
		Page page = findPage(dc, 0, 10);
		
		return page.getItems();
	}
	
	@Override
	public Promotion getRegisterPresent () {
		Date now = new Date();
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
		
		dc.add(Restrictions.eq("code", ExchangeCode.REGISTER_PRESENT));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.le("startTime", now));
		dc.add(Restrictions.ge("endTime", now));
		
		dc.addOrder(Order.asc("startTime"));
		List ls = findAllByCriteria(dc);
		
		if (ls.size() > 0) {
			return (Promotion) ls.get(0);
		} else {
			return null;
		}
		
	}

	@Override
	public void registerPresent(String userId) {
		Promotion promotion = getRegisterPresent();
		if (promotion != null) {
			List<ExchangePlan> exchanges = exchangePlanService.listByPromotion(promotion.getId());
			if (exchanges.size() > 0) {
				ExchangePlan plan = exchanges.get(0);
				int number = plan.getNumber();
				
				for (int i = 0; i < number; i++) {
					Coupon coupon = new Coupon();
					coupon.setOwnerId(userId);
					coupon.setStartDate(plan.getStartTime());
					coupon.setEndDate(plan.getEndTime());
					coupon.setType(CouponType.MONEY); // 兑换的一定是抵时券
					coupon.setMoney(plan.getFaceValue());
					coupon.setOrigin("注册赠送");
					coupon.setNote("测试数据");
					couponService.saveOrUpdate(coupon);
				}
			}
		}
	}

}
