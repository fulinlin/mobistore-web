package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 停车场实体
 * @author xuxiang
 */
@Entity
@Table(name="wo_parking_lot")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParkingLot extends IdEntity {

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
	 * 图片
	 */
	@Lob
	private String image;
	
	/**
	 * 收费信息
	 */
	private String chargeInfo;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 地址
	 */
	private String city;
	
	/**
	 * 是否为喔来自营停车场
	 */
	private Boolean isProprietary=Boolean.TRUE;
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsProprietary() {
		return isProprietary;
	}

	public void setIsProprietary(Boolean isProprietary) {
		this.isProprietary = isProprietary;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getChargeInfo() {
		return chargeInfo;
	}

	public void setChargeInfo(String chargeInfo) {
		this.chargeInfo = chargeInfo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}