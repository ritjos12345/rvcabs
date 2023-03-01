package com.rvcabs.microservices.dto;

/**
 * stores status of SMS operations, messageID, error_code, error description
 * @author hui
 *
 */
public class MessageSendStatus {
	private String messageId = null;
	private String errorCode = null;
	private String errorDescription = null;
	
	public static String SMS_EC_UNEXPECTED = "9050";
	public static String[] SMS_EC_SUCCESS = {"0", "1", "2", "3", "4", "5"};
	
	public static String EMAIL_EC_FAIL = "19999";
	public static String[] EMAIL_EC_SUCCESS = {"10001"};
	
	public MessageSendStatus() {
		super();
	}
	
	public MessageSendStatus(String message_id, String error_code, String error_description) {
		super();
		
		messageId = message_id;
		errorCode = error_code;
		errorDescription = error_description;
	}
	
	public String getMessageId() {
		return messageId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setMessageId(String value) {
		messageId = value;
	}
	public void setErrorCode(String value) {
		errorCode = value;
	}
	public void setErrorDescription(String value) {
		errorDescription = value;
	}
	
	public String dump() {
		return "******\nDumping SMSStatus Object ...\n  messageId="+messageId+"\n  errorCode="+errorCode+"\n  errorDescription="+errorDescription+"\n******";
	}
	
	public boolean isStatusOk() {
		boolean retval = false;
		for( int i=0; i<SMS_EC_SUCCESS.length; i++ ) {
			if( SMS_EC_SUCCESS[i].equalsIgnoreCase(errorCode) ) {
				retval = true;
				break;
			}
		}
		
		if( EMAIL_EC_SUCCESS[0].equalsIgnoreCase(errorCode) ) {
			retval = true;
		}
		return retval;
	}
	
	public boolean isStatusOk(String status) {
		boolean retval = false;
		for( int i=0; i<SMS_EC_SUCCESS.length; i++ ) {
			if( SMS_EC_SUCCESS[i].equalsIgnoreCase(status) ) {
				retval = true;
				break;
			}
		}
		if( EMAIL_EC_SUCCESS[0].equalsIgnoreCase(status) ) {
			retval = true;
		}
		return retval;
	}

	public static String getSMS_EC_UNEXPECTED() {
		return SMS_EC_UNEXPECTED;
	}

	public static void setSMS_EC_UNEXPECTED(String sMS_EC_UNEXPECTED) {
		SMS_EC_UNEXPECTED = sMS_EC_UNEXPECTED;
	}

	public static String[] getSMS_EC_SUCCESS() {
		return SMS_EC_SUCCESS;
	}

	public static void setSMS_EC_SUCCESS(String[] sMS_EC_SUCCESS) {
		SMS_EC_SUCCESS = sMS_EC_SUCCESS;
	}

	public static String getEMAIL_EC_FAIL() {
		return EMAIL_EC_FAIL;
	}

	public static void setEMAIL_EC_FAIL(String eMAIL_EC_FAIL) {
		EMAIL_EC_FAIL = eMAIL_EC_FAIL;
	}

	public static String[] getEMAIL_EC_SUCCESS() {
		return EMAIL_EC_SUCCESS;
	}

	public static void setEMAIL_EC_SUCCESS(String[] eMAIL_EC_SUCCESS) {
		EMAIL_EC_SUCCESS = eMAIL_EC_SUCCESS;
	}
}
