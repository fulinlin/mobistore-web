package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.entity.SysMessageSend;

public interface MsgService extends CommonService {

	List<SysMessageSend> listByUser(String userId);
	
}
