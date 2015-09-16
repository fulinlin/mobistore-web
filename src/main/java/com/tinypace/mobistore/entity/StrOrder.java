package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "str_order")
public class StrOrder extends IdEntity {
	private static final long serialVersionUID = -1808821187990463300L;
	
	private String currency;
	private Integer payChannel;
	private BigDecimal amount;
	private BigDecimal freight;
	private BigDecimal payAmount;
	private PayStatus payStatus;
	
	private String shipAddress;
    private ShipStatus shipStatus;
    
    private Date createTime;
    private Date payTime;
    private Date shipTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	private StrClient client;
	
	@Column(name="client_id")
	private String clientId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "strOrder")
	@Where(clause = "isDelete = false and isDisable = false")
	private Set<StrOrderItem> itemSet = new HashSet<StrOrderItem>(0);
	
	// 支付状态
	public static enum PayStatus{
		FEATURE("FEATURE"), INIT("INIT"),IN_PROGRESS("IN_PROGRESS"),SUCCESSED("SUCCESS");
		
		private PayStatus(String textVal){
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
	
	// 送货状态
	public static enum ShipStatus{
		FEATURE("BACK"), INIT("INIT"),IN_PROGRESS("IN_PROGRESS"),SUCCESSED("SUCCESS");
		
		private ShipStatus(String textVal){
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public ShipStatus getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(ShipStatus shipStatus) {
		this.shipStatus = shipStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getShipTime() {
		return shipTime;
	}

	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}

	public StrClient getClient() {
		return client;
	}

	public void setClient(StrClient client) {
		this.client = client;
	}

	public Set<StrOrderItem> getItemSet() {
		return itemSet;
	}

	public void setItemSet(Set<StrOrderItem> itemSet) {
		this.itemSet = itemSet;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	


    

}
