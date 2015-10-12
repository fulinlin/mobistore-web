package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_suggestion")
public class StrSuggestion extends IdEntity {
	private static final long serialVersionUID = -940647278071105819L;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	private StrClient client;
	
	@Column(name="client_id")
	private String clientId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StrClient getClient() {
		return client;
	}

	public void setClient(StrClient client) {
		this.client = client;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
