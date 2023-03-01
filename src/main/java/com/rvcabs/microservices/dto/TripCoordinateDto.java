package com.rvcabs.microservices.dto;

import java.util.List;

import com.rvcabs.microservices.entity.TripCoordinateEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripCoordinateDto {

	private List<TripCoordinateEntity> tripList;

	private String totalTime;

	private String totalDistance;

	public List<TripCoordinateEntity> getTripList() {
		return tripList;
	}

	public void setTripList(List<TripCoordinateEntity> tripList) {
		this.tripList = tripList;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}
}
