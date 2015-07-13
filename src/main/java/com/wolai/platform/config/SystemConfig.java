package com.wolai.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *  系统配置类
 * @author Ethan
 *
 */
@Configuration
public  class SystemConfig {
	
	/**
	 * 管理员登录path
	 */
	@Value("${adminPath}")
	private String adminPath;

	public String getAdminPath() {
		return adminPath;
	}
}
