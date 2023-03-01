package com.rvcabs.microservices.entity;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "drivertripdetail")
public class DriverTripDetailEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@Column
	private String accountId;
	
	public String getAccountId() {
		   return accountId;
		}

	public void setAccountId(String accountId) {
		   this.accountId = accountId;
		}


	@Column(nullable = false, columnDefinition = "tinyint(1) default 1")
	private Boolean isOngoingTrip;

	@Column
	private String driverLat;

	@Column
	private String driverLng;

	@Column(nullable = false, columnDefinition = "FLOAT default '0'")
	private Float totalDistance;

	@Column(nullable = false, columnDefinition = "FLOAT default '0'")
	private Float totalRating;

	@Column(nullable = false, columnDefinition = "DATETIME ")
	private Date nextTripStartTime;
	
	public Date getNextTripStartTime() {
		   return nextTripStartTime;
		}

		public void setNextTripStartTime(Date nextTripStartTime) {
		   this.nextTripStartTime = nextTripStartTime;
		}


	@Column
	private String deviceToken;
	
	public String getDeviceToken() {
		   return mobileNumber;
		}

		public void setDeviceToken(String deviceToken) {
		   this.deviceToken = deviceToken;
		}

	@Column(nullable = false, columnDefinition = "DATETIME")
	private Date onGoingTripStartTime;

	@LastModifiedDate
	@Column(nullable = false)
	protected ZonedDateTime updatedOn = ZonedDateTime.now();

	@Column
	private String mobileNumber;	

	public String getMobileNumber() {
	   return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
	   this.mobileNumber = mobileNumber;
	}


	@Column
	private String driverName;
	
	public String getDriverName() {
		   return driverName;
		}

		public void setDriverName(String driverName) {
		   this.driverName = driverName;
		}

	@Column(nullable = false, columnDefinition = "tinyint(1) default 1")
	private Boolean active;
	
	public Boolean getActive() {
		   return active;
		}

		public void setActive(Boolean active) {
		   this.active = active;
		}

}
