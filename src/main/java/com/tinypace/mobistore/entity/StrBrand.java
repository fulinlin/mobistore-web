package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="str_brand")
public class StrBrand extends IdEntity {
	private static final long serialVersionUID = 5462783088930984388L;

	private String name;
	private String descr;
	
}
