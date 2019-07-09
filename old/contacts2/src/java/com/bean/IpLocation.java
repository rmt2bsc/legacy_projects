package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the ip_location database table/view.
 *
 * @author auto generated.
 */
public class IpLocation extends OrmBean {




	// Property name constants that belong to respective DataSource, IpLocationView

/** The property name constant equivalent to property, IpFrom, of respective DataSource view. */
  public static final String PROP_IPFROM = "IpFrom";
/** The property name constant equivalent to property, IpTo, of respective DataSource view. */
  public static final String PROP_IPTO = "IpTo";
/** The property name constant equivalent to property, CountryCode, of respective DataSource view. */
  public static final String PROP_COUNTRYCODE = "CountryCode";
/** The property name constant equivalent to property, CountryName, of respective DataSource view. */
  public static final String PROP_COUNTRYNAME = "CountryName";
/** The property name constant equivalent to property, Region, of respective DataSource view. */
  public static final String PROP_REGION = "Region";
/** The property name constant equivalent to property, City, of respective DataSource view. */
  public static final String PROP_CITY = "City";
/** The property name constant equivalent to property, Latitude, of respective DataSource view. */
  public static final String PROP_LATITUDE = "Latitude";
/** The property name constant equivalent to property, Longitude, of respective DataSource view. */
  public static final String PROP_LONGITUDE = "Longitude";
/** The property name constant equivalent to property, Zipcode, of respective DataSource view. */
  public static final String PROP_ZIPCODE = "Zipcode";
/** The property name constant equivalent to property, Timezone, of respective DataSource view. */
  public static final String PROP_TIMEZONE = "Timezone";
/** The property name constant equivalent to property, IpId, of respective DataSource view. */
  public static final String PROP_IPID = "IpId";



	/** The javabean property equivalent of database column ip_location.ip_from */
  private double ipFrom;
/** The javabean property equivalent of database column ip_location.ip_to */
  private double ipTo;
/** The javabean property equivalent of database column ip_location.country_code */
  private String countryCode;
/** The javabean property equivalent of database column ip_location.country_name */
  private String countryName;
/** The javabean property equivalent of database column ip_location.region */
  private String region;
/** The javabean property equivalent of database column ip_location.city */
  private String city;
/** The javabean property equivalent of database column ip_location.latitude */
  private double latitude;
/** The javabean property equivalent of database column ip_location.longitude */
  private double longitude;
/** The javabean property equivalent of database column ip_location.zipcode */
  private String zipcode;
/** The javabean property equivalent of database column ip_location.timezone */
  private String timezone;
/** The javabean property equivalent of database column ip_location.ip_id */
  private int ipId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public IpLocation() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable ipFrom
 *
 * @author auto generated.
 */
  public void setIpFrom(double value) {
    this.ipFrom = value;
  }
/**
 * Gets the value of member variable ipFrom
 *
 * @author atuo generated.
 */
  public double getIpFrom() {
    return this.ipFrom;
  }
/**
 * Sets the value of member variable ipTo
 *
 * @author auto generated.
 */
  public void setIpTo(double value) {
    this.ipTo = value;
  }
/**
 * Gets the value of member variable ipTo
 *
 * @author atuo generated.
 */
  public double getIpTo() {
    return this.ipTo;
  }
/**
 * Sets the value of member variable countryCode
 *
 * @author auto generated.
 */
  public void setCountryCode(String value) {
    this.countryCode = value;
  }
/**
 * Gets the value of member variable countryCode
 *
 * @author atuo generated.
 */
  public String getCountryCode() {
    return this.countryCode;
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
 * Sets the value of member variable region
 *
 * @author auto generated.
 */
  public void setRegion(String value) {
    this.region = value;
  }
/**
 * Gets the value of member variable region
 *
 * @author atuo generated.
 */
  public String getRegion() {
    return this.region;
  }
/**
 * Sets the value of member variable city
 *
 * @author auto generated.
 */
  public void setCity(String value) {
    this.city = value;
  }
/**
 * Gets the value of member variable city
 *
 * @author atuo generated.
 */
  public String getCity() {
    return this.city;
  }
/**
 * Sets the value of member variable latitude
 *
 * @author auto generated.
 */
  public void setLatitude(double value) {
    this.latitude = value;
  }
/**
 * Gets the value of member variable latitude
 *
 * @author atuo generated.
 */
  public double getLatitude() {
    return this.latitude;
  }
/**
 * Sets the value of member variable longitude
 *
 * @author auto generated.
 */
  public void setLongitude(double value) {
    this.longitude = value;
  }
/**
 * Gets the value of member variable longitude
 *
 * @author atuo generated.
 */
  public double getLongitude() {
    return this.longitude;
  }
/**
 * Sets the value of member variable zipcode
 *
 * @author auto generated.
 */
  public void setZipcode(String value) {
    this.zipcode = value;
  }
/**
 * Gets the value of member variable zipcode
 *
 * @author atuo generated.
 */
  public String getZipcode() {
    return this.zipcode;
  }
/**
 * Sets the value of member variable timezone
 *
 * @author auto generated.
 */
  public void setTimezone(String value) {
    this.timezone = value;
  }
/**
 * Gets the value of member variable timezone
 *
 * @author atuo generated.
 */
  public String getTimezone() {
    return this.timezone;
  }
/**
 * Sets the value of member variable ipId
 *
 * @author auto generated.
 */
  public void setIpId(int value) {
    this.ipId = value;
  }
/**
 * Gets the value of member variable ipId
 *
 * @author atuo generated.
 */
  public int getIpId() {
    return this.ipId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}