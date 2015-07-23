package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 扣费信息
 * @author sevenshi
 */
@Entity
@Table(name="wo_deduct_history")
public class DeductHistory extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422799884916485425L;

	
    /**
     * 扣费总额
     */
	private long total;
	
    /**
     * 扣费时间
     */
    private Date deductDate;
	
    /**
     * 企业Id
     */
    @Column(name = "enterprise_id")
	private String  enterpriseId;
	
	/**
     * 企业
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", insertable = false, updatable = false)
    private Enterprise  enterprise;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Date getDeductDate() {
        return deductDate;
    }

    public void setDeductDate(Date deductDate) {
        this.deductDate = deductDate;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
