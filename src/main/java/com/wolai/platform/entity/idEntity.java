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

	protected String id;		// 编号
	
	@PrePersist
	public void prePersist(){
		this.id = IdGen.uuid();
	}
}
