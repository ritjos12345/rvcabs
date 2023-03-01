package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpenditureDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	
	private String expenceType;
	
	
	private String location;
	
	
	private Float totalAmount;
	
	
	private Float totalLt;
	
	
	private Float perLtRate;
	
	private Float odoMeterReading;
	
	private String description;
	
	private String fileUrl;

	
	private String source;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	private Date entryDate;
	
	
	private String status;
	
	
	private boolean active;
	
	private String driverAccountId;
	
	private String vehicleNumber;
	
	private String notes;
	
	private String paymentMode;
	
	private String driverName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpenceType() {
		return expenceType;
	}

	public void setExpenceType(String expenceType) {
		this.expenceType = expenceType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Float getTotalLt() {
		return totalLt;
	}

	public void setTotalLt(Float totalLt) {
		this.totalLt = totalLt;
	}

	public Float getPerLtRate() {
		return perLtRate;
	}

	public void setPerLtRate(Float perLtRate) {
		this.perLtRate = perLtRate;
	}

	public Float getOdoMeterReading() {
		return odoMeterReading;
	}

	public void setOdoMeterReading(Float odoMeterReading) {
		this.odoMeterReading = odoMeterReading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDriverAccountId() {
		return driverAccountId;
	}

	public void setDriverAccountId(String driverAccountId) {
		this.driverAccountId = driverAccountId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
