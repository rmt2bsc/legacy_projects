package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the time_zone database table/view.
 *
 * @author Roy Terrell.
 */
public class TimeZone extends OrmBean {

/** The javabean property equivalent of database column time_zone.id */
  private int id;
/** The javabean property equivalent of database column time_zone.descr */
  private String descr;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public TimeZone() throws SystemException {
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