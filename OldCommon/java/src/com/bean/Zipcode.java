package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the zipcode database table/view.
 *
 * @author Roy Terrell.
 */
public class Zipcode extends OrmBean {

/** The javabean property equivalent of database column zipcode.id */
  private int id;
/** The javabean property equivalent of database column zipcode.zip */
  private String zip;
/** The javabean property equivalent of database column zipcode.city */
  private String city;
/** The javabean property equivalent of database column zipcode.state */
  private String state;
/** The javabean property equivalent of database column zipcode.area_code */
  private String areaCode;
/** The javabean property equivalent of database column zipcode.city_alias_name */
  private String cityAliasName;
/** The javabean property equivalent of database column zipcode.city_alias_abbr */
  private String cityAliasAbbr;
/** The javabean property equivalent of database column zipcode.city_type */
  private String cityType;
/** The javabean property equivalent of database column zipcode.county_name */
  private String countyName;
/** The javabean property equivalent of database column zipcode.county_fips */
  private String countyFips;
/** The javabean property equivalent of database column zipcode.time_zone */
  private int timeZone;
/** The javabean property equivalent of database column zipcode.day_light_saving */
  private String dayLightSaving;
/** The javabean property equivalent of database column zipcode.latitude */
  private double latitude;
/** The javabean property equivalent of database column zipcode.longitude */
  private double longitude;
/** The javabean property equivalent of database column zipcode.elevation */
  private double elevation;
/** The javabean property equivalent of database column zipcode.msa */
  private double msa;
/** The javabean property equivalent of database column zipcode.pmsa */
  private double pmsa;
/** The javabean property equivalent of database column zipcode.cbsa */
  private double cbsa;
/** The javabean property equivalent of database column zipcode.cbsa_div */
  private double cbsaDiv;
/** The javabean property equivalent of database column zipcode.persons_per_household */
  private double personsPerHousehold;
/** The javabean property equivalent of database column zipcode.zipcode_population */
  private double zipcodePopulation;
/** The javabean property equivalent of database column zipcode.counties_area */
  private double countiesArea;
/** The javabean property equivalent of database column zipcode.households_per_zipcode */
  private double householdsPerZipcode;
/** The javabean property equivalent of database column zipcode.white_population */
  private double whitePopulation;
/** The javabean property equivalent of database column zipcode.black_population */
  private double blackPopulation;
/** The javabean property equivalent of database column zipcode.hispanic_population */
  private double hispanicPopulation;
/** The javabean property equivalent of database column zipcode.income_per_household */
  private double incomePerHousehold;
/** The javabean property equivalent of database column zipcode.average_house_value */
  private double averageHouseValue;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public Zipcode() throws SystemException {
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
 * Sets the value of member variable zip
 *
 * @author Roy Terrell.
 */
  public void setZip(String value) {
    this.zip = value;
  }
/**
 * Gets the value of member variable zip
 *
 * @author Roy Terrell.
 */
  public String getZip() {
    return this.zip;
  }
/**
 * Sets the value of member variable city
 *
 * @author Roy Terrell.
 */
  public void setCity(String value) {
    this.city = value;
  }
/**
 * Gets the value of member variable city
 *
 * @author Roy Terrell.
 */
  public String getCity() {
    return this.city;
  }
/**
 * Sets the value of member variable state
 *
 * @author Roy Terrell.
 */
  public void setState(String value) {
    this.state = value;
  }
/**
 * Gets the value of member variable state
 *
 * @author Roy Terrell.
 */
  public String getState() {
    return this.state;
  }
/**
 * Sets the value of member variable areaCode
 *
 * @author Roy Terrell.
 */
  public void setAreaCode(String value) {
    this.areaCode = value;
  }
/**
 * Gets the value of member variable areaCode
 *
 * @author Roy Terrell.
 */
  public String getAreaCode() {
    return this.areaCode;
  }
/**
 * Sets the value of member variable cityAliasName
 *
 * @author Roy Terrell.
 */
  public void setCityAliasName(String value) {
    this.cityAliasName = value;
  }
/**
 * Gets the value of member variable cityAliasName
 *
 * @author Roy Terrell.
 */
  public String getCityAliasName() {
    return this.cityAliasName;
  }
/**
 * Sets the value of member variable cityAliasAbbr
 *
 * @author Roy Terrell.
 */
  public void setCityAliasAbbr(String value) {
    this.cityAliasAbbr = value;
  }
/**
 * Gets the value of member variable cityAliasAbbr
 *
 * @author Roy Terrell.
 */
  public String getCityAliasAbbr() {
    return this.cityAliasAbbr;
  }
/**
 * Sets the value of member variable cityType
 *
 * @author Roy Terrell.
 */
  public void setCityType(String value) {
    this.cityType = value;
  }
/**
 * Gets the value of member variable cityType
 *
 * @author Roy Terrell.
 */
  public String getCityType() {
    return this.cityType;
  }
/**
 * Sets the value of member variable countyName
 *
 * @author Roy Terrell.
 */
  public void setCountyName(String value) {
    this.countyName = value;
  }
/**
 * Gets the value of member variable countyName
 *
 * @author Roy Terrell.
 */
  public String getCountyName() {
    return this.countyName;
  }
/**
 * Sets the value of member variable countyFips
 *
 * @author Roy Terrell.
 */
  public void setCountyFips(String value) {
    this.countyFips = value;
  }
/**
 * Gets the value of member variable countyFips
 *
 * @author Roy Terrell.
 */
  public String getCountyFips() {
    return this.countyFips;
  }
/**
 * Sets the value of member variable timeZone
 *
 * @author Roy Terrell.
 */
  public void setTimeZone(int value) {
    this.timeZone = value;
  }
/**
 * Gets the value of member variable timeZone
 *
 * @author Roy Terrell.
 */
  public int getTimeZone() {
    return this.timeZone;
  }
/**
 * Sets the value of member variable dayLightSaving
 *
 * @author Roy Terrell.
 */
  public void setDayLightSaving(String value) {
    this.dayLightSaving = value;
  }
/**
 * Gets the value of member variable dayLightSaving
 *
 * @author Roy Terrell.
 */
  public String getDayLightSaving() {
    return this.dayLightSaving;
  }
/**
 * Sets the value of member variable latitude
 *
 * @author Roy Terrell.
 */
  public void setLatitude(double value) {
    this.latitude = value;
  }
/**
 * Gets the value of member variable latitude
 *
 * @author Roy Terrell.
 */
  public double getLatitude() {
    return this.latitude;
  }
/**
 * Sets the value of member variable longitude
 *
 * @author Roy Terrell.
 */
  public void setLongitude(double value) {
    this.longitude = value;
  }
/**
 * Gets the value of member variable longitude
 *
 * @author Roy Terrell.
 */
  public double getLongitude() {
    return this.longitude;
  }
/**
 * Sets the value of member variable elevation
 *
 * @author Roy Terrell.
 */
  public void setElevation(double value) {
    this.elevation = value;
  }
/**
 * Gets the value of member variable elevation
 *
 * @author Roy Terrell.
 */
  public double getElevation() {
    return this.elevation;
  }
/**
 * Sets the value of member variable msa
 *
 * @author Roy Terrell.
 */
  public void setMsa(double value) {
    this.msa = value;
  }
/**
 * Gets the value of member variable msa
 *
 * @author Roy Terrell.
 */
  public double getMsa() {
    return this.msa;
  }
/**
 * Sets the value of member variable pmsa
 *
 * @author Roy Terrell.
 */
  public void setPmsa(double value) {
    this.pmsa = value;
  }
/**
 * Gets the value of member variable pmsa
 *
 * @author Roy Terrell.
 */
  public double getPmsa() {
    return this.pmsa;
  }
/**
 * Sets the value of member variable cbsa
 *
 * @author Roy Terrell.
 */
  public void setCbsa(double value) {
    this.cbsa = value;
  }
/**
 * Gets the value of member variable cbsa
 *
 * @author Roy Terrell.
 */
  public double getCbsa() {
    return this.cbsa;
  }
/**
 * Sets the value of member variable cbsaDiv
 *
 * @author Roy Terrell.
 */
  public void setCbsaDiv(double value) {
    this.cbsaDiv = value;
  }
/**
 * Gets the value of member variable cbsaDiv
 *
 * @author Roy Terrell.
 */
  public double getCbsaDiv() {
    return this.cbsaDiv;
  }
/**
 * Sets the value of member variable personsPerHousehold
 *
 * @author Roy Terrell.
 */
  public void setPersonsPerHousehold(double value) {
    this.personsPerHousehold = value;
  }
/**
 * Gets the value of member variable personsPerHousehold
 *
 * @author Roy Terrell.
 */
  public double getPersonsPerHousehold() {
    return this.personsPerHousehold;
  }
/**
 * Sets the value of member variable zipcodePopulation
 *
 * @author Roy Terrell.
 */
  public void setZipcodePopulation(double value) {
    this.zipcodePopulation = value;
  }
/**
 * Gets the value of member variable zipcodePopulation
 *
 * @author Roy Terrell.
 */
  public double getZipcodePopulation() {
    return this.zipcodePopulation;
  }
/**
 * Sets the value of member variable countiesArea
 *
 * @author Roy Terrell.
 */
  public void setCountiesArea(double value) {
    this.countiesArea = value;
  }
/**
 * Gets the value of member variable countiesArea
 *
 * @author Roy Terrell.
 */
  public double getCountiesArea() {
    return this.countiesArea;
  }
/**
 * Sets the value of member variable householdsPerZipcode
 *
 * @author Roy Terrell.
 */
  public void setHouseholdsPerZipcode(double value) {
    this.householdsPerZipcode = value;
  }
/**
 * Gets the value of member variable householdsPerZipcode
 *
 * @author Roy Terrell.
 */
  public double getHouseholdsPerZipcode() {
    return this.householdsPerZipcode;
  }
/**
 * Sets the value of member variable whitePopulation
 *
 * @author Roy Terrell.
 */
  public void setWhitePopulation(double value) {
    this.whitePopulation = value;
  }
/**
 * Gets the value of member variable whitePopulation
 *
 * @author Roy Terrell.
 */
  public double getWhitePopulation() {
    return this.whitePopulation;
  }
/**
 * Sets the value of member variable blackPopulation
 *
 * @author Roy Terrell.
 */
  public void setBlackPopulation(double value) {
    this.blackPopulation = value;
  }
/**
 * Gets the value of member variable blackPopulation
 *
 * @author Roy Terrell.
 */
  public double getBlackPopulation() {
    return this.blackPopulation;
  }
/**
 * Sets the value of member variable hispanicPopulation
 *
 * @author Roy Terrell.
 */
  public void setHispanicPopulation(double value) {
    this.hispanicPopulation = value;
  }
/**
 * Gets the value of member variable hispanicPopulation
 *
 * @author Roy Terrell.
 */
  public double getHispanicPopulation() {
    return this.hispanicPopulation;
  }
/**
 * Sets the value of member variable incomePerHousehold
 *
 * @author Roy Terrell.
 */
  public void setIncomePerHousehold(double value) {
    this.incomePerHousehold = value;
  }
/**
 * Gets the value of member variable incomePerHousehold
 *
 * @author Roy Terrell.
 */
  public double getIncomePerHousehold() {
    return this.incomePerHousehold;
  }
/**
 * Sets the value of member variable averageHouseValue
 *
 * @author Roy Terrell.
 */
  public void setAverageHouseValue(double value) {
    this.averageHouseValue = value;
  }
/**
 * Gets the value of member variable averageHouseValue
 *
 * @author Roy Terrell.
 */
  public double getAverageHouseValue() {
    return this.averageHouseValue;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}