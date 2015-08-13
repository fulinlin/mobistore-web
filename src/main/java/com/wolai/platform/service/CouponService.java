package com.wolai.platform.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Enterprise;

public interface CouponService extends CommonService {

	List listByUser(String userId);
	void holdCouponPers(String couponId, String oldCouponId);

    Page listMoneyByUser(String userId, int startIndex, int pageSize);

    Page listTimeByUser(String userId, int startIndex, int pageSize);

    Map<String, Object> usePers(String id, String userId);

    long countAllByUser(String userId);
    long countMoneyByUser(String userId);
    long countTimeByUser(String userId);

    String deductTime(Enterprise enterprise, List<Coupon> list ,long Time);

    Coupon getSuitableMoneyCoupon(BigDecimal money,String userId);
}
