package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingShortDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1340462005156347807L;

	private String id;

	private String pickUpLocation;

	private String releaseLocation;

	private String pickUpCity;

	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "IST")
	private Date pickUpDate;

	@JsonFormat(pattern ="hh.mm a",timezone="IST")
	private Date pickUpTime;

	private String releaseAddress;

	private int status;

	private String travellerName;

	private String travellerMobile;

	private String companyName;

	private String driverAccountId;
	
	private String driverName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPickUpLocation() {
		return pickUpLocation;
	}

	public void setPickUpLocation(String pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}

	public String getReleaseLocation() {
		return releaseLocation;
	}

	public void setReleaseLocation(String releaseLocation) {
		this.releaseLocation = releaseLocation;
	}

	public String getPickUpCity() {
		return pickUpCity;
	}

	public void setPickUpCity(String pickUpCity) {
		this.pickUpCity = pickUpCity;
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

	public String getReleaseAddress() {
		return releaseAddress;
	}

	public void setReleaseAddress(String releaseAddress) {
		this.releaseAddress = releaseAddress;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTravellerName() {
		return travellerName;
	}

	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}

	public String getTravellerMobile() {
		return travellerMobile;
	}

	public void setTravellerMobile(String travellerMobile) {
		this.travellerMobile = travellerMobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDriverAccountId() {
		return driverAccountId;
	}

	public void setDriverAccountId(String driverAccountId) {
		this.driverAccountId = driverAccountId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
