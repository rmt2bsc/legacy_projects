package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_state_country database table/view.
 *
 * @author auto generated.
 */
public class VwStateCountry extends OrmBean {




	// Property name constants that belong to respective DataSource, VwStateCountryView

/** The property name constant equivalent to property, StateId, of respective DataSource view. */
  public static final String PROP_STATEID = "StateId";
/** The property name constant equivalent to property, StateCode, of respective DataSource view. */
  public static final String PROP_STATECODE = "StateCode";
/** The property name constant equivalent to property, CountryId, of respective DataSource view. */
  public static final String PROP_COUNTRYID = "CountryId";
/** The property name constant equivalent to property, StateName, of respective DataSource view. */
  public static final String PROP_STATENAME = "StateName";
/** The property name constant equivalent to property, SttVoidInd, of respective DataSource view. */
  public static final String PROP_STTVOIDIND = "SttVoidInd";
/** The property name constant equivalent to property, CountryName, of respective DataSource view. */
  public static final String PROP_COUNTRYNAME = "CountryName";



	/** The javabean property equivalent of database column vw_state_country.state_id */
  private int stateId;
/** The javabean property equivalent of database column vw_state_country.state_code */
  private String stateCode;
/** The javabean property equivalent of database column vw_state_country.country_id */
  private int countryId;
/** The javabean property equivalent of database column vw_state_country.state_name */
  private String stateName;
/** The javabean property equivalent of database column vw_state_country.stt_void_ind */
  private String sttVoidInd;
/** The javabean property equivalent of database column vw_state_country.country_name */
  private String countryName;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwStateCountry() throws SystemException {
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
 * Sets the value of member variable stateCode
 *
 * @author auto generated.
 */
  public void setStateCode(String value) {
    this.stateCode = value;
  }
/**
 * Gets the value of member variable stateCode
 *
 * @author atuo generated.
 */
  public String getStateCode() {
    return this.stateCode;
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
 * Sets the value of member variable countryName
 *
 * @author auto generated.
 */
  public void setCountryName(String value) {
    this.countryName = value;
  }
/**
 * Gets the value of member variable countryName
 *
 * @author atuo generated.
 */
  public String getCountryName() {
    return this.countryName;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}