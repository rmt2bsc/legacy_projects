package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_codes database table/view.
 *
 * @author auto generated.
 */
public class XactCodes extends OrmBean {




	// Property name constants that belong to respective DataSource, XactCodesView

/** The property name constant equivalent to property, XactCodeId, of respective DataSource view. */
  public static final String PROP_XACTCODEID = "XactCodeId";
/** The property name constant equivalent to property, XactCodeGrpId, of respective DataSource view. */
  public static final String PROP_XACTCODEGRPID = "XactCodeGrpId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column xact_codes.xact_code_id */
  private int xactCodeId;
/** The javabean property equivalent of database column xact_codes.xact_code_grp_id */
  private int xactCodeGrpId;
/** The javabean property equivalent of database column xact_codes.description */
  private String description;
/** The javabean property equivalent of database column xact_codes.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact_codes.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact_codes.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public XactCodes() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable xactCodeId
 *
 * @author auto generated.
 */
  public void setXactCodeId(int value) {
    this.xactCodeId = value;
  }
/**
 * Gets the value of member variable xactCodeId
 *
 * @author atuo generated.
 */
  public int getXactCodeId() {
    return this.xactCodeId;
  }
/**
 * Sets the value of member variable xactCodeGrpId
 *
 * @author auto generated.
 */
  public void setXactCodeGrpId(int value) {
    this.xactCodeGrpId = value;
  }
/**
 * Gets the value of member variable xactCodeGrpId
 *
 * @author atuo generated.
 */
  public int getXactCodeGrpId() {
    return this.xactCodeGrpId;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author auto generated.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author auto generated.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author auto generated.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author atuo generated.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}