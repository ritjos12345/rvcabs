package com.rvcabs.microservices.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tripdetail")
public class TripDetailEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1838414581202010092L;
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;
	
	@Column
	private String bookingId;
	
	@Column
	private ZonedDateTime tripStartTime;
	
	@Column
	private ZonedDateTime tripEndTime;
		
	@Column
	private float tripRating;
	
	@Column
	private String tripFeedBack;
	
	@Column
	private float tripTotalDistance;
	
	@Column
	private String tripImage;
	
	@Column
	private String signatureURL;
	
	@Column
	private String feedBackSource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public ZonedDateTime getTripStartTime() {
		return tripStartTime;
	}

	public void setTripStartTime(ZonedDateTime tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public ZonedDateTime getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(ZonedDateTime tripEndTime) {
		this.tripEndTime = tripEndTime;
	}

	public float getTripRating() {
		return tripRating;
	}

	public void setTripRating(float tripRating) {
		this.tripRating = tripRating;
	}

	public String getTripFeedBack() {
		return tripFeedBack;
	}

	public void setTripFeedBack(String tripFeedBack) {
		this.tripFeedBack = tripFeedBack;
	}

	public float getTripTotalDistance() {
		return tripTotalDistance;
	}

	public void setTripTotalDistance(float tripTotalDistance) {
		this.tripTotalDistance = tripTotalDistance;
	}

	public String getTripImage() {
		return tripImage;
	}

	public void setTripImage(String tripImage) {
		this.tripImage = tripImage;
	}

	public String getSignatureURL() {
		return signatureURL;
	}

	public void setSignatureURL(String signatureURL) {
		this.signatureURL = signatureURL;
	}

	public String getFeedBackSource() {
		return feedBackSource;
	}

	public void setFeedBackSource(String feedBackSource) {
		this.feedBackSource = feedBackSource;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/*
	 * @Column private String driverContactNumber;
	 */
	
	
	
	
}
