package com.tinypace.mobistore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrCollection;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.StrSuggestion;
import com.tinypace.mobistore.entity.StrClient.AgentType;
import com.tinypace.mobistore.entity.StrOrder.Status;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.SysVerifyCode;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.util.StringUtil;
import com.tinypace.mobistore.util.TimeUtil;

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
	public StrClient signonPers(String mobile, String password, String platform, String isWebview, String deviceToken) {
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
				client.setClientPlatform(StrClient.PlatformType.StringToEnum(platform.trim().toUpperCase()));
			}

			if (StringUtils.isNotEmpty(isWebview)) {
				AgentType agent = Boolean.valueOf(isWebview)? AgentType.WEBVIEW: AgentType.BROWSER;
				client.setClientAgent(agent);
			}

			if (StringUtils.isNotEmpty(deviceToken)) {
				client.setDeviceToken(deviceToken);
			}
			client.setLastLoginTime(new Date());
			saveOrUpdate(client);
		}
		return client;
	}

	@Override
	public StrClient signupPers(String mobile, String password, String platform, String isWebview, String deviceToken) {
		String newToken = null;
		DetachedCriteria dc = DetachedCriteria.forClass(StrClient.class);
		dc.add(Restrictions.eq("mobile", mobile));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		List<StrClient> ls = (List<StrClient>) findAllByCriteria(dc);

		if (ls.size() > 0) {
			return null;
		}

		StrClient client = new StrClient();
		newToken = UUID.randomUUID().toString();
		client.setAuthToken(newToken);
		client.setMobile(mobile);
		client.setPassword(password);

		if (StringUtils.isNotEmpty(platform)) {
			client.setClientPlatform(StrClient.PlatformType.valueOf(platform.trim().toUpperCase()));
		}

		if (StringUtils.isNotEmpty(isWebview)) {
			AgentType agent = Boolean.valueOf(isWebview)? AgentType.WEBVIEW: AgentType.BROWSER;
			client.setClientAgent(agent);
		}

		if (StringUtils.isNotEmpty(deviceToken)) {
			client.setDeviceToken(deviceToken);
		}
		client.setLastLoginTime(new Date());
		saveOrUpdate(client);

		return client;
	}

	@Override
	public boolean collectIfNeedPers(String clientId, String productId) {
		StrCollection collect = isCollected(clientId, productId);

		if (collect == null) {
			StrProduct prodcut = (StrProduct) get(StrProduct.class, productId);
			prodcut.setCollect(prodcut.getCollect() + 1);
			saveOrUpdate(prodcut);

			StrCollection his = new StrCollection();
			his.setClientId(clientId);
			his.setProductId(productId);
			his.setCollectTime(new Date());
			saveOrUpdate(his);

			return true;
		} else {
			StrProduct prodcut = (StrProduct) get(StrProduct.class, productId);
			prodcut.setCollect(prodcut.getCollect() - 1);
			saveOrUpdate(prodcut);

			collect.setIsDelete(true);
			saveOrUpdate(collect);

			return false;
		}
	}

	@Override
	public StrCollection isCollected(String clientId, String productId) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrCollection.class);
		dc.add(Restrictions.eq("productId", productId));
		dc.add(Restrictions.eq("clientId", clientId));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		List<StrCollection> ls = (List<StrCollection>) findAllByCriteria(dc);
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Long> count(String clientId) {
		Map<String, Long> ret = new HashMap<String, Long>();

		DetachedCriteria dc1 = DetachedCriteria.forClass(StrCollection.class);
		dc1.add(Restrictions.eq("clientId", clientId));
		dc1.add(Restrictions.ne("isDelete", true));
		dc1.add(Restrictions.ne("isDisable", true));
		long collectionCount = getDao().count(dc1);

		DetachedCriteria dc2 = DetachedCriteria.forClass(StrMsg.class);
		dc2.add(Restrictions.eq("clientId", clientId));
		dc2.add(Restrictions.ne("isDelete", true));
		dc2.add(Restrictions.ne("isDisable", true));
		long msgCount = getDao().count(dc2);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("waitPay", 0);
		map.put("waitShip", 0);
		map.put("waitReceive", 0);
		map.put("waitRate", 0);
		String hql = "select count(o.status) from StrOrder o where o.clientId = ? group by o.status";
		List ls = getDao().findMapByHQL(hql, clientId);

		if (ls.size() > 0) {
			Map<String, Long> countMap = getDao().findMapByHQL(hql, clientId).get(0);
			for (String str : countMap.keySet()) {
				int key = Integer.valueOf(str);
				if (key < 0) {
					continue;
				}

				int val = countMap.get(str).intValue();
				if (key == Status.INIT.value().longValue()) {
					map.put("waitPay", map.get("waitPay") + val);
				} else if (key == Status.PAID.value().longValue())  {
					map.put("waitShip", map.get("waitShip") + val);
				} else if (key == Status.SHIPPING.value().longValue())  {
					map.put("waitReceive", map.get("waitReceive") + val);
				} else if (key == Status.RECEIVED.value().longValue())  {
					map.put("waitRate", map.get("waitRate") + val);
				}
			}
		}

		ret.put("collectionCount", collectionCount);
		ret.put("msgCount", msgCount);

		ret.put("waitPay", map.get("waitPay").longValue());
		ret.put("waitShip", map.get("waitShip").longValue());
		ret.put("waitReceive", map.get("waitReceive").longValue());
		ret.put("waitRate", map.get("waitRate").longValue());

		return ret;
	}

	@Override
	public SysVerifyCode forgetPaswordPers(String mobile) {
		StrClient client = getClientByMobile(mobile);
		if (client == null) {
			return null;
		}

		SysVerifyCode po = new SysVerifyCode();
		String code = StringUtil.RandomNumbString(4);
		Date now = new Date();
		po.setClientId(client.getId());
		po.setCode(code);
		po.setCreateTime(now);
		po.setExpireTime(new Date(now.getTime() + 300000));
		saveOrUpdate(po);

		return po;
	}

	@Override
	public StrClient getClientByMobile(String mobile) {
		DetachedCriteria dc = DetachedCriteria.forClass(StrClient.class);
		dc.add(Restrictions.eq("mobile", mobile));
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
	public StrClient resetPasswordPers(String verifyCode, String mobile,
			String password, String platform, String isWebview,
			String deviceToken) {

		StrClient client = getClientByMobile(mobile);
		if (client == null) {
			return null;
		}

		String newToken = null;
		DetachedCriteria dc = DetachedCriteria.forClass(SysVerifyCode.class);
		dc.add(Restrictions.eq("clientId", client.getId()));
		dc.add(Restrictions.eq("code", verifyCode));
		dc.add(Restrictions.ne("isDelete", true));
		dc.add(Restrictions.ne("isDisable", true));
		List<SysVerifyCode> ls = (List<SysVerifyCode>) findAllByCriteria(dc);

		if (ls.size() < 1) {
			return null;
		}

		newToken = UUID.randomUUID().toString();
		client.setAuthToken(newToken);
		client.setMobile(mobile);
		client.setPassword(password);

		if (StringUtils.isNotEmpty(platform)) {
			client.setClientPlatform(StrClient.PlatformType.valueOf(platform.trim().toUpperCase()));
		}

		if (StringUtils.isNotEmpty(isWebview)) {
			AgentType agent = Boolean.valueOf(isWebview)? AgentType.WEBVIEW: AgentType.BROWSER;
			client.setClientAgent(agent);
		}

		if (StringUtils.isNotEmpty(deviceToken)) {
			client.setDeviceToken(deviceToken);
		}
		client.setLastLoginTime(new Date());
		saveOrUpdate(client);

		SysVerifyCode code = ls.get(0);
		code.setIsDelete(true);
		saveOrUpdate(code);

		return client;
	}

}
