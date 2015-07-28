package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 车牌信息
 * @author 徐祥
 */
@Entity
@Table(name="wo_car_no")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class License extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5128744877634675218L;
	
  	public static enum LICENSE_COLOR{
  		RED("RED"), YELLOW("YELLOW"), BLUE("BLUE"), BLACK("BLACK"), WHITE("WHITE");
  		
  		private LICENSE_COLOR(String code){
  			this.code = code;
  		}
  		private String code;
  		
  		public String Code(){
  			return code;
  		}
  		
  		public String toString() {
  			return code;
  		}
  		
  		 public static LICENSE_COLOR value(String value) {
  	        switch (value) {
  	        case "RED":
  	        	return RED;
  	        case "YELLOW":
  	            return YELLOW;
  	        case "BLUE":
  	        	return BLUE;
  	        case "BLACK":
  	            return BLACK;
  	        case "WHITE":
  	        	return WHITE;
  	        default:
  	            return null;
  	        }
  	    }
  	}

	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 车架号
	 */
	private String frameNumber;
	
	/**
	 * 品牌
	 */
	private String brand;
	
	/**
	 * 颜色
	 */
	@Enumerated(EnumType.STRING)
	private LICENSE_COLOR color;
	
	/**
	 * 是否绑定
	 */
	private Boolean isPostpaid=Boolean.FALSE;
	
	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Boolean getIsPostpaid() {
		return isPostpaid;
	}

	public void setIsPostpaid(Boolean isPostpaid) {
		this.isPostpaid = isPostpaid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public LICENSE_COLOR getColor() {
		return color;
	}
	public void setColor(LICENSE_COLOR color) {
		this.color = color;
	}
}
