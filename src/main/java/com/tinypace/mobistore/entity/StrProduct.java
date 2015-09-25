package com.tinypace.mobistore.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="str_product")
public class StrProduct extends IdEntity {
	private static final long serialVersionUID = 5462783088930984388L;

	private String name;
	private String descr;
	private String image;

	private BigDecimal freight;
	private BigDecimal freightFreeIfTotalAmount;
	private Integer orderPeriod;
	private Integer qty;
	private Date startTime;
	private Date endTime;

	private Integer mass;
	private Integer capacity;
	private String color;
	private String size;
	
	private String batchNumb;
	private Date productionDate;
	private Integer shelfLife;
	
	private Boolean recommend;
	private Boolean hot;
	private Boolean promotion;
	
	private Integer collect = 0;
	
	private BigDecimal retailPrice;
	private BigDecimal discountPrice;
	
	private String tags;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	private StrCategory category;
	
	@Column(name="category_id")
	private String categoryId;
	
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


	public Boolean getRecommend() {
		return recommend;
	}


	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}


	public Boolean getHot() {
		return hot;
	}


	public void setHot(Boolean hot) {
		this.hot = hot;
	}


	public Boolean getPromotion() {
		return promotion;
	}


	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getFreightFreeIfTotalAmount() {
		return freightFreeIfTotalAmount;
	}

	public void setFreightFreeIfTotalAmount(BigDecimal freightFreeIfTotalAmount) {
		this.freightFreeIfTotalAmount = freightFreeIfTotalAmount;
	}

	public Integer getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(Integer orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getMass() {
		return mass;
	}

	public void setMass(Integer mass) {
		this.mass = mass;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getBatchNumb() {
		return batchNumb;
	}

	public void setBatchNumb(String batchNumb) {
		this.batchNumb = batchNumb;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Integer getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(Integer shelfLife) {
		this.shelfLife = shelfLife;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public StrCategory getCategory() {
		return category;
	}

	public void setCategory(StrCategory category) {
		this.category = category;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCollect() {
		return collect;
	}

	public void setCollect(Integer collect) {
		this.collect = collect;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}


}
