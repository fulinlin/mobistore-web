package com.wolai.platform.config;

import com.wolai.platform.util.CustomizedPropertyConfigurer;
import com.wolai.platform.util.SpringContextHolder;

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
