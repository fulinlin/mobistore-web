package com.wolai.platform.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


/**
 * 基础实体
 * 
 * @author xuxiang
 * @version $Id$
 * @since 	1.0
 */
@MappedSuperclass
public class idEntity implements Serializable {

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
	
/*	@PrePersist
	public void prePersist(){
		this.id = IdGen.uuid();
	}*/

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
}
