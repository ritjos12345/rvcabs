package com.rvcabs.microservices.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushNotificationRequest {

private String topic= "common"; 
private String title= "Common topic - Hello";
private String message= "Sending test message \uD83D\uDE42";
private String token= "ss22t03wz208eg=APA2idkkow223FE_0v5yHxqCLTyxAQafj6nWaqi4QzwZTW004q1PUux63UsFN";
private String payloadMessageId= "123";
private String payloadData= "Hello. This is payload content.";
public String getTopic() {
	return topic;
}
public void setTopic(String topic) {
	this.topic = topic;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public String getPayloadMessageId() {
	return payloadMessageId;
}
public void setPayloadMessageId(String payloadMessageId) {
	this.payloadMessageId = payloadMessageId;
}
public String getPayloadData() {
	return payloadData;
}
public void setPayloadData(String payloadData) {
	this.payloadData = payloadData;
}
}
