package com.rvcabs.microservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RolesEntity extends BaseEntity<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6239210136107082983L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, length = 255)
	private Long roleId;

	@Column(length = 500)
	private String roleName;

	@Column(length = 350)
	private String description;

	@Column(columnDefinition = "tinyint(1) default 0")
	private boolean isUserDefinedRole;

	/*@Column(columnDefinition = "tinyint(1) default 1")
	private boolean status;*/

	@Column
	private String userType;



}
