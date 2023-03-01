package com.rvcabs.microservices.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TripAddInfoDto {

	private String tripId;

	private byte[] content;

	private float count;

	private String driverAccountId;
	
	private String bookingId;
	
	private String srcLat;

	private String srcLng;
	
	private String srcName;
	
	private String distance;
	
	private String picUrl;
	
	private String tripType;

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public float getCount() {
		return count;
	}

	public void setCount(float count) {
		this.count = count;
	}

	public String getDriverAccountId() {
		return driverAccountId;
	}

	public void setDriverAccountId(String driverAccountId) {
		this.driverAccountId = driverAccountId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getSrcLat() {
		return srcLat;
	}

	public void setSrcLat(String srcLat) {
		this.srcLat = srcLat;
	}

	public String getSrcLng() {
		return srcLng;
	}

	public void setSrcLng(String srcLng) {
		this.srcLng = srcLng;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

}
