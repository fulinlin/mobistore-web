package com.tinypace.mobistore.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="str_brand")
public class StrBrand extends IdEntity {
	private static final long serialVersionUID = 5462783088930984388L;

	private String name;
	private String descr;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	@Where(clause = "isDelete = false and isDisable = false")
	private Set<StrSeries> seriesSet = new HashSet<StrSeries>(0);

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

	public Set<StrSeries> getSeriesSet() {
		return seriesSet;
	}

	public void setSeriesSet(Set<StrSeries> seriesSet) {
		this.seriesSet = seriesSet;
	}
	
	
}
