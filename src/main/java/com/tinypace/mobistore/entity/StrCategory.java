package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="str_category")
public class StrCategory extends IdEntity {
	private static final long serialVersionUID = 5462783088930984388L;

	private String code;
	private String name;
	private String descr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
