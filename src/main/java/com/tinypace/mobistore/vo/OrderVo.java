package com.tinypace.mobistore.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tinypace.mobistore.entity.StrOrder.PayStatus;
import com.tinypace.mobistore.entity.StrOrder.ShipStatus;

public class OrderVo {
	private String id;
	private String currency;
	private Integer payChannel;
	private BigDecimal amount;
	private BigDecimal freight;
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private PayStatus payStatus = PayStatus.INIT;
	
	private String recipientArea;
	private String recipientStreet;
	private String recipientAddress;
	private String recipientName;
	private String recipientPhone;
    private ShipStatus shipStatus = ShipStatus.INIT;
    
    private Date createTime;
    private Date payTime;
    private Date shipTime;
    
    private Set<OrderItemVo> items = new HashSet<OrderItemVo>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public Set<OrderItemVo> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemVo> items) {
		this.items = items;
	}
    
}
