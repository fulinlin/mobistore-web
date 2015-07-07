package com.wolai.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 结算信息
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_settlement")
public class Settlement extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1409610006846500734L;

	/**
	 * 创建时间
	 */
	private Date createTime=new Date();
	
	/**
	 * 结算金额
	 */
	private BigDecimal Money;
	
	/**
	 * 是否成功付款
	 */
	private  Boolean isPaid;
	
	/**
	 *
	 */
	private Date paidTime;
}
