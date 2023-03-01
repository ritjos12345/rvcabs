package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationDetailsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5560288768986203114L;
	
	private String applicationId;

	private String accountId;

	private String applicationName;

	private String description;
	
	private String paymentProcessor;
	
	private ZonedDateTime applicationCretedOn;
	
	private String clientId;
	
	private String clientSecret;
	
	private String publickey;

	private String privatekey;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentProcessor() {
		return paymentProcessor;
	}

	public void setPaymentProcessor(String paymentProcessor) {
		this.paymentProcessor = paymentProcessor;
	}

	public ZonedDateTime getApplicationCretedOn() {
		return applicationCretedOn;
	}

	public void setApplicationCretedOn(ZonedDateTime applicationCretedOn) {
		this.applicationCretedOn = applicationCretedOn;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
