package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the zipcode database table/view.
 *
 * @author auto generated.
 */
public class Zipcode extends OrmBean {




	// Property name constants that belong to respective DataSource, ZipcodeView

/** The property name constant equivalent to property, ZipId, of respective DataSource view. */
  public static final String PROP_ZIPID = "ZipId";
/** The property name constant equivalent to property, Zip, of respective DataSource view. */
  public static final String PROP_ZIP = "Zip";
/** The property name constant equivalent to property, City, of respective DataSource view. */
  public static final String PROP_CITY = "City";
/** The property name constant equivalent to property, State, of respective DataSource view. */
  public static final String PROP_STATE = "State";
/** The property name constant equivalent to property, AreaCode, of respective DataSource view. */
  public static final String PROP_AREACODE = "AreaCode";
/** The property name constant equivalent to property, CityAliasName, of respective DataSource view. */
  public static final String PROP_CITYALIASNAME = "CityAliasName";
/** The property name constant equivalent to property, CityAliasAbbr, of respective DataSource view. */
  public static final String PROP_CITYALIASABBR = "CityAliasAbbr";
/** The property name constant equivalent to property, CityTypeId, of respective DataSource view. */
  public static final String PROP_CITYTYPEID = "CityTypeId";
/** The property name constant equivalent to property, CountyName, of respective DataSource view. */
  public static final String PROP_COUNTYNAME = "CountyName";
/** The property name constant equivalent to property, CountyFips, of respective DataSource view. */
  public static final String PROP_COUNTYFIPS = "CountyFips";
/** The property name constant equivalent to property, TimeZoneId, of respective DataSource view. */
  public static final String PROP_TIMEZONEID = "TimeZoneId";
/** The property name constant equivalent to property, DayLightSaving, of respective DataSource view. */
  public static final String PROP_DAYLIGHTSAVING = "DayLightSaving";
/** The property name constant equivalent to property, Latitude, of respective DataSource view. */
  public static final String PROP_LATITUDE = "Latitude";
/** The property name constant equivalent to property, Longitude, of respective DataSource view. */
  public static final String PROP_LONGITUDE = "Longitude";
/** The property name constant equivalent to property, Elevation, of respective DataSource view. */
  public static final String PROP_ELEVATION = "Elevation";
/** The property name constant equivalent to property, Msa, of respective DataSource view. */
  public static final String PROP_MSA = "Msa";
/** The property name constant equivalent to property, Pmsa, of respective DataSource view. */
  public static final String PROP_PMSA = "Pmsa";
/** The property name constant equivalent to property, Cbsa, of respective DataSource view. */
  public static final String PROP_CBSA = "Cbsa";
/** The property name constant equivalent to property, CbsaDiv, of respective DataSource view. */
  public static final String PROP_CBSADIV = "CbsaDiv";
/** The property name constant equivalent to property, PersonsPerHousehold, of respective DataSource view. */
  public static final String PROP_PERSONSPERHOUSEHOLD = "PersonsPerHousehold";
/** The property name constant equivalent to property, ZipcodePopulation, of respective DataSource view. */
  public static final String PROP_ZIPCODEPOPULATION = "ZipcodePopulation";
/** The property name constant equivalent to property, CountiesArea, of respective DataSource view. */
  public static final String PROP_COUNTIESAREA = "CountiesArea";
/** The property name constant equivalent to property, HouseholdsPerZipcode, of respective DataSource view. */
  public static final String PROP_HOUSEHOLDSPERZIPCODE = "HouseholdsPerZipcode";
/** The property name constant equivalent to property, WhitePopulation, of respective DataSource view. */
  public static final String PROP_WHITEPOPULATION = "WhitePopulation";
/** The property name constant equivalent to property, BlackPopulation, of respective DataSource view. */
  public static final String PROP_BLACKPOPULATION = "BlackPopulation";
/** The property name constant equivalent to property, HispanicPopulation, of respective DataSource view. */
  public static final String PROP_HISPANICPOPULATION = "HispanicPopulation";
/** The property name constant equivalent to property, IncomePerHousehold, of respective DataSource view. */
  public static final String PROP_INCOMEPERHOUSEHOLD = "IncomePerHousehold";
/** The property name constant equivalent to property, AverageHouseValue, of respective DataSource view. */
  public static final String PROP_AVERAGEHOUSEVALUE = "AverageHouseValue";



	/** The javabean property equivalent of database column zipcode.zip_id */
  private int zipId;
/** The javabean property equivalent of database column zipcode.zip */
  private int zip;
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
/** The javabean property equivalent of database column zipcode.city_type_id */
  private String cityTypeId;
/** The javabean property equivalent of database column zipcode.county_name */
  private String countyName;
/** The javabean property equivalent of database column zipcode.county_fips */
  private String countyFips;
/** The javabean property equivalent of database column zipcode.time_zone_id */
  private int timeZoneId;
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
 * @author auto generated.
 */
  public Zipcode() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable zipId
 *
 * @author auto generated.
 */
  public void setZipId(int value) {
    this.zipId = value;
  }
/**
 * Gets the value of member variable zipId
 *
 * @author atuo generated.
 */
  public int getZipId() {
    return this.zipId;
  }
/**
 * Sets the value of member variable zip
 *
 * @author auto generated.
 */
  public void setZip(int value) {
    this.zip = value;
  }
/**
 * Gets the value of member variable zip
 *
 * @author atuo generated.
 */
  public int getZip() {
    return this.zip;
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
 * Sets the value of member variable state
 *
 * @author auto generated.
 */
  public void setState(String value) {
    this.state = value;
  }
/**
 * Gets the value of member variable state
 *
 * @author atuo generated.
 */
  public String getState() {
    return this.state;
  }
/**
 * Sets the value of member variable areaCode
 *
 * @author auto generated.
 */
  public void setAreaCode(String value) {
    this.areaCode = value;
  }
/**
 * Gets the value of member variable areaCode
 *
 * @author atuo generated.
 */
  public String getAreaCode() {
    return this.areaCode;
  }
/**
 * Sets the value of member variable cityAliasName
 *
 * @author auto generated.
 */
  public void setCityAliasName(String value) {
    this.cityAliasName = value;
  }
/**
 * Gets the value of member variable cityAliasName
 *
 * @author atuo generated.
 */
  public String getCityAliasName() {
    return this.cityAliasName;
  }
/**
 * Sets the value of member variable cityAliasAbbr
 *
 * @author auto generated.
 */
  public void setCityAliasAbbr(String value) {
    this.cityAliasAbbr = value;
  }
/**
 * Gets the value of member variable cityAliasAbbr
 *
 * @author atuo generated.
 */
  public String getCityAliasAbbr() {
    return this.cityAliasAbbr;
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
 * Sets the value of member variable countyName
 *
 * @author auto generated.
 */
  public void setCountyName(String value) {
    this.countyName = value;
  }
/**
 * Gets the value of member variable countyName
 *
 * @author atuo generated.
 */
  public String getCountyName() {
    return this.countyName;
  }
/**
 * Sets the value of member variable countyFips
 *
 * @author auto generated.
 */
  public void setCountyFips(String value) {
    this.countyFips = value;
  }
/**
 * Gets the value of member variable countyFips
 *
 * @author atuo generated.
 */
  public String getCountyFips() {
    return this.countyFips;
  }
/**
 * Sets the value of member variable timeZoneId
 *
 * @author auto generated.
 */
  public void setTimeZoneId(int value) {
    this.timeZoneId = value;
  }
/**
 * Gets the value of member variable timeZoneId
 *
 * @author atuo generated.
 */
  public int getTimeZoneId() {
    return this.timeZoneId;
  }
/**
 * Sets the value of member variable dayLightSaving
 *
 * @author auto generated.
 */
  public void setDayLightSaving(String value) {
    this.dayLightSaving = value;
  }
/**
 * Gets the value of member variable dayLightSaving
 *
 * @author atuo generated.
 */
  public String getDayLightSaving() {
    return this.dayLightSaving;
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
 * Sets the value of member variable elevation
 *
 * @author auto generated.
 */
  public void setElevation(double value) {
    this.elevation = value;
  }
/**
 * Gets the value of member variable elevation
 *
 * @author atuo generated.
 */
  public double getElevation() {
    return this.elevation;
  }
/**
 * Sets the value of member variable msa
 *
 * @author auto generated.
 */
  public void setMsa(double value) {
    this.msa = value;
  }
/**
 * Gets the value of member variable msa
 *
 * @author atuo generated.
 */
  public double getMsa() {
    return this.msa;
  }
/**
 * Sets the value of member variable pmsa
 *
 * @author auto generated.
 */
  public void setPmsa(double value) {
    this.pmsa = value;
  }
/**
 * Gets the value of member variable pmsa
 *
 * @author atuo generated.
 */
  public double getPmsa() {
    return this.pmsa;
  }
/**
 * Sets the value of member variable cbsa
 *
 * @author auto generated.
 */
  public void setCbsa(double value) {
    this.cbsa = value;
  }
/**
 * Gets the value of member variable cbsa
 *
 * @author atuo generated.
 */
  public double getCbsa() {
    return this.cbsa;
  }
/**
 * Sets the value of member variable cbsaDiv
 *
 * @author auto generated.
 */
  public void setCbsaDiv(double value) {
    this.cbsaDiv = value;
  }
/**
 * Gets the value of member variable cbsaDiv
 *
 * @author atuo generated.
 */
  public double getCbsaDiv() {
    return this.cbsaDiv;
  }
/**
 * Sets the value of member variable personsPerHousehold
 *
 * @author auto generated.
 */
  public void setPersonsPerHousehold(double value) {
    this.personsPerHousehold = value;
  }
/**
 * Gets the value of member variable personsPerHousehold
 *
 * @author atuo generated.
 */
  public double getPersonsPerHousehold() {
    return this.personsPerHousehold;
  }
/**
 * Sets the value of member variable zipcodePopulation
 *
 * @author auto generated.
 */
  public void setZipcodePopulation(double value) {
    this.zipcodePopulation = value;
  }
/**
 * Gets the value of member variable zipcodePopulation
 *
 * @author atuo generated.
 */
  public double getZipcodePopulation() {
    return this.zipcodePopulation;
  }
/**
 * Sets the value of member variable countiesArea
 *
 * @author auto generated.
 */
  public void setCountiesArea(double value) {
    this.countiesArea = value;
  }
/**
 * Gets the value of member variable countiesArea
 *
 * @author atuo generated.
 */
  public double getCountiesArea() {
    return this.countiesArea;
  }
/**
 * Sets the value of member variable householdsPerZipcode
 *
 * @author auto generated.
 */
  public void setHouseholdsPerZipcode(double value) {
    this.householdsPerZipcode = value;
  }
/**
 * Gets the value of member variable householdsPerZipcode
 *
 * @author atuo generated.
 */
  public double getHouseholdsPerZipcode() {
    return this.householdsPerZipcode;
  }
/**
 * Sets the value of member variable whitePopulation
 *
 * @author auto generated.
 */
  public void setWhitePopulation(double value) {
    this.whitePopulation = value;
  }
/**
 * Gets the value of member variable whitePopulation
 *
 * @author atuo generated.
 */
  public double getWhitePopulation() {
    return this.whitePopulation;
  }
/**
 * Sets the value of member variable blackPopulation
 *
 * @author auto generated.
 */
  public void setBlackPopulation(double value) {
    this.blackPopulation = value;
  }
/**
 * Gets the value of member variable blackPopulation
 *
 * @author atuo generated.
 */
  public double getBlackPopulation() {
    return this.blackPopulation;
  }
/**
 * Sets the value of member variable hispanicPopulation
 *
 * @author auto generated.
 */
  public void setHispanicPopulation(double value) {
    this.hispanicPopulation = value;
  }
/**
 * Gets the value of member variable hispanicPopulation
 *
 * @author atuo generated.
 */
  public double getHispanicPopulation() {
    return this.hispanicPopulation;
  }
/**
 * Sets the value of member variable incomePerHousehold
 *
 * @author auto generated.
 */
  public void setIncomePerHousehold(double value) {
    this.incomePerHousehold = value;
  }
/**
 * Gets the value of member variable incomePerHousehold
 *
 * @author atuo generated.
 */
  public double getIncomePerHousehold() {
    return this.incomePerHousehold;
  }
/**
 * Sets the value of member variable averageHouseValue
 *
 * @author auto generated.
 */
  public void setAverageHouseValue(double value) {
    this.averageHouseValue = value;
  }
/**
 * Gets the value of member variable averageHouseValue
 *
 * @author atuo generated.
 */
  public double getAverageHouseValue() {
    return this.averageHouseValue;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}