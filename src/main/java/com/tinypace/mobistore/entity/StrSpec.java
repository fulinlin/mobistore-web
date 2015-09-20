package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
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
	
	public static enum SpecType{
		COLOR("颜色"), 
		SIZE("尺寸");
		
		private SpecType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String value(){
  			return textVal;
  		}
  		
  		public String toString(){
  			return textVal;
  		}
	}
	
	private SpecType type;
	private String name;
	private String descr;
	
	public SpecType getType() {
		return type;
	}
	public void setType(SpecType type) {
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
