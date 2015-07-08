package com.wolai.platform.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.wolai.platform.util.IdGen;


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
	protected String id;
	
	/**
	 * 删除标记位
	 */
	protected Boolean isDelete=Boolean.FALSE;
	
	@PrePersist
	public void prePersist(){
		this.id = IdGen.uuid();
	}

	@Id
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
