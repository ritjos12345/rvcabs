package com.rvcabs.microservices.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Oxygen-global partner user can register the application by providing few deaails like application name,description and processor information.")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDetailsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 168446915310106503L;


	@NotNull(message="Application Name is mandatory")
	@Size(min = 5, max = 50,message="Application Name should have atleast 5 and Maximum of 50 characters")
	@Pattern(regexp = "[a-zA-Z\\s]{5,50}$",message = "applicationName is invalid")
	@ApiModelProperty(notes = "Name of the application", required = true, allowEmptyValue = false, example = "Booster Program")
	private String applicationName;

	@NotNull(message="application Description is mandatory")
	@Size(min = 10, max = 500 ,message="Application description should have atleast 10 characters")
	@Pattern(regexp = "[a-zA-Z0-9\\s_.\\/#&-]*$",message = "application Description is invalid")
	@ApiModelProperty(notes = "Description of the application", required = true, allowEmptyValue = false, example = "Using Booster Program user can transfer pension")
	private String applicationDescription;

	@NotNull(message="account Id is mandatory")
	@Size(min = 25, max = 100 ,message="Account Id should have atleast 25 characters")
	@Pattern(regexp = "[a-zA-Z0-9\\s_.\\/#&-]*$",message = "Account Id is invalid")
	@ApiModelProperty(notes = "Application User Identifier", required = true, allowEmptyValue = false, example = "0c2ee26a-b41b-4049-9f8d-7a7ac25dfa15")
	private String accountId;
	
	@NotNull(message="Payment Processor mandatory")
	@Size(min = 3, max = 15,message="Payment Process should have atleast 3 and Maximum of 15 characters")
	@Pattern(regexp = "[a-zA-Z]{3,15}$",message = "Payment Processor should be FIS/ICHOOSE")
	@ApiModelProperty(notes = "Name of Paymet Processor", required = true, allowEmptyValue = false, example = "FIS|ICHOOSE")
	private String paymentProcessor;

}
