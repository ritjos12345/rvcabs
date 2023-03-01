package com.rvcabs.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contactperson")
public class ContactPersonEntity {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -978387498307375788L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@NotNull
	@Column(name = "firstName", nullable = false, length = 100)
	private String firstName;
	
	@NotNull
	@Column(name = "lastName", nullable = false, length = 100)
	private String lastName;

	@NotNull
	@Column(name = "emailId", nullable = false, length = 50)
	private String emailId;

	@NotNull
	@Column(name = "mobileNumber", nullable = false, length = 15)
	private String mobileNumber;
	
	@Column(name = "companyId", nullable = false, length = 50)
	private String companyId;
	
	@Column(name = "vendorId", nullable = false, length = 50)
	private String vendorId;
	
	

}
