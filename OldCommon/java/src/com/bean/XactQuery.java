package com.bean;


import com.bean.OrmBean;
import com.util.SystemException;


public class XactQuery extends OrmBean {

  private String xactDate1;
  private String xactDate2;
  private double xactAmount1;
  private double xactAmount2;
  private int xactTypeId;
  private String xactDate1Op;
  private String xactDate2Op;
  private String xactAmount1Op;
  private String xactAmount2Op;
  private String relOp2;
  private int xactCatgId;
  private String xactReason;
  private String xactCustomerNo;
  private String xactCustomerName;
  private String xactCreditorNo;
  private String xactCreditorName;
  

	// Getter/Setter Methods

  public XactQuery() throws SystemException {
  }
  
  public static XactQuery getInstance() {
      try {
          return new XactQuery();
      }
      catch (SystemException e) {
          return null;
      }
  }

  
  
  public void setXactDate1(String value) {
      this.xactDate1 = value;
  }
  public String getXactDate1() {
      return this.xactDate1;
  }
  public void setXactDate2(String value) {
      this.xactDate2 = value;
  }
  public String getXactDate2() {
      return this.xactDate2;
  }  
  public void setXactAmount1(double value) {
      this.xactAmount1 = value;
  }
  public double getXactAmount1() {
      return this.xactAmount1;
  }
  public void setXactAmount2(double value) {
      this.xactAmount2 = value;
  }
  public double getXactAmount2() {
      return this.xactAmount2;
  }
  public void setXactTypeId(int value) {
      this.xactTypeId = value;
  }
  public int getXactTypeId() {
      return this.xactTypeId;
  }
  public void setXactDate1Op(String value) {
      this.xactDate1Op = value;
  }
  public String getXactDate1Op() {
      return this.xactDate1Op;
  }
  public void setXactDate2Op(String value) {
      this.xactDate2Op = value;
  }
  public String getXactDate2Op() {
      return this.xactDate2Op;
  }
  public void setXactAmount1Op(String value) {
      this.xactAmount1Op = value;
  }
  public String getXactAmount1Op() {
      return this.xactAmount1Op;
  }
  public void setXactAmount2Op(String value) {
      this.xactAmount2Op = value;
  }
  public String getXactAmount2Op() {
      return this.xactAmount2Op;
  }
  public int getXactCatgId() {
      return this.xactCatgId;
  }
  public void setXactCatgId(int value) {
      this.xactCatgId = value;
  }
  public void setXactReason(String value) {
      this.xactReason = value;
  }
  public String getXactReason() {
      return this.xactReason;
  }
  public void setXactCustomerNo(String value) {
      this.xactCustomerNo = value;
  }
  public String getXactCustomerNo() {
      return this.xactCustomerNo;
  }
  public void setXactCustomerName(String value) {
      this.xactCustomerName = value;
  }
  public String getXactCustomerName() {
      return this.xactCustomerName;
  }
  public void setXactCreditorNo(String value) {
      this.xactCreditorNo = value;
  }
  public String getXactCreditorNo() {
      return this.xactCreditorNo;
  }
  public void setXactCreditorName(String value) {
      this.xactCreditorName = value;
  }
  public String getXactCreditorName() {
      return this.xactCreditorName;
  }

  public void initBean() throws SystemException {}
}