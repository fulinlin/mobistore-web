package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "str_config")
public class SysConfig extends IdEntity {
	private static final long serialVersionUID = -5530378693805363888L;
	
	private String androidMkt;
	private String iosMkt;
	
	public String getAndroidMkt() {
		return androidMkt;
	}
	public void setAndroidMkt(String androidMkt) {
		this.androidMkt = androidMkt;
	}
	public String getIosMkt() {
		return iosMkt;
	}
	public void setIosMkt(String iosMkt) {
		this.iosMkt = iosMkt;
	}

}
