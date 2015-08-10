package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.TestService;
import com.wolai.platform.util.TimeUtils;

@Service
public class TestServiceImpl extends CommonServiceImpl implements TestService {
	private SysUser loginPers(String phone, String password, boolean updateToken) {
		String newToken = null;
		List<SysUser> users;	

		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("mobile", phone));
		dc.add(Restrictions.eq("password", password));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		users = (List<SysUser>) findAllByCriteria(dc);
		
		SysUser user = null;
		if (users.size() > 0) {
			user = users.get(0);
			newToken = UUID.randomUUID().toString();
			if (updateToken || StringUtils.isEmpty(user.getAuthToken())) {
				user.setAuthToken(newToken);
			}
			user.setLastLoginTime(new Date());
			saveOrUpdate(user);
		} 
		return user;
	}
	@Override
	public SysUser loginNotUpdateTokenPers(String phone, String password) {
		return loginPers(phone, password, false);
	}
	@Override
	public List<SysUser> listTestUsers() {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		dc.add(Restrictions.eq("customerType", SysUser.UserType.INDIVIDUAL));
		dc.add(Restrictions.ne("name", ""));
		dc.addOrder(Order.desc("name"));
		List<SysUser> users = (List<SysUser>) findAllByCriteria(dc);
		return users;
	}
	@Override
	public List<License> listCarsOut() {
		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		
		String  hql = "from License lc where lc.isDelete = ? and lc.isDisable = ? and lc.id not in (" +
					"select id from ParkingRecord pr where pr.parkStatus != ? and pr.driveInTime > ?"
				+ ")";
		
		List ls = getListByHQL(hql, false, false, ParkingRecord.ParkStatus.OUT, dt);
		return ls;
	}
	@Override
	public List<License> listCarsIn() {
		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		
		String  hql = "from License lc where lc.isDelete = ? and lc.isDisable = ? and lc.id in (" +
					"select id from ParkingRecord pr where pr.parkStatus != ? and pr.driveInTime > ?"
				+ ")";
		
		List ls = getListByHQL(hql, false, false, ParkingRecord.ParkStatus.OUT, dt);
		return ls;
	}
	@Override
	public List<ParkingLot> listParkingLot() {
		DetachedCriteria dc2 = DetachedCriteria.forClass(ParkingLot.class);
		dc2.add(Restrictions.eq("isDelete", false));
		dc2.add(Restrictions.eq("isDisable", false));
		dc2.addOrder(Order.asc("name"));
		List lots = findAllByCriteria(dc2);
		
		return lots;
	}
	@Override
	public Map<String, Object> enter(String carToIn, String lotToIn) {
		
		return null;
	}
	@Override
	public Map<String, Object> exit(String carToOut) {
		
		return null;
	}
}
