package com.rvcabs.microservices.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillingDto {

	
	private String id;
	
	private String bookingId;

	private Date fromDate;

	private Date toDate;

	private String costCentre;

	private String travelId;

	private String empNo;

	private String gstNo;

	private String empName;

	private Long dutySlip;

	private Date dutySlipDate;

	private String typeOfDuty;

	private String vehicleSeg;

	private String vehicleType;

	private Float basic;

	private String carNo;

	private String openingKM;

	private String closingKM;

	private String perKmRate;

	private String totalKm;

	private Float totalKmAmt;

	private Date pickUpTime;

	private Date pickDropTime;

	private Float perHrRate;

	private String totalHr;

	private Float hidenHr;

	private Integer totalHrAmt;
	
	private Float naDa;

	private Float tolllPark;

	private Float totalTripBill;

	private String nameOfCompany;
	
	private int adjustHr;
	
	private int adjustKm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCostCentre() {
		return costCentre;
	}

	public void setCostCentre(String costCentre) {
		this.costCentre = costCentre;
	}

	public String getTravelId() {
		return travelId;
	}

	public void setTravelId(String travelId) {
		this.travelId = travelId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Long getDutySlip() {
		return dutySlip;
	}

	public void setDutySlip(Long dutySlip) {
		this.dutySlip = dutySlip;
	}

	public Date getDutySlipDate() {
		return dutySlipDate;
	}

	public void setDutySlipDate(Date dutySlipDate) {
		this.dutySlipDate = dutySlipDate;
	}

	public String getTypeOfDuty() {
		return typeOfDuty;
	}

	public void setTypeOfDuty(String typeOfDuty) {
		this.typeOfDuty = typeOfDuty;
	}

	public String getVehicleSeg() {
		return vehicleSeg;
	}

	public void setVehicleSeg(String vehicleSeg) {
		this.vehicleSeg = vehicleSeg;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Float getBasic() {
		return basic;
	}

	public void setBasic(Float basic) {
		this.basic = basic;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getOpeningKM() {
		return openingKM;
	}

	public void setOpeningKM(String openingKM) {
		this.openingKM = openingKM;
	}

	public String getClosingKM() {
		return closingKM;
	}

	public void setClosingKM(String closingKM) {
		this.closingKM = closingKM;
	}

	public String getPerKmRate() {
		return perKmRate;
	}

	public void setPerKmRate(String perKmRate) {
		this.perKmRate = perKmRate;
	}

	public String getTotalKm() {
		return totalKm;
	}

	public void setTotalKm(String totalKm) {
		this.totalKm = totalKm;
	}

	public Float getTotalKmAmt() {
		return totalKmAmt;
	}

	public void setTotalKmAmt(Float totalKmAmt) {
		this.totalKmAmt = totalKmAmt;
	}

	public Date getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public Date getPickDropTime() {
		return pickDropTime;
	}

	public void setPickDropTime(Date pickDropTime) {
		this.pickDropTime = pickDropTime;
	}

	public Float getPerHrRate() {
		return perHrRate;
	}

	public void setPerHrRate(Float perHrRate) {
		this.perHrRate = perHrRate;
	}

	public String getTotalHr() {
		return totalHr;
	}

	public void setTotalHr(String totalHr) {
		this.totalHr = totalHr;
	}

	public Float getHidenHr() {
		return hidenHr;
	}

	public void setHidenHr(Float hidenHr) {
		this.hidenHr = hidenHr;
	}

	public Integer getTotalHrAmt() {
		return totalHrAmt;
	}

	public void setTotalHrAmt(Integer totalHrAmt) {
		this.totalHrAmt = totalHrAmt;
	}

	public Float getNaDa() {
		return naDa;
	}

	public void setNaDa(Float naDa) {
		this.naDa = naDa;
	}

	public Float getTolllPark() {
		return tolllPark;
	}

	public void setTolllPark(Float tolllPark) {
		this.tolllPark = tolllPark;
	}

	public Float getTotalTripBill() {
		return totalTripBill;
	}

	public void setTotalTripBill(Float totalTripBill) {
		this.totalTripBill = totalTripBill;
	}

	public String getNameOfCompany() {
		return nameOfCompany;
	}

	public void setNameOfCompany(String nameOfCompany) {
		this.nameOfCompany = nameOfCompany;
	}

	public int getAdjustHr() {
		return adjustHr;
	}

	public void setAdjustHr(int adjustHr) {
		this.adjustHr = adjustHr;
	}

	public int getAdjustKm() {
		return adjustKm;
	}

	public void setAdjustKm(int adjustKm) {
		this.adjustKm = adjustKm;
	}
}
