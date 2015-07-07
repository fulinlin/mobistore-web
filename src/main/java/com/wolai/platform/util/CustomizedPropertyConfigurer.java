package com.wolai.platform.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
/**
 * 
 * 
 * 系统上下文属性文件的操作
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

    /**
     * 存放上下文属性的map
     */
    private static Map<String, Object> CTXPROPERTIESMAP;
    
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
        //load properties to ctxPropertiesMap  
        CTXPROPERTIESMAP = new HashMap<String, Object>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            CTXPROPERTIESMAP.put(keyStr, value);  
        }  
    }  
  
    /**
     * 
     * 获得属性值
     * @param name 属性名称
     * @return 返回对象
     */
    //static method for accessing context properties  
    public static Object getContextProperty(String name) {  
        return CTXPROPERTIESMAP.get(name);  
    }  
}
