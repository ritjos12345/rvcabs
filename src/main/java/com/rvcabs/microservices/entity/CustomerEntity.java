package com.rvcabs.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -978387498307375788L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "customerId", nullable = false, length = 100)
	private String id;

	@NotNull
	@Column(name = "customerName", nullable = false, length = 100)
	private String name;

	@NotNull
	@Column(name = "emailId", nullable = false, length = 50)
	private String emailId;

	@NotNull
	@Column(name = "mobileNumber", nullable = false, length = 15)
	private String mobileNumber;

	@Column(name = "gstNum")
	private String gstNum;

	@NotNull
	@Column(name = "countryCode", nullable = false, length = 3)
	private String countryCode;

	@NotNull
	@Column(name = "address", nullable = false, length = 50)
	private String address;

	@NotNull
	@Column(name = "area", nullable = false, length = 50)
	private String area;

	@NotNull
	@Column(name = "city", nullable = false, length = 50)
	private String city;

	@NotNull
	@Column(name = "state", nullable = false, length = 50)
	private String state;

	@NotNull
	@Column(name = "pincode", nullable = false, length = 15)
	private Long pincode;

	@Column(columnDefinition = "tinyint(1) default 0")
	private boolean active;

	/*
	 * @NotNull
	 * 
	 * @Column(name = "clientId", nullable = false, length = 150) private String
	 * clientId;
	 * 
	 * @NotNull
	 * 
	 * @Column(name = "clientSecret", nullable = false, length = 150) private String
	 * clientSecret;
	 */
	/*
	 * @NotNull
	 * 
	 * @Column(name = "publickey",length = 5000) private String publickey;
	 * 
	 * @NotNull
	 * 
	 * @Column(name = "privatekey",length = 5000) private String privatekey;
	 */
	@Lob
	@Column(name = "customerrLogo", columnDefinition = "MEDIUMBLOB", length = 400000)
	private byte[] customerLogo;

	@Column(name = "contactPerson")
	private String contactPerson;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGstNum() {
		return gstNum;
	}

	public void setGstNum(String gstNum) {
		this.gstNum = gstNum;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public byte[] getCustomerLogo() {
		return customerLogo;
	}

	public void setCustomerLogo(byte[] customerLogo) {
		this.customerLogo = customerLogo;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * @Column(name = "extension",length = 10) private String extension;
	 */

}