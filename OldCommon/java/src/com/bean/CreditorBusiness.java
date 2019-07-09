package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


public class CreditorBusiness extends OrmBean {

  private int id;
  private int glAccountId;
  private int businessId;
  private int creditorTypeId;
  private String accountNumber;
  private double creditLimit;
  private double apr;
  private int active;
  private java.util.Date dateCreated;
  private java.util.Date dateUpdated;
  private String userId;
	private int busType;
	private int servType;
	private String longname;
	private String shortname;
	private String contactFirstname;
	private String contactLastname;
	private String contactPhone;
	private String contactExt;
	private String taxId;
  private String website;
  private String creditorTypeDescription;



	// Getter/Setter Methods

  public CreditorBusiness() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
  public int getGlAccountId() {
    return this.glAccountId;
  }
  public void setBusinessId(int value) {
    this.businessId = value;
  }
  public int getBusinessId() {
    return this.businessId;
  }
  public void setCreditorTypeId(int value) {
    this.creditorTypeId = value;
  }
  public int getCreditorTypeId() {
    return this.creditorTypeId;
  }
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }
  public String getAccountNumber() {
    return this.accountNumber;
  }
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
  public double getCreditLimit() {
    return this.creditLimit;
  }
  public void setApr(double value) {
    this.apr = value;
  }
  public double getApr() {
    return this.apr;
  }
  public void setActive(int value) {
    this.active = value;
  }
  public int getActive() {
    return this.active;
  }
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
  public void setUserId(String value) {
    this.userId = value;
  }
  public String getUserId() {
    return this.userId;
  }
  public void setBusType(int value) {
    this.busType = value;
  }
  public int getBusType() {
    return this.busType;
  }
  public void setServType(int value) {
    this.servType = value;
  }
  public int getServType() {
    return this.servType;
  }
  public void setLongname(String value) {
    this.longname = value;
  }
  public String getLongname() {
    return this.longname;
  }
  public void setShortname(String value) {
    this.shortname = value;
  }
  public String getShortname() {
    return this.shortname;
  }
  public void setContactFirstname(String value) {
    this.contactFirstname = value;
  }
  public String getContactFirstname() {
    return this.contactFirstname;
  }
  public void setContactLastname(String value) {
    this.contactLastname = value;
  }
  public String getContactLastname() {
    return this.contactLastname;
  }
  public void setContactPhone(String value) {
    this.contactPhone = value;
  }
  public String getContactPhone() {
    return this.contactPhone;
  }
  public void setContactExt(String value) {
    this.contactExt = value;
  }
  public String getContactExt() {
    return this.contactExt;
  }
  public void setTaxId(String value) {
    this.taxId = value;
  }
  public String getTaxId() {
    return this.taxId;
  }
  public void setWebsite(String value) {
			this.website = value;
	}
	public String getWebsite() {
			return this.website;
	}

  public String getCreditorTypeDescription() {
		return this.creditorTypeDescription;
	}
	public void setCreditorTypeDescription(String value) {
		this.creditorTypeDescription = value;
	}
  public void initBean() throws SystemException {}
}