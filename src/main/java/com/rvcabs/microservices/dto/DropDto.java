package com.rvcabs.microservices.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DropDto implements Serializable{

	private byte[] content;
	
	private String feedback;
	
	private float rating;
	
	private int isPreferredDriver;
	
	private String tripId;
	
	private String sigURl;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getIsPreferredDriver() {
		return isPreferredDriver;
	}

	public void setIsPreferredDriver(int isPreferredDriver) {
		this.isPreferredDriver = isPreferredDriver;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getSigURl() {
		return sigURl;
	}

	public void setSigURl(String sigURl) {
		this.sigURl = sigURl;
	}
}
