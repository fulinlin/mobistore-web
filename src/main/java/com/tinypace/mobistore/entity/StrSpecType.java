package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author aaron
 * 产品的规格类型
 * 比如 颜色、尺寸
 */

@Entity
@Table(name="str_spec_type")
public class StrSpecType extends IdEntity {
	private static final long serialVersionUID = -5171526496939235078L;
	private String name;
	private String descr;

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
	
}
