package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class CompanyDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3702271805726118205L;
	
	private String id;

	
	private String companyName;

	
	private String gstnumber;
	
		
	private String address;
		
	
	private String city;
		
	
	private String state;
	
	
	private String pincode;
	
		
	private Boolean active=true;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGstnumber() {
		return gstnumber;
	}

	public void setGstnumber(String gstnumber) {
		this.gstnumber = gstnumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<ContactPersonDto> contactPersonDtoList;

	public List<ContactPersonDto> getContactPersonDtoList() {
		return contactPersonDtoList;
	}

	public void setContactPersonDtoList(List<ContactPersonDto> contactPersonDtoList) {
		this.contactPersonDtoList = contactPersonDtoList;
	}
	

}
