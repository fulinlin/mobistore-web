package com.tinypace.mobistore.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.tinypace.mobistore.constant.Constant;

public class SysListener extends HttpServlet implements ServletContextListener {

	private static final long serialVersionUID = 0xe5a9f33cc18dab6L;
	protected final Logger log = Logger.getLogger(getClass());

	public void contextInitialized(ServletContextEvent sce) {
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/config.properties");
		
        try {
			prop.load(in);
			
			String webRoot = prop.getProperty("web.root");
			String webUri = prop.getProperty("web.uri");
			String clientRootDev = prop.getProperty("client.path.development");
			String clientRootProd = prop.getProperty("client.path.production");
			Constant.WEB_ROOT = webRoot;
			Constant.WEB_PATH = Constant.WEB_ROOT + webUri;
			Constant.CLIENT_PATH_DEVELOPMENT = clientRootDev;
			Constant.CLIENT_PATH_PRODUCTION = clientRootProd;
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent servletcontextevent) {
	}

}