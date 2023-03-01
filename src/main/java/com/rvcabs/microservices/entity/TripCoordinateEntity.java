package com.rvcabs.microservices.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tripcoordinate")
public class TripCoordinateEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2115748604983429713L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@Column
	private String tripId;

	@Column
	private String srcLat;

	@Column
	private String srcLng;

	@Column
	private String dstLat;

	@Column
	private String dstLng;

	@Column
	private String srcName;

	@Column
	private String dstName;
	

	@Column
	private String distance;
	
	@Column(name="timeDifference")
	private String timeDifference;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private ZonedDateTime createdOn = ZonedDateTime.now();

	@LastModifiedDate
	@Column(nullable = false)
	private ZonedDateTime updatedOn = ZonedDateTime.now();

	@PrePersist
	protected void onCreate() {
		// ZoneId.of("UTC")
		updatedOn = createdOn = ZonedDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		// ZoneId.of("UTC")
		updatedOn = ZonedDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
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

	public String getDstLat() {
		return dstLat;
	}

	public void setDstLat(String dstLat) {
		this.dstLat = dstLat;
	}

	public String getDstLng() {
		return dstLng;
	}

	public void setDstLng(String dstLng) {
		this.dstLng = dstLng;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getDstName() {
		return dstName;
	}

	public void setDstName(String dstName) {
		this.dstName = dstName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
