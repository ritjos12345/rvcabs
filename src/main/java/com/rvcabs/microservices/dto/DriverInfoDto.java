package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Named
@Configurable(autowire = Autowire.BY_TYPE)
public class DriverInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
		
	private String accountId;
		
	private String mobileNumber;
		
	private CarDetailDto carDetailDto;
	
	private List<DocumentDetailDto> documentDetailDtos;

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public CarDetailDto getCarDetailDto() {
		return carDetailDto;
	}

	public void setCarDetailDto(CarDetailDto carDetailDto) {
		this.carDetailDto = carDetailDto;
	}

	public List<DocumentDetailDto> getDocumentDetailDtos() {
		return documentDetailDtos;
	}

	public void setDocumentDetailDtos(List<DocumentDetailDto> documentDetailDtos) {
		this.documentDetailDtos = documentDetailDtos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	/*@Inject
	private AddressDetailsDto addressDetailsDto;
*/

	


	
	



	
}
