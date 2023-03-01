package com.rvcabs.microservices.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TravelerDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;

	private String lastName;

	private String employeeId;
	
	@NotNull(message = "Mobile Number is mandatory")
	@Size(min = 8, max = 15, message = "Mobile Number should have atleast 8 and Maximum of 15 digits")
	// @JsonProperty(value = "mobileNumber")
	@Pattern(regexp = "[0-9]+", message = "Mobile Number is invalid")
	@ApiModelProperty(notes = "Mobile Number Of the Appliation User ", required = true, allowEmptyValue = false, example = "8088661978")
	private String mobileNumber;
	
	private String secMobileNumber;

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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
