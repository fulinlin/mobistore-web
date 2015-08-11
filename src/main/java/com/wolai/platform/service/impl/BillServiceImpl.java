package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.service.BillService;

@Service
public class BillServiceImpl extends CommonServiceImpl implements BillService {

	@Override
	public Bill getBillByParking(String parkingId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
		dc.add(Restrictions.eq("parkingRecordId", parkingId));
		dc.setFetchMode("coupon", FetchMode.JOIN);
		List<Bill> ls = (List<Bill>) findAllByCriteria(dc);
		if (ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}
	
	@Override
	public List<Bill> getPostPayCarsFromBill() {
		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
		dc.add(Restrictions.eq("payStatus",PayStatus.INIT));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		return getDao().findAllByCriteria(dc);
	}

	@Override
	public List<Bill> getPostPayBillByCarNo(String carNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
		dc.add(Restrictions.eq("payStatus",PayStatus.INIT));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("carNo",carNo));
		
		return getDao().findAllByCriteria(dc);
	}

	@Override
	public Boolean hasUnPayedBill(String CarNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
		dc.createAlias("parkingRecord", "parkingRecord",JoinType.LEFT_OUTER_JOIN);
		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
		dc.add(Restrictions.ne("payStatus",PayStatus.SUCCESSED));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("carNo",CarNo));
		
		return null;
	}

}
