package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_shoppingcart_item")
public class StrShoppingcartItem extends IdEntity {
	private static final long serialVersionUID = 478005474396003310L;
	private BigDecimal unitPrice;
	private Integer qty;
	private BigDecimal freight;
	private BigDecimal amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private StrProduct product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private StrShoppingcart shoppingcart;


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

	public StrShoppingcart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(StrShoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

}
