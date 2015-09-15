package com.tinypace.mobistore.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.Enterprise;
import com.tinypace.mobistore.entity.License;
import com.tinypace.mobistore.entity.ParkingLot;
import com.tinypace.mobistore.entity.SysUser;

public interface TestService extends CommonService {
	SysUser loginNotUpdateTokenPers(String phone, String password);

	List<SysUser> listTestUsers();
	
	List<License> listCarsOut(String userId);
	List<License> listCarsIn(String userId);
	List<ParkingLot> listParkingLot();

	String enter(String carNo);
	String payCheck(String carNo);
	String leave(String carNo, String action);

	String bound(String url, String token, HttpServletRequest request);
	void initRemoteUrl(HttpServletRequest request);
}
