package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private StrClient client;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingcart")
	@Where(clause = "isDelete = false and isDisable = false")
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


}
