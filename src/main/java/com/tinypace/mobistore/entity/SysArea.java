package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysArea extends IdEntity {
	private static final long serialVersionUID = -2905495436282293708L;
	
	private Integer parentid;
	private String areaname;
	private String shortname;
	private String lng;
	private String lat;
	private Integer level;
    private String position;
    private Integer sort;
    
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLevelname() {
		String name = "";
		if (this.getLevel() == 1) {
			name = "provice";
		} else if (this.getLevel() == 2) {
			name = "city";
		} else if (this.getLevel() == 3) {
			name = "region";
		}
		return name;
	}
}
