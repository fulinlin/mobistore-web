package com.tinypace.mobistore.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * 
 * 
 * SpringContextHolder spring 工具类
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */

public class SpringContextHolder implements ApplicationContextAware {

    /**
     * log
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);
    
    /**
     * 
     */
    private static ApplicationContext APPLICATIONCONTEXT;
    
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        LOGGER.debug("注入ApplicationContext到SpringContextHolder:{}", APPLICATIONCONTEXT);
        APPLICATIONCONTEXT = arg0;
    }
    
    /**
     * 
     *getApplicationContext()
     * @return 返回 ApplicationContext 对象
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return APPLICATIONCONTEXT;
    }
    
    /**
     * 根据名称获得指定的bean
     * @param name bean 名称
     * @param <T> bean
     * @return 返回相关的bean
     */
    public static <T> T getBean(String name,Class<T> reqiredType) {
        checkApplicationContext();
        return APPLICATIONCONTEXT.getBean(name,reqiredType);
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @param clazz bean类型
     * @param <T> bean
     * @return 返回相关的bean
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return  APPLICATIONCONTEXT.getBean(clazz);
    }
     
    /**
     * 检查ApplicationContext不为空.
     */
    public static void checkApplicationContext() {
        if (APPLICATIONCONTEXT == null) {
            throw new IllegalStateException("applicationContext未注入，请在applicationContext.xml中配置");
        }
    }
    
    public static String getRootRealPath(){
		String rootRealPath ="";
		try {
			rootRealPath=getApplicationContext().getResource("").getFile().getAbsolutePath();
		} catch (IOException e) {
			LOGGER.warn("获取系统根目录失败");
		}
		return rootRealPath;
	}
	
	public static String getResourceRootRealPath(){
		String rootRealPath ="";
		try {
			rootRealPath=new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
		} catch (IOException e) {
			LOGGER.warn("获取资源根目录失败");
		}
		return rootRealPath;
	}
}
