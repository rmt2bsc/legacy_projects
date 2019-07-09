package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_upload database table/view.
 *
 * @author Roy Terrell.
 */
public class XactUpload extends OrmBean {

/** The javabean property equivalent of database column xact_upload.code */
  private String code;
/** The javabean property equivalent of database column xact_upload.date */
  private java.util.Date date;
/** The javabean property equivalent of database column xact_upload.descr */
  private String descr;
/** The javabean property equivalent of database column xact_upload.amt */
  private double amt;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public XactUpload() throws SystemException {
  }
/**
 * Sets the value of member variable code
 *
 * @author Roy Terrell.
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author Roy Terrell.
 */
  public String getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable date
 *
 * @author Roy Terrell.
 */
  public void setDate(java.util.Date value) {
    this.date = value;
  }
/**
 * Gets the value of member variable date
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDate() {
    return this.date;
  }
/**
 * Sets the value of member variable descr
 *
 * @author Roy Terrell.
 */
  public void setDescr(String value) {
    this.descr = value;
  }
/**
 * Gets the value of member variable descr
 *
 * @author Roy Terrell.
 */
  public String getDescr() {
    return this.descr;
  }
/**
 * Sets the value of member variable amt
 *
 * @author Roy Terrell.
 */
  public void setAmt(double value) {
    this.amt = value;
  }
/**
 * Gets the value of member variable amt
 *
 * @author Roy Terrell.
 */
  public double getAmt() {
    return this.amt;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}