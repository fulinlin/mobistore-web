package com.wolai.platform.service;

import java.util.List;
import java.util.Map;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Enterprise;

public interface CouponService extends CommonService {

    Page listByUser(String userId, int startIndex, int pageSize);

    Page listMoneyByUser(String userId, int startIndex, int pageSize);

    Page listTimeByUser(String userId, int startIndex, int pageSize);

    Map<String, Object> usePers(String id, String userId);

    long countMoneyByUser(String userId);

    long countTimeByUser(String userId);

    String deductTime(Enterprise enterprise, List<Coupon> list ,long Time);
}
