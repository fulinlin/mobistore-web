package com.wolai.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.ExchangePlan;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.entity.RewardPoints;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.SysUser.PayType;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.entity.SysVerificationCode;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.ExchangePlanService;
import com.wolai.platform.service.PromotionService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.CommonUtils;
import com.wolai.platform.vo.UserVo;

@Service
public class UserServiceImpl extends CommonServiceImpl implements UserService {
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	ExchangePlanService exchangePlanService;
	
	@Autowired
	CouponService couponService;

	@Override
	public SysUser saveOrUpdate(SysUser user) {
		Enterprise enterprise =null;
		if(StringUtils.isBlank(user.getId())&&UserType.ENTERPRISE.equals(user.getCustomerType())){
			enterprise=new Enterprise();
		}
		getDao().saveOrUpdate(user);
		
		if(enterprise!=null){
			enterprise.setUserId(user.getId());
			enterprise.setName(user.getName());
			getDao().save(enterprise);
		}
		return user;
	}

	
	private SysUser loginPers(String phone, String password, boolean updateToken, String deviceType, String deviceToken) {
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
			
			user.setDeviceType(deviceType);
			if (deviceToken != null) {
				user.setDeviceToken(deviceToken);
			}
			user.setLastLoginTime(new Date());
			saveOrUpdate(user);
		} 
		return user;
	}
	@Override
	public SysUser loginPers(String phone, String password, String deviceType, String deviceToken) {
		return loginPers(phone, password, true, deviceType, deviceToken);
	}
	
	@Override
	public SysUser loginWithToken(String token) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("authToken", token));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser)users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public SysUser logoutPers(String token) {
		SysUser user = getUserByToken(token);
		if (user != null) {
			user.setAuthToken(null);
			saveOrUpdate(user);
			return user;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> registerPers(String phone, String password) {
		Map<String, Object> ret = new HashMap<String, Object>(); 
		
		SysUser po = getUserByPhone(phone);
		if (po != null) {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "手机已被占用");
			return ret;
		}
		
		SysUser user = new SysUser();
		user.setMobile(phone);
		user.setPassword(password);
		user.setCustomerType(UserType.INDIVIDUAL);
		String newToken = UUID.randomUUID().toString();
		user.setAuthToken(newToken);
		user.setLastLoginTime(new Date());
		saveOrUpdate(user);
		
		RewardPoints rewardPoints = new RewardPoints();
		rewardPoints.setBalance(0);
		rewardPoints.setUserId(user.getId());
		promotionService.saveOrUpdate(rewardPoints);
		
		// 按促销，送代金券
		promotionService.registerPresent(user.getId());
		
		ret.put("token", newToken);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}

	@Override
	public SysUser getUserByPhone(String phone) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("mobile", phone));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser) users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser getUserByToken(String token) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("authToken", token));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser) users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser getUserByPhoneAndPassword(String phone, String password) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("mobile", phone));
		List users = findAllByCriteria(dc);
		if (users.size() > 0) {
			return (SysUser) users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> updatePassword(String phone, String password,String newPassword) {
		Map<String, Object> ret = new HashMap<String, Object>(); 

		SysUser po = getUserByPhoneAndPassword(phone, password);
		if (po == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "user not found");
			return ret;
		}
		
		po.setMobile(phone);
		po.setPassword(newPassword);
		po.setCustomerType(UserType.INDIVIDUAL);
		String newToken = UUID.randomUUID().toString();
//		po.setAuthToken(newToken);
		po.setLastLoginTime(new Date());
		saveOrUpdate(po);
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
	@Override
	public Map<String, Object> updateProfile(SysUser user, String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		user.setName(name);
		saveOrUpdate(user);
		
		UserVo vo = new UserVo();
		BeanUtilEx.copyProperties(vo, user);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		
		return ret;
	}

	@Override
	public String createCode(String phone) {
		String code = CommonUtils.RandomNum(4);
		SysVerificationCode vcode = new SysVerificationCode();
		vcode.setMobile(phone);
		vcode.setCode(code);
		vcode.setCreateTime(new Date());
		getDao().saveOrUpdate(vcode);
		return code;
	}

	@Override
	public Map<String, Object> resetPasswordPers(String phone, String password) {
		Map<String, Object> ret = new HashMap<String, Object>(); 
		
		SysUser user = getUserByPhone(phone);
		if (user == null) {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "用户不存在");
			return ret;
		}
		
		user.setPassword(password);
		String newToken = UUID.randomUUID().toString();
		user.setAuthToken(newToken);
		user.setLastLoginTime(new Date());
		saveOrUpdate(user);
		
		ret.put("token", newToken);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
    
	public Page<SysUser> findAllByPage(SysUser user,int start,int limit) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		if(user!=null){
			// 是否查看禁用账户相关设置
			if(user.getIsDisable()!=null){
				dc.add(Restrictions.eq("isDisable",user.getIsDisable()));
			}
			
			// 手机号模糊查询
			if(StringUtils.isNotEmpty(user.getMobile())){
				dc.add(Restrictions.like("mobile",user.getMobile(),MatchMode.ANYWHERE));
			}
			
			if(user.getCustomerType()!=null){
				dc.add(Restrictions.eq("customerType",user.getCustomerType()));
			}
		}
		return getDao().findPage(dc, start, limit);
	}

	@Override
	public boolean validateMobile(String mobile,String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("mobile", mobile));
		if(StringUtils.isNotBlank(userId)){
			dc.add(Restrictions.ne("id", userId));
		}
		SysUser user = (SysUser) getDao().getByCriteria(dc);
		return user!=null;
	}

	@Override
	public Enterprise getEnterpriceInfo(String userId) {
		if(StringUtils.isNotBlank(userId)){
			DetachedCriteria dc = DetachedCriteria.forClass(Enterprise.class);
			dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
			dc.add(Restrictions.eq("userId", userId));
			return (Enterprise) getDao().getByCriteria(dc);
		}
		return null;
	}

	@Override
	public SysUser getTempUserPers() {
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("customerType", UserType.TEMP));
		SysUser user = (SysUser) getDao().getByCriteria(dc);
		if(user==null){
			user = new SysUser();
			user.setCustomerType(UserType.TEMP);
			user.setPayType(PayType.PERPAID);
			user.setName("系统占位用户");
			getDao().save(user);
		}
		return user;
	}

	@Override
	public void setPayTypePers(SysUser user, String payType) {
		user.setPayType(SysUser.PayType.value(payType));
		saveOrUpdate(user);
	}
}
