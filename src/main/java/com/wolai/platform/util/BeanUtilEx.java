package com.wolai.platform.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;

public class BeanUtilEx extends BeanUtils {

	static {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
	}

	public static void copyProperties(Object target, Object source) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		D1 d1 = new D1();
//		D2 d2 = new D2();
//
//		d1.setDate(new Date());
//
//		System.out.println(d2.getDate());
//		BeanUtilEx.copyProperties(d2, d1);
//		System.out.println(d2.getDate());
		
		String regex = ".*[A-Za-z0-9]{5}$";
		System.out.println("粤SD0&00".matches(regex));
	}
}
