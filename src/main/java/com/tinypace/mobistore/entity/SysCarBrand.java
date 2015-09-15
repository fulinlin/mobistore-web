package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sys_car_brand")
public class SysCarBrand extends IdEntity {
	private static final long serialVersionUID = 3513674139851385415L;
	
	private String code;
    private String name;
    private String make;
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
}