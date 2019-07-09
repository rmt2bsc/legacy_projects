package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


public class GlAccountTypeCatg extends OrmBean {

  private int id;
  private int acctTypeId;
  private int acctCatId;
  private int acctSeq;
  private String acctNo;
  private String name;
  private int balanceTypeId;
  private String description;
  private String acctTypeDescr;
  private String acctCatgDescr;
  private java.util.Date dateCreated;
  private java.util.Date dateUpdated;
  private String userId;



	// Getter/Setter Methods

  public GlAccountTypeCatg() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setAcctTypeId(int value) {
    this.acctTypeId = value;
  }
  public int getAcctTypeId() {
    return this.acctTypeId;
  }
  public void setAcctCatId(int value) {
    this.acctCatId = value;
  }
  public int getAcctCatId() {
    return this.acctCatId;
  }
  public void setAcctSeq(int value) {
    this.acctSeq = value;
  }
  public int getAcctSeq() {
    return this.acctSeq;
  }
  public void setAcctNo(String value) {
    this.acctNo = value;
  }
  public String getAcctNo() {
    return this.acctNo;
  }
  public void setName(String value) {
    this.name = value;
  }
  public String getName() {
    return this.name;
  }
  public void setBalanceTypeId(int value) {
    this.balanceTypeId = value;
  }
  public int getBalanceTypeId() {
    return this.balanceTypeId;
  }
  public void setDescription(String value) {
    this.description = value;
  }
  public String getDescription() {
    return this.description;
  }
  public void setAcctTypeDescr(String value) {
    this.acctTypeDescr = value;
  }
  public String getAcctTypeDescr() {
    return this.acctTypeDescr;
  }
  public void setAcctCatgDescr(String value) {
    this.acctCatgDescr = value;
  }
  public String getAcctCatgDescr() {
    return this.acctCatgDescr;
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

  public void initBean() throws SystemException {}
}