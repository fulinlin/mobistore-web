package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.TestService;
import com.wolai.platform.util.IdGen;
import com.wolai.platform.util.TimeUtils;
import com.wolai.platform.util.WebClientUtil;

@Service
public class TestServiceImpl extends CommonServiceImpl implements TestService {
	public static String REMOTE_URL = "http://10.0.1.109/wolai/wolai/rt/";
	
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
		dc.add(Restrictions.like("name", "%测试%"));
		dc.addOrder(Order.asc("name"));
		List<SysUser> users = (List<SysUser>) findAllByCriteria(dc);
		return users;
	}
	
	@Override
	public List<License> listCarsOut(String userId) {
//		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		
		String  hql = "from License lc where userId=? and lc.isDelete = ? and lc.isDisable = ? and lc.id not "
				+ " in (" +
					"select carNoId from ParkingRecord pr where (pr.parkStatus = ? or pr.parkStatus = ?)"
				+ ")";
		
		List ls = getListByHQL(hql, userId, false, false, ParkingRecord.ParkStatus.IN, ParkingRecord.ParkStatus.PARKED);
		return ls;
	}
	
	@Override
	public List<License> listCarsIn(String userId) {
		Date dt = TimeUtils.getDateBefore(new Date(), 10);
		
		
		String  hql1 = "select carNoId from ParkingRecord pr where (pr.parkStatus = ? or pr.parkStatus = ?)";
		List<License> ls1 = getListByHQL(hql1, ParkingRecord.ParkStatus.IN, ParkingRecord.ParkStatus.PARKED);
		
		String  hql = "from License lc where userId=? and lc.isDelete = ? and lc.isDisable = ? and lc.id "
				+ " in (" +
					"select carNoId from ParkingRecord pr where (pr.parkStatus = ? or pr.parkStatus = ?)"
				+ ")";
		
		List<License> ls = getListByHQL(hql, userId, false, false, ParkingRecord.ParkStatus.IN, ParkingRecord.ParkStatus.PARKED);
		for (License l:ls) {
			DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
			dc.add(Restrictions.eq("isDelete", false));
			dc.add(Restrictions.eq("isDisable", false));
			dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.OUT));
			dc.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.UNKONW));
			dc.add(Restrictions.eq("carNoId", l.getId()));
			dc.addOrder(Order.desc("driveInTime"));
			ParkingRecord park = (ParkingRecord) FindFirstByCriteria(dc);
			l.setIsPaid(park.getIsPaid());
		}
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
	public String bound(String url, String token) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", url);
		map.put("token", token);
		
		String ret = WebClientUtil.post(REMOTE_URL + "simuseturltoken", JSON.toJSONString(map));
		return ret;
	}
	
	@Override
	public String enter(String carNo) {
		// {"carNo":"","exNo":"","entranceNo":""} 
		Map<String, String> map = new HashMap<String, String>();
		map.put("carNo", carNo);
		map.put("exNo", IdGen.uuid());
		map.put("entranceNo", "11");
		
		String ret = WebClientUtil.post(REMOTE_URL + "simucarenter", JSON.toJSONString(map));
		return ret;
	}
	
	@Override
	public String exit(String carNo) {
		DetachedCriteria dc2 = DetachedCriteria.forClass(ParkingRecord.class);
		dc2.add(Restrictions.eq("isDelete", false));
		dc2.add(Restrictions.eq("isDisable", false));
		dc2.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.OUT));
		dc2.add(Restrictions.ne("parkStatus", ParkingRecord.ParkStatus.UNKONW));
		dc2.addOrder(Order.desc("id"));
		List<ParkingRecord> parks = (List<ParkingRecord>) findAllByCriteria(dc2);
		ParkingRecord park = parks.get(0);
		String exNo = park.getExNo();
		Date intime = park.getDriveInTime();
		
		// {"carNo":"苏E11111","exNo":"1","exportNo":"A1","enterTime":"1438936610000"}
		Map<String, String> map = new HashMap<String, String>();
		map.put("carNo", carNo);
		map.put("exNo", exNo);
		map.put("entranceNo", "11");
		map.put("enterTime", String.valueOf(intime.getTime()));
		
		String ret = WebClientUtil.post(REMOTE_URL + "simucarleave", JSON.toJSONString(map));
		return ret;
	}
}
