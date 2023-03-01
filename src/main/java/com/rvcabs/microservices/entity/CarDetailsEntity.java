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

@Entity
@Table(name = "cardetail")
@Setter
@Getter
public class CarDetailsEntity extends BaseEntity<String> {

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
	@JoinColumn(name = "typeId", nullable = false)
	private SysCarMasterEntity sysCarMaster;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subTypeId", nullable = false)
	private SysCarSubTypeEntity sysCarSubType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carColorId", nullable = false)
	private SysCarColorEntity sysCarColor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carIntColorId", nullable = false)
	private SysCarIntColorEntity sysCarIntColor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carCategoryId", nullable = false)
	private SysCarCategoryEntity sysCarCategoryEntity;
	
	@Column
	private String carImage;

	@Column
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

	public SysCarMasterEntity getSysCarMaster() {
		return sysCarMaster;
	}

	public void setSysCarMaster(SysCarMasterEntity sysCarMaster) {
		this.sysCarMaster = sysCarMaster;
	}

	public SysCarSubTypeEntity getSysCarSubType() {
		return sysCarSubType;
	}

	public void setSysCarSubType(SysCarSubTypeEntity sysCarSubType) {
		this.sysCarSubType = sysCarSubType;
	}

	public SysCarColorEntity getSysCarColor() {
		return sysCarColor;
	}

	public void setSysCarColor(SysCarColorEntity sysCarColor) {
		this.sysCarColor = sysCarColor;
	}

	public SysCarIntColorEntity getSysCarIntColor() {
		return sysCarIntColor;
	}

	public void setSysCarIntColor(SysCarIntColorEntity sysCarIntColor) {
		this.sysCarIntColor = sysCarIntColor;
	}

	public SysCarCategoryEntity getSysCarCategoryEntity() {
		return sysCarCategoryEntity;
	}

	public void setSysCarCategoryEntity(SysCarCategoryEntity sysCarCategoryEntity) {
		this.sysCarCategoryEntity = sysCarCategoryEntity;
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
