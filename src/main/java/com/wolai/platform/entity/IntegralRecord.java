package com.wolai.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * 积分变动记录
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_integral_record")
public class IntegralRecord extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 307315644736388795L;
	
	public static enum IntegralChangeType{
  		/**
  		 * 停车获得
  		 */
  		PARKING("PARKING"),
  		
  		/**
  		 * 市场活动赠送
  		 */
  		MARKING("MARKING"),
  		
  		/**
  		 * 兑换优惠券
  		 */
  		CONSUM("CONSUM");
  		
  		
  		private IntegralChangeType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
	
	/**
	 * 积分变动类型
	 */
	@Enumerated(EnumType.STRING)
	private IntegralChangeType type;
	
	/**
	 * 变动数量
	 */
	private BigDecimal number;
	
	/**
	 * 关联记录
	 */
	@Column(name="relation_id")
	private String relationId;
	
	@Any(metaColumn=@Column(name="type"))
    @AnyMetaDef(idType="string", metaType="string",
        metaValues={
         @MetaValue(targetEntity=ParkingRecord.class, value="parking"),
         @MetaValue(targetEntity=Coupon.class, value="consum"),
    })
    @JoinColumn(name="relation_id",insertable=false,updatable=false)
	private Object relation;

	public IntegralChangeType getType() {
		return type;
	}

	public void setType(IntegralChangeType type) {
		this.type = type;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Object getRelation() {
		return relation;
	}

	public void setRelation(Object relation) {
		this.relation = relation;
	}
	
}
