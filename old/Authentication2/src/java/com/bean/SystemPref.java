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




	// Property name constants that belong to respective DataSource, SystemPrefView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, MainKey, of respective DataSource view. */
  public static final String PROP_MAINKEY = "MainKey";
/** The property name constant equivalent to property, SubKey, of respective DataSource view. */
  public static final String PROP_SUBKEY = "SubKey";
/** The property name constant equivalent to property, Descr, of respective DataSource view. */
  public static final String PROP_DESCR = "Descr";



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
	super();
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