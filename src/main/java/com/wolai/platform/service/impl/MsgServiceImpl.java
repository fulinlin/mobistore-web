package com.wolai.platform.service.impl;

import java.util.Date;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.SysMessageSend;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.util.PushUtil;

@Service
public class MsgServiceImpl extends CommonServiceImpl implements MsgService {
	private PushUtil androidPush = null;
	private PushUtil iosPush = null;

	@Override
	public Page listByUser(String userId, Date after, Date before, int startIndex, int pageSize) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysMessageSend.class);
		dc.setFetchMode("message", FetchMode.JOIN);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.add(Restrictions.ge("sendTime", after));
		dc.add(Restrictions.le("sendTime", before));
		
		dc.addOrder(Order.desc("sendTime"));
		Page page = findPage(dc, startIndex, pageSize);
		
		return page;
	}
	
	@Override
	public void sendAppMsg(SysUser user, String msg) {
		if (Constant.DEVICE_TYPE_ANDROID.equals(user.getDeviceType())) {
			getAndroidSendor().sendAndroidUnicastMsg(user.getDeviceToken(), msg);
		} else if (Constant.DEVICE_TYPE_IOS.equals(user.getDeviceType())) {
			getIosSendor().sendIosUnicast(user.getDeviceToken(), msg);
		}		
	}
	@Override
	public void sendAppMsg(String msg) {
		getAndroidSendor().sendAndroidBroadcastMsg(msg);
		getIosSendor().sendIosBroadcast(msg);
	}

	private PushUtil getAndroidSendor() {
		if (this.androidPush == null) {
			this.androidPush = new PushUtil(PushUtil.APP_KEY_ANDROID, PushUtil.APP_SECRET_ANDROID);
		}
		return this.androidPush;
	}
	
	private PushUtil getIosSendor() {
		if (this.iosPush == null) {
			this.iosPush = new PushUtil(PushUtil.APP_KEY_IOS, PushUtil.APP_SECRET_IOS);
		}
		return this.iosPush;
	}

}
