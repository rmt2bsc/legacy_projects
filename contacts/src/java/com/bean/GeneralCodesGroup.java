package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the general_codes_group database table/view.
 *
 * @author auto generated.
 */
public class GeneralCodesGroup extends OrmBean {




	// Property name constants that belong to respective DataSource, GeneralCodesGroupView

/** The property name constant equivalent to property, CodeGrpId, of respective DataSource view. */
  public static final String PROP_CODEGRPID = "CodeGrpId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, Permcol, of respective DataSource view. */
  public static final String PROP_PERMCOL = "Permcol";



	/** The javabean property equivalent of database column general_codes_group.code_grp_id */
  private int codeGrpId;
/** The javabean property equivalent of database column general_codes_group.description */
  private String description;
/** The javabean property equivalent of database column general_codes_group.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column general_codes_group.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column general_codes_group.user_id */
  private String userId;
/** The javabean property equivalent of database column general_codes_group.permcol */
  private String permcol;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public GeneralCodesGroup() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable codeGrpId
 *
 * @author auto generated.
 */
  public void setCodeGrpId(int value) {
    this.codeGrpId = value;
  }
/**
 * Gets the value of member variable codeGrpId
 *
 * @author atuo generated.
 */
  public int getCodeGrpId() {
    return this.codeGrpId;
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
 * Sets the value of member variable permcol
 *
 * @author auto generated.
 */
  public void setPermcol(String value) {
    this.permcol = value;
  }
/**
 * Gets the value of member variable permcol
 *
 * @author atuo generated.
 */
  public String getPermcol() {
    return this.permcol;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}