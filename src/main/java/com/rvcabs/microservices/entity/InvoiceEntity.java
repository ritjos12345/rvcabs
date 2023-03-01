package com.rvcabs.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "invoice")
public class InvoiceEntity extends BaseEntity<String> {

	/**
	 * 
	 */
	@Column
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", nullable = false, length = 100)
	private String id;

	@Column
	private String invoiceNo;

	@Column
	private String invoiceDate;

	@Column
	private String toCompanyName;

	@Column
	private String fromCompanyName;

	@Column
	private String toCompanyAddress;

	@Column
	private String fromcompanyAddress;

	@Column
	private String toCompanyState;

	@Column
	private String fromCompanyState;

	@Column
	private String toGstNo;

	@Column
	private String fromGstNo;

	@Column
	private String toStateCode;

	@Column
	private String fromStateCode;

	@Column
	private String fromPANNo;

	@Column
	private String fromSACCode;

	@Column
	private String fromVendorCode;

	@Column
	private String desOfService;

	@Column
	private String durationOfInvoce;

	@Column
	private String sGSTUTGS;

	@Column
	private String cGST;

	@Column
	private float iGSTAmt;
	
	@Column
	private float sGSTUTGSAmt;

	@Column
	private float cGSTAmt;

	@Column
	private String iGST;

	@Column
	private float billWitoutGST;

	@Column
	private float billWitGST;

	@Column
	private String totalBillingInWords;
	
	@Column
	private String invoiceURL;
	
	@Column
	private String billingIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getInvoiceURL() {
		return invoiceURL;
	}

	public void setInvoiceURL(String invoiceURL) {
		this.invoiceURL = invoiceURL;
	}

	public String getBillingIds() {
		return billingIds;
	}

	public void setBillingIds(String billingIds) {
		this.billingIds = billingIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
