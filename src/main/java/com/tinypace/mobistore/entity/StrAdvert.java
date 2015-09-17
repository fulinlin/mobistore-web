package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="str_advert")
public class StrAdvert extends IdEntity {
	private static final long serialVersionUID = 8901867019267724725L;
	private String title;
	private String descr;
	private String image;
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
