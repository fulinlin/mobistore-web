package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "str_order")
public class StrOrder extends IdEntity {
	private static final long serialVersionUID = -1808821187990463300L;
	
	private String currency;
	private Integer payChannel;
	private BigDecimal amount;
	private BigDecimal freight;
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	
	private String recipientId;
	private String recipientArea;
	private String recipientStreet;
	private String recipientAddress;
	private String recipientName;
	private String recipientPhone;
     
	@Enumerated(EnumType.ORDINAL)
    private Status status = Status.INIT;
    
    private Date createTime;
    private Date payTime;
    private Date shipTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	private StrClient client;
	
	@Column(name="client_id")
	private String clientId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "strOrder")
	@Where(clause = "is_delete = 0 and is_disable = 0")
	private Set<StrOrderItem> itemSet = new HashSet<StrOrderItem>(0);
	
	// 流程状态
	public static enum Status{
		INIT(0), PAYING(1), PAID(2), 
		SHIPPING(3), RECEIVED(4), RATED(5),
		CANCEL(6),
		PAY_FEATURE(7), SHIPPING_FEATURE(8);
		
		private Status(Integer val){
  			this.val=val;
  		}
  		private Integer val;
  		
  		public Integer value(){
  			return val;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}



	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientPhone() {
		return recipientPhone;
	}

	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}

	public String getRecipientArea() {
		return recipientArea;
	}

	public void setRecipientArea(String recipientArea) {
		this.recipientArea = recipientArea;
	}

	public String getRecipientStreet() {
		return recipientStreet;
	}

	public void setRecipientStreet(String recipientStreet) {
		this.recipientStreet = recipientStreet;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

}
