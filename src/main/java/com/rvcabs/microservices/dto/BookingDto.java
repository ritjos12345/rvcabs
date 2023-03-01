package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1340462005156347807L;

	private String id;

	private String accountId;

	private String driverAccountId;

	@NotNull(message = "pickUpCity is mandatory")
	@ApiModelProperty(notes = "Pick Up City From where trip start", required = true, allowEmptyValue = false, example = "Banglore")
	private String pickUpCity;

	@NotNull(message = "pickUpLocation is mandatory")
	@ApiModelProperty(notes = "Pick Up Location From where trip start", required = true, allowEmptyValue = false, example = "Jayanagar")
	private String pickUpLocation;

	@NotNull(message = "pickUpAddress is mandatory")
	@ApiModelProperty(notes = "Pick Up Location From where trip start", required = true, allowEmptyValue = false, example = "4th Cross,3rd Block")
	private String pickUpAddress;

	@NotNull(message = "travelId is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example = "TN0u8u")
	private String travelId;

	@NotNull(message = "releaseLocation is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example = "Koramangala")
	private String releaseLocation;

	@NotNull(message = "releaseAddress is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example = "5th cross,1st Block")
	private String releaseAddress;

	@NotNull(message = "pickUpDate is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false)
	@JsonFormat(pattern = "dd-MM-yyyy",timezone="IST")
	private Date pickUpDate;

	@NotNull(message = "pickUpTime is mandatory")
	@JsonFormat(pattern ="hh.mm a",timezone="IST")
	private Date pickUpTime;

	@NotNull(message = "dropDate is mandatory")
	@JsonFormat(pattern = "dd-MM-yyyy",timezone="IST")
	private Date dropDate;

	@NotNull(message = "dropTime is mandatory")
	@JsonFormat(pattern ="hh.mm a",timezone="IST")
	private Date dropTime;

	@Autowired
	private MasterDataDto sysCarCategoryDto;

	@Autowired
	private MasterDataDto sysCarMasterDto;

	@Autowired
	private MasterDataDto sysBookingRequestTypeDto;

	@Autowired
	private TravelerDetailDto travelerDetailDto;
	
	
	@Autowired
	private TravelerDetailDto driverDetailDto;

	@Autowired
	private CompanyDetailDto companyDetailDto;

	/*
	 * tripStatus flag is as follows 1-New Booking 2-Booking Reqest Sent To Driver
	 * 3-Accepted By Driver 4-Trip Started 5-Trip End 9-Trip Cancelled By User
	 * 10-Trip Cancelled By Driver
	 */

	private Integer tripStatus;

	@NotNull(message = "carHire is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example = "One Way")
	private String carHire;

	/*
	 * @NotNull(message = "carUse is mandatory") private String carUse;
	 */

	private String instruction;

	private ZonedDateTime notificationExpTime;

	private String tripId;

	private String totalTime;

	private String totalDistance;

	@NotNull(message = "Travel desk name is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example = "Amit Patgaonkar")
	private String traveldeskname;

	@NotNull(message = "Travel desk email is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example = "amit@gmail.com")
	private String traveldeskEmailId;

	private String costCenter;
	
	@JsonFormat(pattern = "dd-MM-yyyy",timezone="IST")
	private ZonedDateTime createdDate;
	
	@JsonFormat(pattern ="hh.mm a",timezone="IST")
	private ZonedDateTime createdTime;

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

	public MasterDataDto getSysCarCategoryDto() {
		return sysCarCategoryDto;
	}

	public void setSysCarCategoryDto(MasterDataDto sysCarCategoryDto) {
		this.sysCarCategoryDto = sysCarCategoryDto;
	}

	public MasterDataDto getSysCarMasterDto() {
		return sysCarMasterDto;
	}

	public void setSysCarMasterDto(MasterDataDto sysCarMasterDto) {
		this.sysCarMasterDto = sysCarMasterDto;
	}

	public MasterDataDto getSysBookingRequestTypeDto() {
		return sysBookingRequestTypeDto;
	}

	public void setSysBookingRequestTypeDto(MasterDataDto sysBookingRequestTypeDto) {
		this.sysBookingRequestTypeDto = sysBookingRequestTypeDto;
	}

	public TravelerDetailDto getTravelerDetailDto() {
		return travelerDetailDto;
	}

	public void setTravelerDetailDto(TravelerDetailDto travelerDetailDto) {
		this.travelerDetailDto = travelerDetailDto;
	}

	public TravelerDetailDto getDriverDetailDto() {
		return driverDetailDto;
	}

	public void setDriverDetailDto(TravelerDetailDto driverDetailDto) {
		this.driverDetailDto = driverDetailDto;
	}

	public CompanyDetailDto getCompanyDetailDto() {
		return companyDetailDto;
	}

	public void setCompanyDetailDto(CompanyDetailDto companyDetailDto) {
		this.companyDetailDto = companyDetailDto;
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

	public ZonedDateTime getNotificationExpTime() {
		return notificationExpTime;
	}

	public void setNotificationExpTime(ZonedDateTime notificationExpTime) {
		this.notificationExpTime = notificationExpTime;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
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

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public ZonedDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(ZonedDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
