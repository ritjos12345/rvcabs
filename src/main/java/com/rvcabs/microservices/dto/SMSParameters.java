package com.rvcabs.microservices.dto;

public class SMSParameters  {
	public String targetDeviceNum = null;
	public String message = null;
	public String SMS_GATEWAY_URL= "http://bulkdnd.mobi-marketing.biz/api/postsms.php";
	public String SMS_GATEWAY_USER_NAME= null;
	public String SMS_GATEWAY_PASSWORD= null;
	public String CAMPAIGN="RVCAB";
	public int ROUTE=6;
	public String COUNTRY_CODE = "91";
	public String AUTHKEY = "8574ATRzOBwHfbK55e85d71cP11";
	public String SENDER_ID = "RVTECH";
	public String toString() {
		StringBuffer sb = new StringBuffer("Dumping an SMSParameter Object:\n");
		sb.append("\tTargetDeviceID=["+targetDeviceNum+"]\n");
		sb.append("\tmessage=["+message+"]\n");
		return sb.toString();
	}
	public String getTargetDeviceNum() {
		return targetDeviceNum;
	}
	public void setTargetDeviceNum(String targetDeviceNum) {
		this.targetDeviceNum = targetDeviceNum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSMS_GATEWAY_URL() {
		return SMS_GATEWAY_URL;
	}
	public void setSMS_GATEWAY_URL(String sMS_GATEWAY_URL) {
		SMS_GATEWAY_URL = sMS_GATEWAY_URL;
	}
	public String getSMS_GATEWAY_USER_NAME() {
		return SMS_GATEWAY_USER_NAME;
	}
	public void setSMS_GATEWAY_USER_NAME(String sMS_GATEWAY_USER_NAME) {
		SMS_GATEWAY_USER_NAME = sMS_GATEWAY_USER_NAME;
	}
	public String getSMS_GATEWAY_PASSWORD() {
		return SMS_GATEWAY_PASSWORD;
	}
	public void setSMS_GATEWAY_PASSWORD(String sMS_GATEWAY_PASSWORD) {
		SMS_GATEWAY_PASSWORD = sMS_GATEWAY_PASSWORD;
	}
	public String getCAMPAIGN() {
		return CAMPAIGN;
	}
	public void setCAMPAIGN(String cAMPAIGN) {
		CAMPAIGN = cAMPAIGN;
	}
	public int getROUTE() {
		return ROUTE;
	}
	public void setROUTE(int rOUTE) {
		ROUTE = rOUTE;
	}
	public String getCOUNTRY_CODE() {
		return COUNTRY_CODE;
	}
	public void setCOUNTRY_CODE(String cOUNTRY_CODE) {
		COUNTRY_CODE = cOUNTRY_CODE;
	}
	public String getAUTHKEY() {
		return AUTHKEY;
	}
	public void setAUTHKEY(String aUTHKEY) {
		AUTHKEY = aUTHKEY;
	}
	public String getSENDER_ID() {
		return SENDER_ID;
	}
	public void setSENDER_ID(String sENDER_ID) {
		SENDER_ID = sENDER_ID;
	}
}
