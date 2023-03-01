package com.rvcabs.microservices.dto;

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

@ApiModel
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8829404761685916276L;

	
	@NotNull(message = "password is mandatory")
	@Size(min = 6, max = 15, message = "Password should have atleast 6 and Maximum of 15 characters")
	@Pattern(regexp = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%^&*_=+-/]){7,15}", message = "password is invalid")
	@ApiModelProperty(notes = "Password of the Appliation User ", required = true, allowEmptyValue = false, example = "Abc@12def")
	private String newPassword;

	@NotNull(message = "Confirm Password is mandatory")
	@Size(min = 6, max = 15, message = "Password should have atleast 6 and Maximum of 15 characters")
	@Pattern(regexp = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%^&*_=+-/]){7,15}", message = "confirm Password is invalid")
	@ApiModelProperty(notes = "Confirm Password of the Appliation User ", required = true, allowEmptyValue = false, example = "Abc@12def")
	private String confirmPassword;

	@NotNull(message = "account Id is mandatory")
	@ApiModelProperty(notes = "Account Identifier ", required = true, allowEmptyValue = false, example = "9kdkc-kkdk")
	private String accountId;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
