package com.wolai.platform.service.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.ParkingRecord.ParkStatus;
import com.wolai.platform.entity.TempParkingRecord;
import com.wolai.platform.service.LicenseService;

@Service
public class LicenseServiceImpl extends CommonServiceImpl implements LicenseService {

	@Override
	public Page listByUser(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(License.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.addOrder(Order.asc("carNo"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}

	@Override
	public void create(License po) {
		getDao().saveOrUpdate(po);
		
		DetachedCriteria dc = DetachedCriteria.forClass(TempParkingRecord.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("carNo",po.getCarNo()));
		dc.addOrder(Order.desc("driveInTime"));
		
		TempParkingRecord tempRecord = (TempParkingRecord) getDao().FindFirstByCriteria(dc);
		if (tempRecord != null) {
			ParkingRecord record = new ParkingRecord();
			record.setCarNo(record.getCarNo());
			record.setCarNoId(po.getId());
			record.setCarPicPath(tempRecord.getCarPicPath());
			record.setDriveInTime(tempRecord.getDriveInTime());
			record.setEntranceNo(tempRecord.getEntranceNo());
			record.setExNo(tempRecord.getExNo());
			record.setUserId(po.getUserId());
			record.setParkStatus(ParkStatus.IN);
			
			getDao().delete(tempRecord);
			getDao().save(record);
		}

	}

	@Override
	public void update(License po) {
		getDao().saveOrUpdate(po);
	}

	@Override
	public License getLincense(String carNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(License.class);
		dc.add(Restrictions.eq("carNo", carNo));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.setFetchMode("user", FetchMode.JOIN);
		List<License> ls = (List<License>) findAllByCriteria(dc);
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}

	@Override
	public License getLicense(String id, String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(License.class);
		dc.add(Restrictions.eq("id", id));
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		
		List<License> ls = (List<License>) findAllByCriteria(dc);
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}
}
