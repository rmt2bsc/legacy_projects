package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


public class ViewTapGrpSummary extends OrmBean {

  private int id;
  private int processed;
  private int errors;
  private int groupTotal;



	// Getter/Setter Methods

  public ViewTapGrpSummary() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setProcessed(int value) {
    this.processed = value;
  }
  public int getProcessed() {
    return this.processed;
  }
  public void setErrors(int value) {
    this.errors = value;
  }
  public int getErrors() {
    return this.errors;
  }
  public void setGroupTotal(int value) {
    this.groupTotal = value;
  }
  public int getGroupTotal() {
    return this.groupTotal;
  }
  public void initBean() throws SystemException {}
}