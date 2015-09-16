package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="str_series")
public class StrSeries extends IdEntity {
	private static final long serialVersionUID = 5462783088930984388L;

	private String name;
	private String descr;
	
}
