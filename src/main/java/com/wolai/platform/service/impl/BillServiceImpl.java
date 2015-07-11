package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.Bill;
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
	
}
