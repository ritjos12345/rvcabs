package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DocumentTypeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1752839164029757405L;
	
	private Integer documentId;

	private String docName;

	private String description;

	private boolean active;

	private String keyName;
	
	private Map<String,String> documentField;

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
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

}
