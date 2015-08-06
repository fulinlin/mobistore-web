package com.wolai.platform.listener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.unionpay.acp.sdk.SDKConfig;

public class SysListener extends HttpServlet implements ServletContextListener {

	private static final long serialVersionUID = 0xe5a9f33cc18dab6L;
	protected final Logger log = Logger.getLogger(getClass());

	public void contextInitialized(ServletContextEvent sce) {
//		Properties prop = new Properties();
//		InputStream in = getClass().getResourceAsStream("/appConfig.properties");
//		
//        try {
//			prop.load(in);
//        } catch (IOException e) {
//			e.printStackTrace();
//		}
		
		SDKConfig.getConfig().loadPropertiesFromSrc();
	}

	public void contextDestroyed(ServletContextEvent servletcontextevent) {
	}

}