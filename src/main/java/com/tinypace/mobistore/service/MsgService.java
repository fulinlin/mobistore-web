package com.tinypace.mobistore.service;


import java.util.Date;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.SysUser;

public interface MsgService extends CommonService {

	Page listByUser(String userId, Date after, Date before, int startIndex, int pageSize);

	void sendAppMsg(SysUser user, String title, String msg1, String msg2);
	void sendAppMsg(String title, String msg1, String msg2);
}
