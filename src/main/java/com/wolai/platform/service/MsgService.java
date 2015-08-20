package com.wolai.platform.service;


import java.util.Date;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.SysUser;

public interface MsgService extends CommonService {

	Page listByUser(String userId, Date after, Date before, int startIndex, int pageSize);

	void sendAppMsg(SysUser user, String title, String msg1, String msg2);
	void sendAppMsg(String title, String msg1, String msg2);
}
