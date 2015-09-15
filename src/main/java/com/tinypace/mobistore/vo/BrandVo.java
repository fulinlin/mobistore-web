package com.tinypace.mobistore.vo;

import java.util.Date;

import com.tinypace.mobistore.entity.SysMessage.MsgType;

public class BrandVo {
	private String id;
	private String code;
    private String name;
    private String make;
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

}
