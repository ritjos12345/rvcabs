package com.rvcabs.microservices.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rvcabs.microservices.dto.CarDetailDto;
import com.rvcabs.microservices.dto.DocumentDetailDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Oxygen Global Partner user(s) has to sign-up by providing user's basic information.After sign-up partner user has sign-in and register the applications. ")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserRequest extends CreateBaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8829404761685916276L;

	
	private String accountId;
	
	@NotNull(message="Email Id is mandatory")
	@Size(min = 5, max = 50,message="Email Id should have atleast 5 and Maximum of 50 characters")
	//@JsonProperty(value = "emailId")
	@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}{5,50}$",message = "Email is invalid")
	@ApiModelProperty(notes = "Email Id of the Appliation User ", required = true, allowEmptyValue = false, example = "siva.gvs@gmail.com")
	private String emailId;

	@NotNull(message="Mobile Number is mandatory")
	@Size(min = 8, max = 15,message="Mobile Number should have atleast 8 and Maximum of 15 digits")
	//@JsonProperty(value = "mobileNumber")
	@Pattern(regexp = "[0-9]+",message = "Mobile Number is invalid")
	@ApiModelProperty(notes = "Mobile Number Of the Appliation User ", required = true, allowEmptyValue = false, example = "8088661978")
	private String mobileNumber;

	@NotNull(message="password is mandatory")
	@Size(min = 6, max = 15 ,message="Password should have atleast 6 and Maximum of 15 characters")
	//@JsonProperty(value = "password")
	@Pattern(regexp = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%^&*_=+-/]){7,15}",message = "password is invalid")
	@ApiModelProperty(notes = "Password of the Appliation User ", required = true, allowEmptyValue = false, example = "Abc@12def")
	private String password;
	

	@NotNull(message="First Name is mandatory")
	@Size(min = 1, max = 20,message="First Name should have atleast 1 and Maximum of 20 characters")
	//@JsonProperty(value = "firstName")
	@Pattern(regexp = "[a-zA-Z0-9\\s]{2,20}$",message = "firstName is invalid")
	@ApiModelProperty(notes = "First name of the Application User", required = true, allowEmptyValue = false, example = "Siva Prasad")
	private String firstName;
	
	@Size(min = 1, max = 20)
	//@JsonProperty(value = "lastName")
	@Pattern(regexp = "[a-zA-Z0-9\\s]+",message = "lastName is invalid")
	@ApiModelProperty(notes = "The Last name of the Application User", required = false, allowEmptyValue = true, example = "Siva Prasad")
	private String lastName;
	
	
	private String countryCode="IN";
	
	@NotNull(message="Gender is mandatory")
	@Size(min = 1, max = 10)
	@JsonProperty(value = "gender")
	@Pattern(regexp = "[a-zA-Z]{1,10}$",message = "gender is invalid")
	@ApiModelProperty(notes = "Gender Of the Appliation User", required = true, allowEmptyValue = false, example = "Male")
	private String gender;
	
	@NotNull(message="date of birth is mandatory")
	//@JsonProperty(value = "dateOfBirth")
	@ApiModelProperty(notes = "Partner User's date of birth", required = true, allowEmptyValue = false)
	private Date dateOfBirth;
	

	
	@ApiModelProperty(notes = "Customer Identifier ", required = false, allowEmptyValue = false, example = "9kdkc-kkdk")
	private String customerId;

	@NotNull
	@ApiModelProperty(notes = "User Type", required = false, allowEmptyValue = true, example = "CUSTOMER/ADMIN/DRIVER...etc")
	private String userType;

	@ApiModelProperty(notes = "Profile Picture", required = false, allowEmptyValue = true)
	private byte[] profilePicture;

	@ApiModelProperty(notes = "User Profile Picture Extension", required = false, allowEmptyValue = true)
	private String extension;
	
	private String employID;

	
	private String secMobileNumber;

	/*
	 * @ApiModelProperty(notes = "Branch Id", required = false, allowEmptyValue =
	 * true) private Long branchId;
	 * 
	 * 
	 * 
	 * 
	 * private CarDetailDto carDetailDto;
	 * 
	 * 
	 */
	@Autowired
	private List<DocumentDetailDto> documentDetailDtos;
	 
	@Autowired
	private CarDetailDto carDetailDto;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEmployID() {
		return employID;
	}

	public void setEmployID(String employID) {
		this.employID = employID;
	}

	public String getSecMobileNumber() {
		return secMobileNumber;
	}

	public void setSecMobileNumber(String secMobileNumber) {
		this.secMobileNumber = secMobileNumber;
	}

	public List<DocumentDetailDto> getDocumentDetailDtos() {
		return documentDetailDtos;
	}

	public void setDocumentDetailDtos(List<DocumentDetailDto> documentDetailDtos) {
		this.documentDetailDtos = documentDetailDtos;
	}

	public CarDetailDto getCarDetailDto() {
		return carDetailDto;
	}

	public void setCarDetailDto(CarDetailDto carDetailDto) {
		this.carDetailDto = carDetailDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}