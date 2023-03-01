package com.rvcabs.microservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "sysdocumenttype")
public class SysDocumentTypeEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5179411467988350661L;

	/**
	 * 
	 */

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, length = 255)
	private Integer documentId;

	@Column(length = 500)
	private String docName;

	@Column(length = 350)
	private String description;

	@Column(name = "active", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean active;

	/*@Column(columnDefinition = "tinyint(1) default 1")
	private boolean status;*/

	@Column
	private String keyName;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
