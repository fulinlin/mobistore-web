package com.tinypace.mobistore.config;

import com.tinypace.mobistore.util.CustomizedPropertyConfigurer;
import com.tinypace.mobistore.util.SpringContextHolder;

/**
 *  系统配置类
 * @author Ethan
 *
 */
public  class SystemConfig {
	
	CustomizedPropertyConfigurer customizedPropertyConfigurer = SpringContextHolder.getBean(CustomizedPropertyConfigurer.class);
	
	public static String getAdminPath(){
		return CustomizedPropertyConfigurer.getAdminPath();
	}
	
	public static String getMaxOverMoney(){
		return (String) CustomizedPropertyConfigurer.getContextProperty("coupon.max_over_money");
	}
}
