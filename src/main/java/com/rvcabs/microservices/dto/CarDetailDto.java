package com.rvcabs.microservices.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String accountId;

	private MasterDataDto type;

	private MasterDataDto subType;
	
	private MasterDataDto carColor;
	
	private MasterDataDto carInterrior;

	private MasterDataDto carCategory;
	
	private String carImage;
	
	private String regNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public MasterDataDto getType() {
		return type;
	}

	public void setType(MasterDataDto type) {
		this.type = type;
	}

	public MasterDataDto getSubType() {
		return subType;
	}

	public void setSubType(MasterDataDto subType) {
		this.subType = subType;
	}

	public MasterDataDto getCarColor() {
		return carColor;
	}

	public void setCarColor(MasterDataDto carColor) {
		this.carColor = carColor;
	}

	public MasterDataDto getCarInterrior() {
		return carInterrior;
	}

	public void setCarInterrior(MasterDataDto carInterrior) {
		this.carInterrior = carInterrior;
	}

	public MasterDataDto getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(MasterDataDto carCategory) {
		this.carCategory = carCategory;
	}

	public String getCarImage() {
		return carImage;
	}

	public void setCarImage(String carImage) {
		this.carImage = carImage;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
