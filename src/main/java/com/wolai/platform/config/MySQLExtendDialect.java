package com.wolai.platform.config;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

public class MySQLExtendDialect extends MySQLDialect {
	public MySQLExtendDialect() {
		super();
		registerFunction("convert_gbk", new SQLFunctionTemplate(
				new StringType(), "convert(?1 using 'gbk')"));
	}

}
