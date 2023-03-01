package com.rvcabs.microservices.dto.notification;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3887275370301082795L;


	private String fromEmailAddress;

	private String fromEmailName;

	private String logoURL;
	
	private String copyRightsFrom;
	
	private String termsAndConditionsURL;
	
	private String privacyPolicyURL;
	
	private String regardsFrom;
	
	private String actionName;
	
	private String contactSupportURL;

	private String myAccountURL;

	private String appName;

	private String supportEmailId;

	private String appPortal;

}
