package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


public class User extends OrmBean {

  private int id;
  private String login;
  private String description;
  private String password;
  private int totalLogons;
  private int active;
  private int employeeId;
  private java.util.Date startDate;
  private java.util.Date terminationDate;
  private int personId;
  private String firstname;
  private String midname;
  private String lastname;
  private String maidenname;
  private String generation;
  private String shortname;
  private int title;
  private int genderId;
  private int maritalStatus;
  private java.util.Date birthDate;
  private int raceId;
  private String ssn;
  private String email;
  private int titleId;
  private String titleDescription;
  private int typeId;
  private String typeDescription;
  private int managerId;
  private double billRate;
  private double otBillRate;



	// Getter/Setter Methods

  public User() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }

  public void setLogin(String value) {
    this.login = value;
  }
  public String getLogin() {
    return this.login;
  }
  public void setDescription(String value) {
    this.description = value;
  }
  public String getDescription() {
    return this.description;
  }
  public void setPassword(String value) {
    this.password = value;
  }
  public String getPassword() {
    return this.password;
  }
  public void setTotalLogons(int value) {
    this.totalLogons = value;
  }
  public int getTotalLogons() {
    return this.totalLogons;
  }
  public void setActive(int value) {
    this.active = value;
  }
  public int getActive() {
    return this.active;
  }

  public void setEmployeeId(int value) {
    this.employeeId = value;
  }
  public int getEmployeeId() {
    return this.employeeId;
  }
  public void setStartDate(java.util.Date value) {
    this.startDate = value;
  }
  public java.util.Date getStartDate() {
    return this.startDate;
  }
  public void setTerminationDate(java.util.Date value) {
    this.terminationDate = value;
  }
  public java.util.Date getTerminationDate() {
    return this.terminationDate;
  }

  public void setPersonId(int value) {
    this.personId = value;
  }
  public int getPersonId() {
    return this.personId;
  }
  public void setFirstname(String value) {
    this.firstname = value;
  }
  public String getFirstname() {
    return this.firstname;
  }
  public void setMidname(String value) {
    this.midname = value;
  }
  public String getMidname() {
    return this.midname;
  }
  public void setLastname(String value) {
    this.lastname = value;
  }
  public String getLastname() {
    return this.lastname;
  }
  public void setMaidenname(String value) {
    this.maidenname = value;
  }
  public String getMaidenname() {
    return this.maidenname;
  }
  public void setGeneration(String value) {
    this.generation = value;
  }
  public String getGeneration() {
    return this.generation;
  }
  public void setShortname(String value) {
    this.shortname = value;
  }
  public String getShortname() {
    return this.shortname;
  }
  public void setTitle(int value) {
    this.title = value;
  }
  public int getTitle() {
    return this.title;
  }
  public void setGenderId(int value) {
    this.genderId = value;
  }
  public int getGenderId() {
    return this.genderId;
  }
  public void setMaritalStatus(int value) {
    this.maritalStatus = value;
  }
  public int getMaritalStatus() {
    return this.maritalStatus;
  }
  public void setBirthDate(java.util.Date value) {
    this.birthDate = value;
  }
  public java.util.Date getBirthDate() {
    return this.birthDate;
  }
  public void setRaceId(int value) {
    this.raceId = value;
  }
  public int getRaceId() {
    return this.raceId;
  }
  public void setSsn(String value) {
    this.ssn = value;
  }
  public String getSsn() {
    return this.ssn;
  }
  public void setEmail(String value) {
    this.email = value;
  }
  public String getEmail() {
    return this.email;
  }

  public void setTitleId(int value) {
    this.titleId = value;
  }
  public int getTitleId() {
    return this.titleId;
  }
  public void setTitleDescription(String value) {
    this.titleDescription = value;
  }
  public String getTitleDescription() {
    return this.titleDescription;
  }

  public void setTypeId(int value) {
    this.typeId = value;
  }
  public int getTypeId() {
    return this.typeId;
  }
  public void setTypeDescription(String value) {
    this.typeDescription = value;
  }
  public String getTypeDescription() {
    return this.typeDescription;
  }
  public void setManagerId(int value) {
      this.managerId = value;
  }
  public int getManagerId() {
      return this.managerId;
  }
  public double getBillRate() {
      return this.billRate;
  }
  public void setBillRate(double value) {
      this.billRate = value;
  }
  public double getOtBillRate() {
      return this.otBillRate;
  }
  public void setOtBillRate(double value) {
      this.otBillRate = value;
  }
  public void initBean() throws SystemException {}


}