package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="str_series")
public class StrSeries extends IdEntity {
	private static final long serialVersionUID = 5462783088930984388L;

	private String name;
	private String descr;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
	private StrBrand brand;
	
	@Column(name="brand_id")
	private String brandId;

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

	public StrBrand getBrand() {
		return brand;
	}

	public void setBrand(StrBrand brand) {
		this.brand = brand;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
