package com.rvcabs.microservices.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "travelbill")
public class BillingEntity extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7008826736366743794L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@Column
	private String companyIds;

	@Temporal(TemporalType.DATE)
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	private Date toDate;

	@Column
	private String costCentre;

	@Column
	private String travelId;

	@Column
	private String empNo;

	@Column
	private String gstNo;

	@Column
	private String empName;

	@Column
	private Long dutySlip;

	@Temporal(TemporalType.DATE)
	private Date dutySlipDate;

	@Column
	private String typeOfDuty;

	@Column
	private String vehicleSeg;

	@Column
	private String vehicleType;

	@Column
	private String basic;

	@Column
	private String carNo;

	@Column
	private String openingKM;

	@Column
	private String closingKM;

	@Column
	private String perKmRate;

	@Column
	private String totalKm;

	@Column
	private Float totalKmAmt;

	@Temporal(TemporalType.TIME)
	private Date pickUpTime;

	@Temporal(TemporalType.TIME)
	private Date pickDropTime;

	@Column
	private Float perHrRate;

	@Column
	private String totalHr;

	@Column
	private Float hidenHr;

	@Column
	private Integer totalHrAmt;

	@Column
	private Float tolllPark;

	@Column
	private Float naDa;

	@Column
	private Float totalTripBill;

	@Column
	private String nameOfCompany;

	@Column
	private String bookingId;

	@Column
	private int adjustHr;

	@Column
	private int adjustKm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
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

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
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

	public Float getTolllPark() {
		return tolllPark;
	}

	public void setTolllPark(Float tolllPark) {
		this.tolllPark = tolllPark;
	}

	public Float getNaDa() {
		return naDa;
	}

	public void setNaDa(Float naDa) {
		this.naDa = naDa;
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

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
