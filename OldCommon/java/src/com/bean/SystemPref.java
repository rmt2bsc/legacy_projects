package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the system_pref database table/view.
 *
 * @author Roy Terrell.
 */
public class SystemPref extends OrmBean {

/** The javabean property equivalent of database column system_pref.id */
  private int id;
/** The javabean property equivalent of database column system_pref.main_key */
  private String mainKey;
/** The javabean property equivalent of database column system_pref.sub_key */
  private String subKey;
/** The javabean property equivalent of database column system_pref.descr */
  private String descr;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public SystemPref() throws SystemException {
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
 * Sets the value of member variable mainKey
 *
 * @author Roy Terrell.
 */
  public void setMainKey(String value) {
    this.mainKey = value;
  }
/**
 * Gets the value of member variable mainKey
 *
 * @author Roy Terrell.
 */
  public String getMainKey() {
    return this.mainKey;
  }
/**
 * Sets the value of member variable subKey
 *
 * @author Roy Terrell.
 */
  public void setSubKey(String value) {
    this.subKey = value;
  }
/**
 * Gets the value of member variable subKey
 *
 * @author Roy Terrell.
 */
  public String getSubKey() {
    return this.subKey;
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