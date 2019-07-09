package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the country database table/view.
 *
 * @author auto generated.
 */
public class Country extends OrmBean {




	// Property name constants that belong to respective DataSource, CountryView

/** The property name constant equivalent to property, CountryId, of respective DataSource view. */
  public static final String PROP_COUNTRYID = "CountryId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, CntryVoidInd, of respective DataSource view. */
  public static final String PROP_CNTRYVOIDIND = "CntryVoidInd";
/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";



	/** The javabean property equivalent of database column country.country_id */
  private int countryId;
/** The javabean property equivalent of database column country.name */
  private String name;
/** The javabean property equivalent of database column country.cntry_void_ind */
  private String cntryVoidInd;
/** The javabean property equivalent of database column country.code */
  private String code;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Country() throws SystemException {
	super();
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
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable cntryVoidInd
 *
 * @author auto generated.
 */
  public void setCntryVoidInd(String value) {
    this.cntryVoidInd = value;
  }
/**
 * Gets the value of member variable cntryVoidInd
 *
 * @author atuo generated.
 */
  public String getCntryVoidInd() {
    return this.cntryVoidInd;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}