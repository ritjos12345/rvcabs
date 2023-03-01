package com.rvcabs.microservices.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FillterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> dutyTypes;
	private List<String> companyNames;
	private List<String> vechicleSeg;
	private List<String> vechicleType;
	private List<String> gstNum;
	private List<String> costCenter;
	private List<String> employeeIds;
	private List<String> travelIds;
	private String startDate;
	private String endDate;
	private String accountId;
	private String vechNum;
	public List<String> getDutyTypes() {
		return dutyTypes;
	}
	public void setDutyTypes(List<String> dutyTypes) {
		this.dutyTypes = dutyTypes;
	}
	public List<String> getCompanyNames() {
		return companyNames;
	}
	public void setCompanyNames(List<String> companyNames) {
		this.companyNames = companyNames;
	}
	public List<String> getVechicleSeg() {
		return vechicleSeg;
	}
	public void setVechicleSeg(List<String> vechicleSeg) {
		this.vechicleSeg = vechicleSeg;
	}
	public List<String> getVechicleType() {
		return vechicleType;
	}
	public void setVechicleType(List<String> vechicleType) {
		this.vechicleType = vechicleType;
	}
	public List<String> getGstNum() {
		return gstNum;
	}
	public void setGstNum(List<String> gstNum) {
		this.gstNum = gstNum;
	}
	public List<String> getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(List<String> costCenter) {
		this.costCenter = costCenter;
	}
	public List<String> getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(List<String> employeeIds) {
		this.employeeIds = employeeIds;
	}
	public List<String> getTravelIds() {
		return travelIds;
	}
	public void setTravelIds(List<String> travelIds) {
		this.travelIds = travelIds;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getVechNum() {
		return vechNum;
	}
	public void setVechNum(String vechNum) {
		this.vechNum = vechNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
