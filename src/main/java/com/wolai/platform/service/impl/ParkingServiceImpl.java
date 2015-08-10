package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.ParkingRecord.ParkStatus;
import com.wolai.platform.entity.TempParkingRecord;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.TimeUtils;

@Service
public class ParkingServiceImpl extends CommonServiceImpl implements ParkingService {

	@Override
	public ParkingRecord parkInfo(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		dc.add(Restrictions.gt("driveInTime", dt));
		dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.UNKONW));
		dc.addOrder(Order.desc("driveInTime"));
		dc.setFetchMode("parkingLot", FetchMode.JOIN);

		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (ParkingRecord)ls.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Page parkHistory(String userId, int startIndex, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc.addOrder(Order.desc("driveInTime"));
		dc.setFetchMode("parkingLot", FetchMode.JOIN);
		
		Page ls = findPage(dc, startIndex, pageSize);
		
		return ls;
	}

	@Override
	public ParkingRecord getParkingRecordbyExNo(String exNo,String parkingLotId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("exNo",exNo));
		dc.add(Restrictions.eq("parkingLotId",parkingLotId));
		dc.add(Restrictions.ne("parkStatus", ParkStatus.OUT));
		return (ParkingRecord) getDao().getByCriteria(dc);
	}
	
	@Override
	public TempParkingRecord getTempParkingRecordbyCarNo(String carNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(TempParkingRecord.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("carNo",carNo));
		return (TempParkingRecord) getDao().getByCriteria(dc);
	}

	@Override
	public void savePaingRecord(ParkingRecord record) {
		if(record.getCarNoId()!=null){
			updatePaingRecord(record.getCarNo());
			getDao().saveOrUpdate(record);
		}else{
			DetachedCriteria dc = DetachedCriteria.forClass(TempParkingRecord.class);
			dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
			dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
			dc.add(Restrictions.eq("carNo",record.getCarNo()));
			TempParkingRecord temp = (TempParkingRecord) getDao().FindFirstByCriteria(dc);
			if(temp==null){
				temp= new TempParkingRecord();
			}
			BeanUtilEx.copyProperties(temp, record);
			getDao().saveOrUpdate(temp);
		}
		
	}

	@Override
	public void deleteTempRecord(String exNo) {
		String hql ="delete "+TempParkingRecord.class.getSimpleName()+" where exNo=?";
		getDao().executeByHql(hql, exNo);
	}

	@Override
	public void updatePaingRecord(String carNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("carNo",carNo));
		dc.add(Restrictions.or(Restrictions.eq("parkStatus",ParkStatus.IN),Restrictions.eq("parkStatus",ParkStatus.PARKED)));
		
		List<ParkingRecord> parkingRecords = getDao().findAllByCriteria(dc);
		for(ParkingRecord record1:parkingRecords){
			record1.setParkStatus(ParkStatus.UNKONW);
		}
		getDao().updateAll(parkingRecords);
		
	}
	
}
