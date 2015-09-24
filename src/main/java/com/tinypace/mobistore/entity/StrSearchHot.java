package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "str_search_hot")
public class StrSearchHot extends IdEntity {
	private static final long serialVersionUID = 2447238103292248784L;
	private Integer times;
    private String keywords;
    
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
}
