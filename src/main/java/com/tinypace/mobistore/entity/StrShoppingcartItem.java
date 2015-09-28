package com.tinypace.mobistore.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_shoppingcart_item")
public class StrShoppingcartItem extends IdEntity {
	private static final long serialVersionUID = 478005474396003310L;
	private BigDecimal unitPrice;
	private Integer qty = 0;
	private BigDecimal amount;
	private BigDecimal freight;
	private BigDecimal freightFreeIfTotalAmount;
	private String name;
	private String image;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private StrProduct product;
	
	@Column(name="product_id")
	private String productId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shoppingcart_id", insertable = false, updatable = false)
	private StrShoppingcart shoppingcart;

	@Column(name="shoppingcart_id")
	private String shoppingcartId;

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

	public StrShoppingcart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(StrShoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getShoppingcartId() {
		return shoppingcartId;
	}

	public void setShoppingcartId(String shoppingcartId) {
		this.shoppingcartId = shoppingcartId;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getFreightFreeIfTotalAmount() {
		return freightFreeIfTotalAmount;
	}

	public void setFreightFreeIfTotalAmount(BigDecimal freightFreeIfTotalAmount) {
		this.freightFreeIfTotalAmount = freightFreeIfTotalAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
