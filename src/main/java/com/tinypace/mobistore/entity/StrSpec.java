package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author aaron
 * 产品的规格，规格会影响价格，不同于属性
 * 比如 颜色-红色、尺寸-大号
 */

@Entity
@Table(name="str_spec")
public class StrSpec extends IdEntity {
	private static final long serialVersionUID = 1464076849604026152L;
	
	private String name;
	private String descr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id", insertable = false, updatable = false)
	private StrSpecType type;
	
	@Column(name="type_id")
	private String typeId;
	
	public StrSpecType getType() {
		return type;
	}
	public void setType(StrSpecType type) {
		this.type = type;
	}
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
