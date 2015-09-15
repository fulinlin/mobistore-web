package com.tinypace.mobistore.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;


/**
 * 基础实体
 * 
 * @author xuxiang
 * @version $Id$
 * @since 	1.0
 */
@MappedSuperclass
public class IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1831643589158410558L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")  
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	protected String id;
	
	/**
	 * 删除标记位
	 */
	protected Boolean isDelete=Boolean.FALSE;
	
	/**
	 * 禁用标记位
	 */
	protected Boolean isDisable=Boolean.FALSE;
	

	
	public String getId() {
		if(StringUtils.isBlank(id)){
			return null;
		}
		return id;
	}

	public void setId(String id) {
		if(StringUtils.isBlank(id)){
			return;
		}
		this.id = id;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}
}
