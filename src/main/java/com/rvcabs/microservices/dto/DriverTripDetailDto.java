package com.rvcabs.microservices.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DriverTripDetailDto {

	private String id;

	private String accountId;

	private Boolean isOngoingTrip;

	private String driverLat;

	private String driverLng;

	private Date nextTripStartTime;

	private Float totalDistance;

	private Float totalRating;

	private String deviceToken;

	private Date onGoingTripStartTime;

	private String mobileNumber;

	private String driverName;

	private Boolean active;
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public Boolean getIsOngoingTrip() {
		return isOngoingTrip;
	}

	public void setIsOngoingTrip(Boolean isOngoingTrip) {
		this.isOngoingTrip = isOngoingTrip;
	}

	public String getDriverLat() {
		return driverLat;
	}

	public void setDriverLat(String driverLat) {
		this.driverLat = driverLat;
	}

	public String getDriverLng() {
		return driverLng;
	}

	public void setDriverLng(String driverLng) {
		this.driverLng = driverLng;
	}

	public Date getNextTripStartTime() {
		return nextTripStartTime;
	}

	public void setNextTripStartTime(Date nextTripStartTime) {
		this.nextTripStartTime = nextTripStartTime;
	}

	public Float getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Float totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Float getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(Float totalRating) {
		this.totalRating = totalRating;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Date getOnGoingTripStartTime() {
		return onGoingTripStartTime;
	}

	public void setOnGoingTripStartTime(Date onGoingTripStartTime) {
		this.onGoingTripStartTime = onGoingTripStartTime;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

}
