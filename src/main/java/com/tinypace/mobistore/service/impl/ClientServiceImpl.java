package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.service.ClientService;

@Service
public class ClientServiceImpl extends CommonServiceImpl implements ClientService {
	@Override
	public StrClient getClientByToken(String token) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrClient.class);
		dc.add(Restrictions.eq("authToken", token));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		
		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (StrClient) ls.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public StrClient signonPers(String mobile, String password, String platform, String agent, String deviceToken) {
		String newToken = null;
		DetachedCriteria dc = DetachedCriteria.forClass(StrClient.class);
		dc.add(Restrictions.eq("mobile", mobile));
		dc.add(Restrictions.eq("password", password));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		List<StrClient> ls = (List<StrClient>) findAllByCriteria(dc);
		
		StrClient client = null;
		if (ls.size() > 0) {
			client = ls.get(0);
			newToken = UUID.randomUUID().toString();
			client.setAuthToken(newToken);
			
			if (StringUtils.isNotEmpty(platform)) {
				client.setClientPlatform(StrClient.PlatformType.valueOf(platform.trim().toUpperCase()));
			}
			
			if (StringUtils.isNotEmpty(agent)) {
				client.setClientAgent(StrClient.AgentType.valueOf(agent.trim().toUpperCase()));
			}
			
			if (StringUtils.isNotEmpty(deviceToken)) {
				client.setDeviceToken(deviceToken);
			}
			client.setLastLoginTime(new Date());
			saveOrUpdate(client);
		} 
		return client;
	}
}
