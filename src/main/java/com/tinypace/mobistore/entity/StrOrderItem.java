package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_order_item")
public class StrOrderItem extends IdEntity {
	private static final long serialVersionUID = -2853759481621344176L;
	private BigDecimal unitPrice;
	private Integer qty;
	private BigDecimal freight;
	private BigDecimal amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private StrProduct product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private StrOrder strOrder;


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public StrProduct getProduct() {
		return product;
	}

	public void setProduct(StrProduct product) {
		this.product = product;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public StrOrder getStrOrder() {
		return strOrder;
	}

	public void setStrOrder(StrOrder strOrder) {
		this.strOrder = strOrder;
	}

}
