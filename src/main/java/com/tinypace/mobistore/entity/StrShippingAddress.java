package com.tinypace.mobistore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="str_shipping_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrShippingAddress extends IdEntity {
	
	private String province;
	private String city;
    private String address;
    private String postcode;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	private StrClient client;
	
	@Column(name="client_id")
	private String clientId;
    
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
