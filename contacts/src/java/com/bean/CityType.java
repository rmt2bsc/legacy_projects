package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the city_type database table/view.
 *
 * @author auto generated.
 */
public class CityType extends OrmBean {




	// Property name constants that belong to respective DataSource, CityTypeView

/** The property name constant equivalent to property, CityTypeId, of respective DataSource view. */
  public static final String PROP_CITYTYPEID = "CityTypeId";
/** The property name constant equivalent to property, Descr, of respective DataSource view. */
  public static final String PROP_DESCR = "Descr";



	/** The javabean property equivalent of database column city_type.city_type_id */
  private String cityTypeId;
/** The javabean property equivalent of database column city_type.descr */
  private String descr;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public CityType() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable cityTypeId
 *
 * @author auto generated.
 */
  public void setCityTypeId(String value) {
    this.cityTypeId = value;
  }
/**
 * Gets the value of member variable cityTypeId
 *
 * @author atuo generated.
 */
  public String getCityTypeId() {
    return this.cityTypeId;
  }
/**
 * Sets the value of member variable descr
 *
 * @author auto generated.
 */
  public void setDescr(String value) {
    this.descr = value;
  }
/**
 * Gets the value of member variable descr
 *
 * @author atuo generated.
 */
  public String getDescr() {
    return this.descr;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}