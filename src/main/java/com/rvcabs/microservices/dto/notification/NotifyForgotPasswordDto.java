package com.rvcabs.microservices.dto.notification;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotifyForgotPasswordDto extends EmailNotification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8660818015563241281L;

	private String password;

	private String firstName;
	
	private String lastName;

	private String emailId;

	private String userName;
}
