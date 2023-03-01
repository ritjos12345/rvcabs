package com.rvcabs.microservices.dto.notification;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRegistrationNotification extends EmailNotification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5567814206675621943L;

	private String accountId;

	private String emailId;

	private String firstName;

	private String lastName;
	
	private String password;
	
	private String userName;
}
