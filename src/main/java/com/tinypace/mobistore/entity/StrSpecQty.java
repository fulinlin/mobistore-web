package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author aaron
 * 定义某个产品规格的库存
 * 比如 衬衫A-白-大号：库存100件
 */

@Entity
@Table(name="str_spec_qty")
public class StrSpecQty extends IdEntity {
	private static final long serialVersionUID = -6636436838851217253L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private StrProduct product;
	
	@Column(name="product_id")
	private String productId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_id", insertable = false, updatable = false)
	private StrSpec color;
	
	@Column(name="color_id")
	private String colorId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "size_id", insertable = false, updatable = false)
	private StrSpec size;
	
	@Column(name="size_id")
	private String sizeId;
	
	private Integer qty;
	
	public StrProduct getProduct() {
		return product;
	}
	public void setProduct(StrProduct product) {
		this.product = product;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public StrSpec getColor() {
		return color;
	}
	public void setColor(StrSpec color) {
		this.color = color;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public StrSpec getSize() {
		return size;
	}
	public void setSize(StrSpec size) {
		this.size = size;
	}
	public String getSizeId() {
		return sizeId;
	}
	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
}