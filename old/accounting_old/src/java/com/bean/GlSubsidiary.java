package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_subsidiary database table/view.
 *
 * @author auto generated.
 */
public class GlSubsidiary extends OrmBean {




	// Property name constants that belong to respective DataSource, GlSubsidiaryView

/** The property name constant equivalent to property, AcctSubsidiaryId, of respective DataSource view. */
  public static final String PROP_ACCTSUBSIDIARYID = "AcctSubsidiaryId";
/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, ActivityTable, of respective DataSource view. */
  public static final String PROP_ACTIVITYTABLE = "ActivityTable";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column gl_subsidiary.acct_subsidiary_id */
  private String acctSubsidiaryId;
/** The javabean property equivalent of database column gl_subsidiary.acct_id */
  private int acctId;
/** The javabean property equivalent of database column gl_subsidiary.activity_table */
  private String activityTable;
/** The javabean property equivalent of database column gl_subsidiary.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public GlSubsidiary() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctSubsidiaryId
 *
 * @author auto generated.
 */
  public void setAcctSubsidiaryId(String value) {
    this.acctSubsidiaryId = value;
  }
/**
 * Gets the value of member variable acctSubsidiaryId
 *
 * @author atuo generated.
 */
  public String getAcctSubsidiaryId() {
    return this.acctSubsidiaryId;
  }
/**
 * Sets the value of member variable acctId
 *
 * @author auto generated.
 */
  public void setAcctId(int value) {
    this.acctId = value;
  }
/**
 * Gets the value of member variable acctId
 *
 * @author atuo generated.
 */
  public int getAcctId() {
    return this.acctId;
  }
/**
 * Sets the value of member variable activityTable
 *
 * @author auto generated.
 */
  public void setActivityTable(String value) {
    this.activityTable = value;
  }
/**
 * Gets the value of member variable activityTable
 *
 * @author atuo generated.
 */
  public String getActivityTable() {
    return this.activityTable;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}