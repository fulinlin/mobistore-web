package com.wolai.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 停车场实体
 * @author Ethan
 */
@Entity
@Table(name="wo_coupon")
public class ParkingLot extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333854011753343965L;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 简介
	 */
	@Lob
	private String summary;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
