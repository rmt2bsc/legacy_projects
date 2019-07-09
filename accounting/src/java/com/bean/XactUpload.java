package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_upload database table/view.
 *
 * @author auto generated.
 */
public class XactUpload extends OrmBean {




	// Property name constants that belong to respective DataSource, XactUploadView

/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";
/** The property name constant equivalent to property, Date, of respective DataSource view. */
  public static final String PROP_DATE = "Date";
/** The property name constant equivalent to property, Descr, of respective DataSource view. */
  public static final String PROP_DESCR = "Descr";
/** The property name constant equivalent to property, Amt, of respective DataSource view. */
  public static final String PROP_AMT = "Amt";



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
 * @author auto generated.
 */
  public XactUpload() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable code
 *
 * @author auto generated.
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author atuo generated.
 */
  public String getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable date
 *
 * @author auto generated.
 */
  public void setDate(java.util.Date value) {
    this.date = value;
  }
/**
 * Gets the value of member variable date
 *
 * @author atuo generated.
 */
  public java.util.Date getDate() {
    return this.date;
  }
/**
 * Sets the value of member variable descr
 *
 * @author auto generated.
 */
  public void setDescr(String value) {
    this.descr = value;
  }
/**
 * Gets the value of member variable descr
 *
 * @author atuo generated.
 */
  public String getDescr() {
    return this.descr;
  }
/**
 * Sets the value of member variable amt
 *
 * @author auto generated.
 */
  public void setAmt(double value) {
    this.amt = value;
  }
/**
 * Gets the value of member variable amt
 *
 * @author atuo generated.
 */
  public double getAmt() {
    return this.amt;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}