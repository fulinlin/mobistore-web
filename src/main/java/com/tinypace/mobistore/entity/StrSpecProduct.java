package com.tinypace.mobistore.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author aaron
 * 具体到某个产品，说明有哪些 规格
 * 比如 衬衫A-白、衬衫A-黑、衬衫A-大号
 */

@Entity
@Table(name="str_spec_product")
public class StrSpecProduct extends IdEntity {
	private static final long serialVersionUID = 7121561095280974881L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private StrProduct product;
	
	@Column(name="product_id")
	private String productId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spec_id", insertable = false, updatable = false)
	private StrSpec spec;
	
	@Column(name="spec_id")
	private String specId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id", insertable = false, updatable = false)
	private StrSpecType type;
	
	@Column(name="type_id")
	private String typeId;

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

	public StrSpec getSpec() {
		return spec;
	}

	public void setSpec(StrSpec spec) {
		this.spec = spec;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}
	
}