package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sys_car_model")
public class SysCarModel extends IdEntity {
	private static final long serialVersionUID = 2730992929545975344L;
	
	private String code;
    private String name;
    private String make;
    
	@Column(name="brand_id")
	private String brandId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
	private SysCarBrand brand;

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

	public SysCarBrand getBrand() {
		return brand;
	}

	public void setBrand(SysCarBrand brand) {
		this.brand = brand;
	}

 
}