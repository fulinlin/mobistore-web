package com.tinypace.mobistore.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ShoppingcartVo {
	private String id;

    private Date createTime;
    private BigDecimal amount;
	
	private Set<ShoppingcartItemVo> items = new HashSet<ShoppingcartItemVo>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Set<ShoppingcartItemVo> getItems() {
		return items;
	}

	public void setItems(Set<ShoppingcartItemVo> items) {
		this.items = items;
	}
	
}
