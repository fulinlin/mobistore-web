package com.wolai.platform.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

public class BeanUtilEx extends BeanUtils {

	static {
		 ConvertUtils.register(new org.apache.commons.beanutils.converters.DateConverter(), java.util.Date.class);
		    ConvertUtils.register(new org.apache.commons.beanutils.converters.DateConverter(), java.sql.Date.class);
		    ConvertUtils.register(new org.apache.commons.beanutils.converters.DateConverter(), BigDecimal.class);
	}

	public static void copyProperties(Object target, Object source)
			throws InvocationTargetException, IllegalAccessException {
		// 支持对日期copy
		org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

	}
	
	public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
		D1 d1 = new D1();
		D2 d2 = new D2();
		
		System.out.println(d2.getDate());
		BeanUtilEx.copyProperties(d2, d1);
		System.out.println(d2.getDate());
		
	}
	
}
