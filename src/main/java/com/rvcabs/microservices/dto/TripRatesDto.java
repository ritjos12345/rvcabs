package com.rvcabs.microservices.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TripRatesDto {
	
	private Integer id;

	private boolean active;

	private int cartype;

	private String minimum;

	private int triptype;

	private float nightallowance;

	private String basic;

	private float dayallowance;

	private float rates;
	
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
	
}
