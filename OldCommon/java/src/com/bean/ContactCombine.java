package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


public class ContactCombine extends OrmBean {

  private String contactType;
  private int personId;
  private String perFirstname;
  private String perMidname;
  private String perLastname;
  private String perMaidenname;
  private String perGeneration;
  private String perShortname;
  private int perTitle;
  private int perGenderId;
  private int perMaritalStatus;
  private java.util.Date perBirthDate;
  private int perRaceId;
  private String perSsn;
  private String perEmail;
  private int businessId;
  private int busType;
  private int busServType;
  private String busLongname;
  private String busShortname;
  private String busContactFirstname;
  private String busContactLastname;
  private String busContactPhone;
  private String busContactExt;
  private String busTaxId;
  private String busWebsite;
  private int addrId;
  private String addr1;
  private String addr2;
  private String addr3;
  private String addr4;
  private int addrZip;
  private String addrZipext;
  private String addrPhoneHome;
  private String addrPhoneWork;
  private String addrPhoneExt;
  private String addrPhoneMain;
  private String addrPhoneCell;
  private String addrPhoneFax;
  private String addrPhonePager;
  private String addrCity;
  private String addrState;



	// Getter/Setter Methods

  public ContactCombine() throws SystemException {
  }
  public void setContactType(String value) {
    this.contactType = value;
  }
  public String getContactType() {
    return (this.contactType == null ? "" : this.contactType);
  }
  public void setPersonId(int value) {
    this.personId = value;
  }
  public int getPersonId() {
    return this.personId;
  }
  public void setPerFirstname(String value) {
    this.perFirstname = value;
  }
  public String getPerFirstname() {
    return (this.perFirstname == null ? "" : this.perFirstname);
  }
  public void setPerMidname(String value) {
    this.perMidname = value;
  }
  public String getPerMidname() {
    return (this.perMidname == null ? "" : this.perMidname);
  }
  public void setPerLastname(String value) {
    this.perLastname = value;
  }
  public String getPerLastname() {
		return (this.perLastname == null ? "" : this.perLastname);
  }
  public void setPerMaidenname(String value) {
    this.perMaidenname = value;
  }
  public String getPerMaidenname() {
		return (this.perMaidenname == null ? "" : this.perMaidenname);
  }
  public void setPerGeneration(String value) {
    this.perGeneration = value;
  }
  public String getPerGeneration() {
		return (this.perGeneration == null ? "" : this.perGeneration);
  }
  public void setPerShortname(String value) {
    this.perShortname = value;
  }
  public String getPerShortname() {
		return (this.perShortname == null ? "" : this.perShortname);
  }
  public void setPerTitle(int value) {
    this.perTitle = value;
  }
  public int getPerTitle() {
    return this.perTitle;
  }
  public void setPerGenderId(int value) {
    this.perGenderId = value;
  }
  public int getPerGenderId() {
    return this.perGenderId;
  }
  public void setPerMaritalStatus(int value) {
    this.perMaritalStatus = value;
  }
  public int getPerMaritalStatus() {
    return this.perMaritalStatus;
  }
  public void setPerBirthDate(java.util.Date value) {
    this.perBirthDate = value;
  }
  public java.util.Date getPerBirthDate() {
    return this.perBirthDate;
  }
  public void setPerRaceId(int value) {
    this.perRaceId = value;
  }
  public int getPerRaceId() {
    return this.perRaceId;
  }
  public void setPerSsn(String value) {
    this.perSsn = value;
  }
  public String getPerSsn() {
		return (this.perSsn == null ? "" : this.perSsn);
  }
  public void setPerEmail(String value) {
    this.perEmail = value;
  }
  public String getPerEmail() {
		return (this.perEmail == null ? "" : this.perEmail);
  }
  public void setBusinessId(int value) {
    this.businessId = value;
  }
  public int getBusinessId() {
    return this.businessId;
  }
  public void setBusType(int value) {
    this.busType = value;
  }
  public int getBusType() {
    return this.busType;
  }
  public void setBusServType(int value) {
    this.busServType = value;
  }
  public int getBusServType() {
    return this.busServType;
  }
  public void setBusLongname(String value) {
    this.busLongname = value;
  }
  public String getBusLongname() {
		return (this.busLongname == null ? "" : this.busLongname);
  }
  public void setBusShortname(String value) {
    this.busShortname = value;
  }
  public String getBusShortname() {
		return (this.busShortname == null ? "" : this.busShortname);
  }
  public void setBusContactFirstname(String value) {
    this.busContactFirstname = value;
  }
  public String getBusContactFirstname() {
		return (this.busContactFirstname == null ? "" : this.busContactFirstname);
  }
  public void setBusContactLastname(String value) {
    this.busContactLastname = value;
  }
  public String getBusContactLastname() {
		return (this.busContactLastname == null ? "" : this.busContactLastname);
  }
  public void setBusContactPhone(String value) {
    this.busContactPhone = value;
  }
  public String getBusContactPhone() {
		return (this.busContactPhone == null ? "" : this.busContactPhone);
  }
  public void setBusContactExt(String value) {
    this.busContactExt = value;
  }
  public String getBusContactExt() {
		return (this.busContactExt == null ? "" : this.busContactExt);
  }
  public void setBusTaxId(String value) {
    this.busTaxId = value;
  }
  public String getBusTaxId() {
		return (this.busTaxId == null ? "" : this.busTaxId);
  }
  public void setBusWebsite(String value) {
    this.busWebsite = value;
  }
  public String getBusWebsite() {
		return (this.busWebsite == null ? "" : this.busWebsite);
  }
  public void setAddrId(int value) {
    this.addrId = value;
  }
  public int getAddrId() {
    return this.addrId;
  }
  public void setAddr1(String value) {
    this.addr1 = value;
  }
  public String getAddr1() {
		return (this.addr1 == null ? "" : this.addr1);
  }
  public void setAddr2(String value) {
    this.addr2 = value;
  }
  public String getAddr2() {
    return (this.addr2 == null ? "" : this.addr2);
  }
  public void setAddr3(String value) {
    this.addr3 = value;
  }
  public String getAddr3() {
    return (this.addr3 == null ? "" : this.addr3);
  }
  public void setAddr4(String value) {
    this.addr4 = value;
  }
  public String getAddr4() {
    return (this.addr4 == null ? "" : this.addr4);
  }
  public void setAddrZip(int value) {
    this.addrZip = value;
  }
  public int getAddrZip() {
		return this.addrZip;
  }
  public void setAddrZipext(String value) {
    this.addrZipext = value;
  }
  public String getAddrZipext() {
		return (this.addrZipext == null ? "" : this.addrZipext);
  }
  public void setAddrPhoneHome(String value) {
    this.addrPhoneHome = value;
  }
  public String getAddrPhoneHome() {
		return (this.addrPhoneHome == null ? "" : this.addrPhoneHome);
  }
  public void setAddrPhoneWork(String value) {
    this.addrPhoneWork = value;
  }
  public String getAddrPhoneWork() {
		return (this.addrPhoneWork == null ? "" : this.addrPhoneWork);
  }
  public void setAddrPhoneExt(String value) {
    this.addrPhoneExt = value;
  }
  public String getAddrPhoneExt() {
		return (this.addrPhoneExt == null ? "" : this.addrPhoneExt);
  }
  public void setAddrPhoneMain(String value) {
    this.addrPhoneMain = value;
  }
  public String getAddrPhoneMain() {
		return (this.addrPhoneMain == null ? "" : this.addrPhoneMain);
  }
  public void setAddrPhoneCell(String value) {
    this.addrPhoneCell = value;
  }
  public String getAddrPhoneCell() {
		return (this.addrPhoneCell == null ? "" : this.addrPhoneCell);
  }
  public void setAddrPhoneFax(String value) {
    this.addrPhoneFax = value;
  }
  public String getAddrPhoneFax() {
		return (this.addrPhoneFax == null ? "" : this.addrPhoneFax);
  }
  public void setAddrPhonePager(String value) {
    this.addrPhonePager = value;
  }
  public String getAddrPhonePager() {
		return (this.addrPhonePager == null ? "" : this.addrPhonePager);
  }
  public void setAddrCity(String value) {
    this.addrCity = value;
  }
  public String getAddrCity() {
		return (this.addrCity == null ? "" : this.addrCity);
  }
  public void setAddrState(String value) {
    this.addrState = value;
  }
  public String getAddrState() {
		return (this.addrState == null ? "" : this.addrState);
  }
  public void initBean() throws SystemException {}
}