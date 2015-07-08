package com.wolai.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 积分（喔币）
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_integral")
public class Integral extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7026662642222628687L;

	/**
	 * 积分总额
	 */
	private BigDecimal balance;
	
	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
}
