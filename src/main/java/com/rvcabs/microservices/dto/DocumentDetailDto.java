package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentDetailDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String id;

	private String accountId;
	
	private MasterDataDto documentType;
	
	private String frontImagePath;

	private String backImagePath;
	
	private String fieldDetails;
	
	private Map<String,String> documentField;

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

	public MasterDataDto getDocumentType() {
		return documentType;
	}

	public void setDocumentType(MasterDataDto documentType) {
		this.documentType = documentType;
	}

	public String getFrontImagePath() {
		return frontImagePath;
	}

	public void setFrontImagePath(String frontImagePath) {
		this.frontImagePath = frontImagePath;
	}

	public String getBackImagePath() {
		return backImagePath;
	}

	public void setBackImagePath(String backImagePath) {
		this.backImagePath = backImagePath;
	}

	public String getFieldDetails() {
		return fieldDetails;
	}

	public void setFieldDetails(String fieldDetails) {
		this.fieldDetails = fieldDetails;
	}

	public Map<String, String> getDocumentField() {
		return documentField;
	}

	public void setDocumentField(Map<String, String> documentField) {
		this.documentField = documentField;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/*
	 * private String documentNumber;
	 * 
	 * private String field1;
	 * 
	 * private String field2;
	 * 
	 * private String field3;
	 * 
	 * private String field4;
	 * 
	 * private String field5;
	 * 
	 * private String field6;
	 * 
	 * private String field7;
	 * 
	 * 
	 * public Integer getId() { return id; }
	 * 
	 * 
	 * public Long getDriverId() { return driverId; }
	 * 
	 * 
	 * public Integer getDocumentType() { return documentType; }
	 * 
	 * 
	 * public void setId(Integer id) { this.id = id; }
	 * 
	 * 
	 * public void setDriverId(Long driverId) { this.driverId = driverId; }
	 * 
	 * 
	 * public void setDocumentType(Integer documentType) { this.documentType =
	 * documentType; }
	 * 
	 * 
	 * public String getDocumentNumber() { return documentNumber; }
	 * 
	 * 
	 * public void setDocumentNumber(String documentNumber) { this.documentNumber =
	 * documentNumber; }
	 * 
	 * 
	 * public String getField1() { return field1; }
	 * 
	 * 
	 * public String getField2() { return field2; }
	 * 
	 * 
	 * public String getField3() { return field3; }
	 * 
	 * 
	 * public String getField4() { return field4; }
	 * 
	 * 
	 * public String getField5() { return field5; }
	 * 
	 * 
	 * public String getField6() { return field6; }
	 * 
	 * 
	 * public String getField7() { return field7; }
	 * 
	 * 
	 * public void setField1(String field1) { this.field1 = field1; }
	 * 
	 * 
	 * public void setField2(String field2) { this.field2 = field2; }
	 * 
	 * 
	 * public void setField3(String field3) { this.field3 = field3; }
	 * 
	 * 
	 * public void setField4(String field4) { this.field4 = field4; }
	 * 
	 * 
	 * public void setField5(String field5) { this.field5 = field5; }
	 * 
	 * 
	 * public void setField6(String field6) { this.field6 = field6; }
	 * 
	 * 
	 * public void setField7(String field7) { this.field7 = field7; }
	 * 
	 * 
	 * public String getBackImagePath() { return backImagePath; }
	 * 
	 * 
	 * public void setBackImagePath(String backImagePath) { this.backImagePath =
	 * backImagePath; }
	 * 
	 * 
	 * public String getFrontImagePath() { return frontImagePath; }
	 * 
	 * 
	 * public void setFrontImagePath(String frontImagePath) { this.frontImagePath =
	 * frontImagePath; }
	 */
}
