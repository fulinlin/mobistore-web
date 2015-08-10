package com.wolai.platform.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;

public interface TestService extends CommonService {
	SysUser loginNotUpdateTokenPers(String phone, String password);

	List<SysUser> listTestUsers();
	
	List<License> listCarsOut();
	List<License> listCarsIn();
	List<ParkingLot> listParkingLot();

	Map<String, Object> enter(String carToIn, String lotToIn);

	Map<String, Object> exit(String carToOut);
}
