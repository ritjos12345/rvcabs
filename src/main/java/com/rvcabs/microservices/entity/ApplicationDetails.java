package com.rvcabs.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "applicationdetails")
public class ApplicationDetails extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5014270540151001446L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "applicationId",length=100)
	private String applicationId;

	@NotNull
	@Column(name = "accountId", length=100)
	private String accountId;

	@NotNull
	@Column(name = "applicationNname",length=50)
	private String applicationName;

	@NotNull
	@Column(name = "description",length=500)
	private String description;
	
	@NotNull
	@Column(name = "paymentProcessor",length=20)
	private String paymentProcessor;
	
	@NotNull
	@Column(name = "publickey",length = 5000)
	private String publickey;
	
	@NotNull
	@Column(name = "privatekey",length = 5000)
	private String privatekey;
	
	@NotNull
	@Column(name = "clientId", nullable = false, length = 150)
	private String clientId;
	
	@NotNull
	@Column(name = "clientSecret", nullable = false, length = 150)
	private String clientSecret;
	
}
