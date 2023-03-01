package com.rvcabs.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "documentdetail")
public class DocumentDetailEntity extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@Column
	private String accountId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "documentTypeId", nullable = false)
	private SysDocumentTypeEntity documentTypeEntity;

	@Column
	private String frontImagePath;

	@Column
	private String backImagePath;

	@Column(columnDefinition = "BLOB")
	private String fieldDetails;

	@Column
	private boolean markfordeletion;

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

	public SysDocumentTypeEntity getDocumentTypeEntity() {
		return documentTypeEntity;
	}

	public void setDocumentTypeEntity(SysDocumentTypeEntity documentTypeEntity) {
		this.documentTypeEntity = documentTypeEntity;
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

	public boolean isMarkfordeletion() {
		return markfordeletion;
	}

	public void setMarkfordeletion(boolean markfordeletion) {
		this.markfordeletion = markfordeletion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
