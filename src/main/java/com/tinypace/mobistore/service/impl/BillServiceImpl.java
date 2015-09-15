package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Bill.PayStatus;
import com.tinypace.mobistore.entity.ParkingRecord.ParkStatus;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.util.DateUtils;

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
	
//	@Override
//	public List<Bill> getPostPayCarsFromBill() {
//		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
//		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
//		dc.add(Restrictions.eq("payStatus",PayStatus.INIT));
//		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
//		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
//		return getDao().findAllByCriteria(dc);
//	}
//
//	@Override
//	public List<Bill> getPostPayBillByCarNo(String carNo) {
//		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
//		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
//		dc.add(Restrictions.eq("payStatus",PayStatus.INIT));
//		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
//		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
//		dc.add(Restrictions.eq("carNo",carNo));
//		
//		return getDao().findAllByCriteria(dc);
//	}
//
	@Override
	public Boolean hasUnPayedBill(String CarNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
		dc.createAlias("parkingRecord", "parkingRecord",JoinType.LEFT_OUTER_JOIN);
		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
		dc.add(Restrictions.ne("payStatus",PayStatus.SUCCESSED));
		dc.add(Restrictions.ne("payStatus",PayStatus.IN_PROGRESS));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("carNo",CarNo));
		dc.add(Restrictions.eq("parkingRecord.parkStatus",ParkStatus.OUT));
		List ls = getDao().findAllByCriteria(dc);
		Object obj = getDao().FindFirstByCriteria(dc);
		return obj!=null;
	}

	@Override
	public List<Bill> findPaidBillButNotCallBack() {
		DetachedCriteria dc = DetachedCriteria.forClass(Bill.class);
		dc.add(Restrictions.eq("isPostPay",Boolean.TRUE));
		dc.add(Restrictions.eq("payStatus",PayStatus.IN_PROGRESS));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.le("tradeSendTime", DateUtils.addHours(new Date(), -1)));
		
		return getDao().findAllByCriteria(dc);
	}

}
