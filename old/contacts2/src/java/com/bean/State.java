package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the state database table/view.
 *
 * @author auto generated.
 */
public class State extends OrmBean {




	// Property name constants that belong to respective DataSource, StateView

/** The property name constant equivalent to property, StateId, of respective DataSource view. */
  public static final String PROP_STATEID = "StateId";
/** The property name constant equivalent to property, AbbrCode, of respective DataSource view. */
  public static final String PROP_ABBRCODE = "AbbrCode";
/** The property name constant equivalent to property, CountryId, of respective DataSource view. */
  public static final String PROP_COUNTRYID = "CountryId";
/** The property name constant equivalent to property, StateName, of respective DataSource view. */
  public static final String PROP_STATENAME = "StateName";
/** The property name constant equivalent to property, SttVoidInd, of respective DataSource view. */
  public static final String PROP_STTVOIDIND = "SttVoidInd";



	/** The javabean property equivalent of database column state.state_id */
  private int stateId;
/** The javabean property equivalent of database column state.abbr_code */
  private String abbrCode;
/** The javabean property equivalent of database column state.country_id */
  private int countryId;
/** The javabean property equivalent of database column state.state_name */
  private String stateName;
/** The javabean property equivalent of database column state.stt_void_ind */
  private String sttVoidInd;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public State() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable stateId
 *
 * @author auto generated.
 */
  public void setStateId(int value) {
    this.stateId = value;
  }
/**
 * Gets the value of member variable stateId
 *
 * @author atuo generated.
 */
  public int getStateId() {
    return this.stateId;
  }
/**
 * Sets the value of member variable abbrCode
 *
 * @author auto generated.
 */
  public void setAbbrCode(String value) {
    this.abbrCode = value;
  }
/**
 * Gets the value of member variable abbrCode
 *
 * @author atuo generated.
 */
  public String getAbbrCode() {
    return this.abbrCode;
  }
/**
 * Sets the value of member variable countryId
 *
 * @author auto generated.
 */
  public void setCountryId(int value) {
    this.countryId = value;
  }
/**
 * Gets the value of member variable countryId
 *
 * @author atuo generated.
 */
  public int getCountryId() {
    return this.countryId;
  }
/**
 * Sets the value of member variable stateName
 *
 * @author auto generated.
 */
  public void setStateName(String value) {
    this.stateName = value;
  }
/**
 * Gets the value of member variable stateName
 *
 * @author atuo generated.
 */
  public String getStateName() {
    return this.stateName;
  }
/**
 * Sets the value of member variable sttVoidInd
 *
 * @author auto generated.
 */
  public void setSttVoidInd(String value) {
    this.sttVoidInd = value;
  }
/**
 * Gets the value of member variable sttVoidInd
 *
 * @author atuo generated.
 */
  public String getSttVoidInd() {
    return this.sttVoidInd;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}