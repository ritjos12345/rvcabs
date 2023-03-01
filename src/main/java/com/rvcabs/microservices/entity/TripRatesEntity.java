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
@Table(name = "tripratesmaster")
public class TripRatesEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, length = 255)
	private Integer id;

	@Column(name = "active", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column
	private int cartype;

	@Column
	private String minimum;

	@Column
	private int triptype;

	@Column
	private float nightallowance;

	@Column
	private String basic;

	@Column
	private float dayallowance;

	@Column
	private float rates;
	
	@Column
	private String natimings;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getCartype() {
		return cartype;
	}

	public void setCartype(int cartype) {
		this.cartype = cartype;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public int getTriptype() {
		return triptype;
	}

	public void setTriptype(int triptype) {
		this.triptype = triptype;
	}

	public float getNightallowance() {
		return nightallowance;
	}

	public void setNightallowance(float nightallowance) {
		this.nightallowance = nightallowance;
	}

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public float getDayallowance() {
		return dayallowance;
	}

	public void setDayallowance(float dayallowance) {
		this.dayallowance = dayallowance;
	}

	public float getRates() {
		return rates;
	}

	public void setRates(float rates) {
		this.rates = rates;
	}

	public String getNatimings() {
		return natimings;
	}

	public void setNatimings(String natimings) {
		this.natimings = natimings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
