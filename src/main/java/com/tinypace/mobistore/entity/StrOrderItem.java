package com.tinypace.mobistore.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_order_item")
public class StrOrderItem extends IdEntity {
	private static final long serialVersionUID = -2853759481621344176L;
	private BigDecimal unitPrice;
	private Integer qty;
	private BigDecimal amount;
	private String name;
	private String image;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private StrProduct product;
	
	@Column(name="product_id")
	private String productId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private StrOrder strOrder;
	
	@Column(name="order_id")
	private String orderId;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
