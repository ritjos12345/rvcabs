package com.rvcabs.microservices.entity;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicationuser")
public class ApplicationUserEntity extends BaseEntity<String>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "accountId", nullable = false, length = 100)
	private String accountId;
	
	
	@Column(name = "userName", nullable = false, length = 50)
	private String userName;

	
	@Column(name = "emailId", nullable = true, length = 50)
	private String emailId;

	
	@Column(name = "mobileNumber", nullable = false, length = 50)
	private String mobileNumber;
	
	@Column(name = "secMobileNumber")
	private String secMobileNumber;
	
	@Column(name = "password", nullable = false, length = 500)
	private String password;
	
	
	@Column(name = "firstName", nullable = true, length = 20)
	private String firstName;
	
	@Column(name = "lastName", nullable = true, length = 20)
	private String lastName;
	
	
	@Column(name = "countryCode", nullable = true, length = 3)
	private String countryCode;
	
	
	@Column(name = "gender", nullable = true, length = 10)
	private String gender;
	
	
	@Column(name = "dateOfBirth", nullable = true)
	private Date dateOfBirth;
	
	/*
	 * @Column(name = "address", nullable = true, length = 50) private String
	 * address;
	 * 
	 * 
	 * @Column(name = "area", nullable = true, length = 50) private String area;
	 * 
	 * 
	 * @Column(name = "city", nullable = true, length = 50) private String city;
	 * 
	 * 
	 * @Column(name = "state", nullable = true, length = 50) private String state;
	 * 
	 * 
	 * @Column(name = "pincode", nullable = true, length = 15) private Long pincode;
	 */
	
	@Column(name="lastPasswordUpdated")
	private ZonedDateTime lastPasswordUpdated;
	
	@Column(name="lastLoggedIn")
	private ZonedDateTime lastLoggedIn;
	
	@Column
	private String customerId;

	@Column
	private String userType;
	
	@Lob
	@Column
	private String profilePicURL;
	
	@Column
	private String employID;
	
	@Column(columnDefinition = "tinyint(1) defult 1",nullable=false)
	private Boolean active;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getSecMobileNumber() {
		return secMobileNumber;
	}

	public void setSecMobileNumber(String secMobileNumber) {
		this.secMobileNumber = secMobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public ZonedDateTime getLastPasswordUpdated() {
		return lastPasswordUpdated;
	}

	public void setLastPasswordUpdated(ZonedDateTime lastPasswordUpdated) {
		this.lastPasswordUpdated = lastPasswordUpdated;
	}

	public ZonedDateTime getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(ZonedDateTime lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProfilePicURL() {
		return profilePicURL;
	}

	public void setProfilePicURL(String profilePicURL) {
		this.profilePicURL = profilePicURL;
	}

	public String getEmployID() {
		return employID;
	}

	public void setEmployID(String employID) {
		this.employID = employID;
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

	
}