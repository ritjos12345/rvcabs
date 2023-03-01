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

@ApiModel(description = "Base Request")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8829404761685916276L;

	@NotNull(message = "Created By is mandatory")
	@Size(min = 5, max = 100, message = "Logged In User Id should have atleast 25 characters")
	@Pattern(regexp = "[a-zA-Z0-9\\s_.\\/#&-]*$", message = "Invalid Created By")
	@ApiModelProperty(notes = "Account Identifier", required = true, allowEmptyValue = false, example = "04da390e-7f95-4b73-a541-4d3c7dd0c891")
	private String createdBy="Admin";

}
