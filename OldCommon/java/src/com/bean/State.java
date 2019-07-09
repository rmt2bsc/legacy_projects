package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the state database table/view.
 *
 * @author Roy Terrell.
 */
public class State extends OrmBean {

/** The javabean property equivalent of database column state.state_id */
  private String stateId;
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
 * @author Roy Terrell.
 */
  public State() throws SystemException {
  }
/**
 * Sets the value of member variable stateId
 *
 * @author Roy Terrell.
 */
  public void setStateId(String value) {
    this.stateId = value;
  }
/**
 * Gets the value of member variable stateId
 *
 * @author Roy Terrell.
 */
  public String getStateId() {
    return this.stateId;
  }
/**
 * Sets the value of member variable countryId
 *
 * @author Roy Terrell.
 */
  public void setCountryId(int value) {
    this.countryId = value;
  }
/**
 * Gets the value of member variable countryId
 *
 * @author Roy Terrell.
 */
  public int getCountryId() {
    return this.countryId;
  }
/**
 * Sets the value of member variable stateName
 *
 * @author Roy Terrell.
 */
  public void setStateName(String value) {
    this.stateName = value;
  }
/**
 * Gets the value of member variable stateName
 *
 * @author Roy Terrell.
 */
  public String getStateName() {
    return this.stateName;
  }
/**
 * Sets the value of member variable sttVoidInd
 *
 * @author Roy Terrell.
 */
  public void setSttVoidInd(String value) {
    this.sttVoidInd = value;
  }
/**
 * Gets the value of member variable sttVoidInd
 *
 * @author Roy Terrell.
 */
  public String getSttVoidInd() {
    return this.sttVoidInd;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}