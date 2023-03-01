package com.rvcabs.microservices.dto;

import java.io.Serializable;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@Setter
@Getter
public class InvoiceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String invoiceNo;

	private String invoiceDate;

	private String toCompanyName;

	private String fromCompanyName="RIGVED Technologies Private Ltd";

	private String toCompanyAddress;

	private String fromcompanyAddress;

	private String toCompanyState;

	private String fromCompanyState="Maharashtra";

	private String toGstNo;

	private String fromGstNo="27AAECR1228G1ZL";

	private String toStateCode;

	private String fromStateCode="27";

	private String fromPANNo="AAECR1228G";

	private String fromSACCode="996601";

	private String fromVendorCode="397584";

	private String desOfService;

	private String durationOfInvoce;

	private String sGSTUTGS;

	private String cGST;

	private String iGST;

	private float billWitoutGST;

	private float billWitGST;
	
	private String totalBillingInWords;

	private float iGSTAmt;
	
	private float sGSTUTGSAmt;

	private float cGSTAmt;

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getToCompanyName() {
		return toCompanyName;
	}

	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}

	public String getFromCompanyName() {
		return fromCompanyName;
	}

	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}

	public String getToCompanyAddress() {
		return toCompanyAddress;
	}

	public void setToCompanyAddress(String toCompanyAddress) {
		this.toCompanyAddress = toCompanyAddress;
	}

	public String getFromcompanyAddress() {
		return fromcompanyAddress;
	}

	public void setFromcompanyAddress(String fromcompanyAddress) {
		this.fromcompanyAddress = fromcompanyAddress;
	}

	public String getToCompanyState() {
		return toCompanyState;
	}

	public void setToCompanyState(String toCompanyState) {
		this.toCompanyState = toCompanyState;
	}

	public String getFromCompanyState() {
		return fromCompanyState;
	}

	public void setFromCompanyState(String fromCompanyState) {
		this.fromCompanyState = fromCompanyState;
	}

	public String getToGstNo() {
		return toGstNo;
	}

	public void setToGstNo(String toGstNo) {
		this.toGstNo = toGstNo;
	}

	public String getFromGstNo() {
		return fromGstNo;
	}

	public void setFromGstNo(String fromGstNo) {
		this.fromGstNo = fromGstNo;
	}

	public String getToStateCode() {
		return toStateCode;
	}

	public void setToStateCode(String toStateCode) {
		this.toStateCode = toStateCode;
	}

	public String getFromStateCode() {
		return fromStateCode;
	}

	public void setFromStateCode(String fromStateCode) {
		this.fromStateCode = fromStateCode;
	}

	public String getFromPANNo() {
		return fromPANNo;
	}

	public void setFromPANNo(String fromPANNo) {
		this.fromPANNo = fromPANNo;
	}

	public String getFromSACCode() {
		return fromSACCode;
	}

	public void setFromSACCode(String fromSACCode) {
		this.fromSACCode = fromSACCode;
	}

	public String getFromVendorCode() {
		return fromVendorCode;
	}

	public void setFromVendorCode(String fromVendorCode) {
		this.fromVendorCode = fromVendorCode;
	}

	public String getDesOfService() {
		return desOfService;
	}

	public void setDesOfService(String desOfService) {
		this.desOfService = desOfService;
	}

	public String getDurationOfInvoce() {
		return durationOfInvoce;
	}

	public void setDurationOfInvoce(String durationOfInvoce) {
		this.durationOfInvoce = durationOfInvoce;
	}

	public String getsGSTUTGS() {
		return sGSTUTGS;
	}

	public void setsGSTUTGS(String sGSTUTGS) {
		this.sGSTUTGS = sGSTUTGS;
	}

	public String getcGST() {
		return cGST;
	}

	public void setcGST(String cGST) {
		this.cGST = cGST;
	}

	public String getiGST() {
		return iGST;
	}

	public void setiGST(String iGST) {
		this.iGST = iGST;
	}

	public float getBillWitoutGST() {
		return billWitoutGST;
	}

	public void setBillWitoutGST(float billWitoutGST) {
		this.billWitoutGST = billWitoutGST;
	}

	public float getBillWitGST() {
		return billWitGST;
	}

	public void setBillWitGST(float billWitGST) {
		this.billWitGST = billWitGST;
	}

	public String getTotalBillingInWords() {
		return totalBillingInWords;
	}

	public void setTotalBillingInWords(String totalBillingInWords) {
		this.totalBillingInWords = totalBillingInWords;
	}

	public float getiGSTAmt() {
		return iGSTAmt;
	}

	public void setiGSTAmt(float iGSTAmt) {
		this.iGSTAmt = iGSTAmt;
	}

	public float getsGSTUTGSAmt() {
		return sGSTUTGSAmt;
	}

	public void setsGSTUTGSAmt(float sGSTUTGSAmt) {
		this.sGSTUTGSAmt = sGSTUTGSAmt;
	}

	public float getcGSTAmt() {
		return cGSTAmt;
	}

	public void setcGSTAmt(float cGSTAmt) {
		this.cGSTAmt = cGSTAmt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
