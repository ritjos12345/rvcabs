package com.rvcabs.microservices.dto;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
	
	private String sms;
	
	private String deviceToken;
	
	private String smsId;
	
	private String pickUpTime;
	
	private int type;
	
	private Map<String,String> configMap;
	
	private Date notificationExpTime;
	
	private String notificationHeader;
	
	private String notificationSubject;
	
	private String bookingTripId;
	
	private String accountId;

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	public Date getNotificationExpTime() {
		return notificationExpTime;
	}

	public void setNotificationExpTime(Date notificationExpTime) {
		this.notificationExpTime = notificationExpTime;
	}

	public String getNotificationHeader() {
		return notificationHeader;
	}

	public void setNotificationHeader(String notificationHeader) {
		this.notificationHeader = notificationHeader;
	}

	public String getNotificationSubject() {
		return notificationSubject;
	}

	public void setNotificationSubject(String notificationSubject) {
		this.notificationSubject = notificationSubject;
	}

	public String getBookingTripId() {
		return bookingTripId;
	}

	public void setBookingTripId(String bookingTripId) {
		this.bookingTripId = bookingTripId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	

}
