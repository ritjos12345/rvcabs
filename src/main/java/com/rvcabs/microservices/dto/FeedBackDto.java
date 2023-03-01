package com.rvcabs.microservices.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedBackDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tripId;

	private Float rating;

	private String feedBack;

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
