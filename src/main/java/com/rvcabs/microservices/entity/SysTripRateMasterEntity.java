package com.rvcabs.microservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tripratemaster")
public class SysTripRateMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, length = 255)
	private Integer id;

	@Column
	private String premiumsedan;

	@Column
	private String compactsedan;

	@Column(name = "active", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column
	private String suv;

	@Column
	private String sedan;

	@Column
	private String premiumsuv;

	@Column
	private String minimum;

	@Column
	private int triptype;

	@Column
	private String nightallowance;

	@Column
	private String index;

	@Column
	private String dayallowance;

}
