package com.wolai.platform.service.impl;

import java.util.ArrayList;
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

import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.entity.SysMessageSend;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.LicenseVo;
import com.wolai.platform.vo.MessageVo;

@Service
public class MsgServiceImpl extends CommonServiceImpl implements MsgService {

	@Override
	public List<SysMessageSend> listByUser(String userId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysMessageSend.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.addOrder(Order.desc("sendTime"));
		List<SysMessageSend> ls = (List<SysMessageSend>) findAllByCriteria(dc);
		
		return ls;
	}

}
