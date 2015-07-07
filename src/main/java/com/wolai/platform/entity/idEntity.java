package com.wolai.platform.entity;

import java.io.Serializable;

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
	
	@PrePersist
	public void prePersist(){
		this.id = IdGen.uuid();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
