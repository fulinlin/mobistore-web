/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tinypace.mobistore.servlet;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;


import com.ckfinder.connector.ServletContextFactory;
import com.ckfinder.connector.configuration.Configuration;
import com.ckfinder.connector.data.AccessControlLevel;
import com.ckfinder.connector.utils.AccessControlUtil;

/**
 * CKFinder配置
 * @author ThinkGem
 * @version 2013-01-15
 */
public class CKFinderConfig extends Configuration {

	public static final String CK_BASH_URL = "/userfiles/";

	public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);  
    }
	
	@Override
    protected Configuration createConfigurationInstance() {
		List<AccessControlLevel> accessControlLevels = this.getAccessConrolLevels();
		if(accessControlLevels.size() > 0){
			AccessControlLevel alc = this.getAccessConrolLevels().get(0);
			alc.setFolderView(true);
			alc.setFolderCreate(true);
			alc.setFolderRename(false);
			alc.setFolderDelete(false);
			
			alc.setFileView(true);
			alc.setFileUpload(true);
			alc.setFileRename(false);
			alc.setFileDelete(false);
		}
		AccessControlUtil.getInstance(this).loadACLConfig();
		try {
			this.baseURL = ServletContextFactory.getServletContext().getContextPath()+"/userfiles/";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new CKFinderConfig(this.servletConf);
    }

    @Override  
    public boolean checkAuthentication(final HttpServletRequest request) {
        return true;
    }

}
