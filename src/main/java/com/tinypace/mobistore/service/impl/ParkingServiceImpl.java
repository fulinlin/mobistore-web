package com.tinypace.mobistore.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Coupon;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.entity.ParkingRecord.ParkStatus;
import com.tinypace.mobistore.entity.TempParkingRecord;
import com.tinypace.mobistore.service.ParkingService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.util.TimeUtils;
import com.tinypace.mobistore.vo.ParkingVo;

@Service
public class ParkingServiceImpl extends CommonServiceImpl implements ParkingService {

	@Override
	public ParkingRecord parkInfo(String userId) {
//		DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
//		dc.add(Restrictions.eq("userId", userId));
//		dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.OUT));
//		dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.UNKONW));
//		dc.addOrder(Order.desc("driveInTime"));
//		dc.setFetchMode("parkingLot", FetchMode.JOIN);
		
//		String  hql1 = "select bl.parkingRecordId from Bill bl where bl.parkingRecordId = ? and bl.payStatus != ?)";
//		
//		List ls1 = getListByHQL(hql1, 
//				"ff8080814f1a5730014f1a57fc530000", 
//				Bill.PayStatus.SUCCESSED
//				);
		
		String  hql = "from ParkingRecord pr where "
				+ "userId=? "
				+ "and (pr.parkStatus = ? or pr.parkStatus = ? )";
//				+ "and (pr.parkStatus = ? or pr.parkStatus = ? "
//									  + " or pr.id in ( select bl.parkingRecordId from Bill bl where bl.parkingRecordId = pr.id and bl.payStatus != ?) "
//					+ ")";
		
		List ls = getListByHQL(hql, 
				userId, 
				ParkingRecord.ParkStatus.IN,
				ParkingRecord.ParkStatus.PARKED
//				Bill.PayStatus.SUCCESSED
				);
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

	@Override
	public void setBillInfoForPark(ParkingVo vo, Bill bill) {
		if (bill != null) {
			vo.setPaytype(bill.getPaytype());
			vo.setMoney(bill.getTotalAmount());
			vo.setPaidMoney(bill.getPayAmount());
			
			Coupon coupon = bill.getCoupon();
			if (coupon != null) {
				vo.setCouponType(coupon.getType());
				vo.setCouponMoney(new BigDecimal(coupon.getMoney()));
				vo.setCouponTime(coupon.getTime());
			}
		}
	}
	
}
