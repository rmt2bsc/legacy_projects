package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the city_type database table/view.
 *
 * @author Roy Terrell.
 */
public class CityType extends OrmBean {

/** The javabean property equivalent of database column city_type.id */
  private String id;
/** The javabean property equivalent of database column city_type.descr */
  private String descr;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public CityType() throws SystemException {
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(String value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public String getId() {
    return this.id;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}