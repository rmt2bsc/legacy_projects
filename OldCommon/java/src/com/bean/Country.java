package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the country database table/view.
 *
 * @author Roy Terrell.
 */
public class Country extends OrmBean {

/** The javabean property equivalent of database column country.id */
  private int id;
/** The javabean property equivalent of database column country.name */
  private String name;
/** The javabean property equivalent of database column country.cntry_void_ind */
  private String cntryVoidInd;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public Country() throws SystemException {
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable cntryVoidInd
 *
 * @author Roy Terrell.
 */
  public void setCntryVoidInd(String value) {
    this.cntryVoidInd = value;
  }
/**
 * Gets the value of member variable cntryVoidInd
 *
 * @author Roy Terrell.
 */
  public String getCntryVoidInd() {
    return this.cntryVoidInd;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}