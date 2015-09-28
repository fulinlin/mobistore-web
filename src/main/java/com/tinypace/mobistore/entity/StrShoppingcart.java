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
@Table(name = "str_shoppingcart")
public class StrShoppingcart extends IdEntity {
	private static final long serialVersionUID = 1039531072687052351L;
    
    private Date createTime;
    private BigDecimal amount;
    private BigDecimal freight;
    private BigDecimal totalAmount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	private StrClient client;
	
	@Column(name="client_id")
	private String clientId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingcart")
	@Where(clause = "is_delete = 0 and is_disable = 0")
	private Set<StrShoppingcartItem> itemSet = new HashSet<StrShoppingcartItem>(0);

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public StrClient getClient() {
		return client;
	}

	public void setClient(StrClient client) {
		this.client = client;
	}

	public Set<StrShoppingcartItem> getItemSet() {
		return itemSet;
	}

	public void setItemSet(Set<StrShoppingcartItem> itemSet) {
		this.itemSet = itemSet;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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


}
