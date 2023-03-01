package com.rvcabs.microservices.entity;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "bookingdetail")
public class BookingDetailEntity extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2576651811233026922L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@Column
	private String accountId;
	
	@Column
	private String driverAccountId;

	@Column
	private String pickUpCity;

	@Column
	private String pickUpLocation;

	@Column
	private String pickUpAddress;

	@Column
	private String travelId;

	@Column
	private String releaseLocation;

	@Column
	private String releaseAddress;

	@Temporal(TemporalType.DATE)
	private Date pickUpDate;

	@Temporal(TemporalType.TIME)
	private Date pickUpTime;

	@Temporal(TemporalType.DATE)
	private Date dropDate;

	@Temporal(TemporalType.TIME)
	private Date dropTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carSegmentId", nullable = false)
	private SysCarCategoryEntity sysCarCategoryEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requestTypeId", nullable = false)
	private SysBookingRequestTypeEntity sysBookingRequestTypeEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId", nullable = false)
	private CompanyDetailsEntity companyDetailsEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carId", nullable = false)
	private SysCarMasterEntity sysCarMasterEntity;

	/*
	 * tripStatus flag is as follows 1-New Booking 2-Booking Reqest Sent To Driver
	 * 3-Accepted By Driver 4-Trip Started 5-Trip End 9-Trip Cancelled By User
	 * 10-Trip Cancelled By Driver
	 */
	@Column
	private Integer tripStatus;

	@Column
	private String carHire;
	
	/*
	 * @Column private String carUse;
	 */
	@Column
	private String instruction;
	
	@Column
	private String traveldeskname;

	@Column
	private String traveldeskEmailId;
	
	@Column
	private String costCenter;
	
//below 2 fields added to resolve error ----Ritesh 10--2-2023	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected ZonedDateTime createdOn = ZonedDateTime.now();

	@Column
	protected ZonedDateTime updatedOn = ZonedDateTime.now();
	
	
	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public ZonedDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(ZonedDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

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

	public String getDriverAccountId() {
		return driverAccountId;
	}

	public void setDriverAccountId(String driverAccountId) {
		this.driverAccountId = driverAccountId;
	}

	public String getPickUpCity() {
		return pickUpCity;
	}

	public void setPickUpCity(String pickUpCity) {
		this.pickUpCity = pickUpCity;
	}

	public String getPickUpLocation() {
		return pickUpLocation;
	}

	public void setPickUpLocation(String pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}

	public String getPickUpAddress() {
		return pickUpAddress;
	}

	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
	}

	public String getTravelId() {
		return travelId;
	}

	public void setTravelId(String travelId) {
		this.travelId = travelId;
	}

	public String getReleaseLocation() {
		return releaseLocation;
	}

	public void setReleaseLocation(String releaseLocation) {
		this.releaseLocation = releaseLocation;
	}

	public String getReleaseAddress() {
		return releaseAddress;
	}

	public void setReleaseAddress(String releaseAddress) {
		this.releaseAddress = releaseAddress;
	}

	public Date getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public Date getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public Date getDropDate() {
		return dropDate;
	}

	public void setDropDate(Date dropDate) {
		this.dropDate = dropDate;
	}

	public Date getDropTime() {
		return dropTime;
	}

	public void setDropTime(Date dropTime) {
		this.dropTime = dropTime;
	}

	public SysCarCategoryEntity getSysCarCategoryEntity() {
		return sysCarCategoryEntity;
	}

	public void setSysCarCategoryEntity(SysCarCategoryEntity sysCarCategoryEntity) {
		this.sysCarCategoryEntity = sysCarCategoryEntity;
	}

	public SysBookingRequestTypeEntity getSysBookingRequestTypeEntity() {
		return sysBookingRequestTypeEntity;
	}

	public void setSysBookingRequestTypeEntity(SysBookingRequestTypeEntity sysBookingRequestTypeEntity) {
		this.sysBookingRequestTypeEntity = sysBookingRequestTypeEntity;
	}

	public CompanyDetailsEntity getCompanyDetailsEntity() {
		return companyDetailsEntity;
	}

	public void setCompanyDetailsEntity(CompanyDetailsEntity companyDetailsEntity) {
		this.companyDetailsEntity = companyDetailsEntity;
	}

	public SysCarMasterEntity getSysCarMasterEntity() {
		return sysCarMasterEntity;
	}

	public void setSysCarMasterEntity(SysCarMasterEntity sysCarMasterEntity) {
		this.sysCarMasterEntity = sysCarMasterEntity;
	}

	public Integer getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(Integer tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getCarHire() {
		return carHire;
	}

	public void setCarHire(String carHire) {
		this.carHire = carHire;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getTraveldeskname() {
		return traveldeskname;
	}

	public void setTraveldeskname(String traveldeskname) {
		this.traveldeskname = traveldeskname;
	}

	public String getTraveldeskEmailId() {
		return traveldeskEmailId;
	}

	public void setTraveldeskEmailId(String traveldeskEmailId) {
		this.traveldeskEmailId = traveldeskEmailId;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
