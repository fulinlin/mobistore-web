package com.tinypace.mobistore.vo;

import java.util.Date;

import javax.persistence.Column;

import com.tinypace.mobistore.entity.SysMessage.MsgType;

public class ModelVo {
	private String id;
	private String code;
    private String name;
    private String make;
    
	private String brandId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
