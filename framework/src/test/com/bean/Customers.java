package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the customers database table/view.
 *
 * @author auto generated.
 */
public class Customers extends OrmBean {




	// Property name constants that belong to respective DataSource, CustomersView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, Surname, of respective DataSource view. */
  public static final String PROP_SURNAME = "Surname";
/** The property name constant equivalent to property, Givenname, of respective DataSource view. */
  public static final String PROP_GIVENNAME = "Givenname";
/** The property name constant equivalent to property, Street, of respective DataSource view. */
  public static final String PROP_STREET = "Street";
/** The property name constant equivalent to property, City, of respective DataSource view. */
  public static final String PROP_CITY = "City";
/** The property name constant equivalent to property, State, of respective DataSource view. */
  public static final String PROP_STATE = "State";
/** The property name constant equivalent to property, Country, of respective DataSource view. */
  public static final String PROP_COUNTRY = "Country";
/** The property name constant equivalent to property, Postalcode, of respective DataSource view. */
  public static final String PROP_POSTALCODE = "Postalcode";
/** The property name constant equivalent to property, Phone, of respective DataSource view. */
  public static final String PROP_PHONE = "Phone";
/** The property name constant equivalent to property, Companyname, of respective DataSource view. */
  public static final String PROP_COMPANYNAME = "Companyname";



	/** The javabean property equivalent of database column customers.id */
  private int id;
/** The javabean property equivalent of database column customers.surname */
  private char surname;
/** The javabean property equivalent of database column customers.givenname */
  private char givenname;
/** The javabean property equivalent of database column customers.street */
  private char street;
/** The javabean property equivalent of database column customers.city */
  private char city;
/** The javabean property equivalent of database column customers.state */
  private char state;
/** The javabean property equivalent of database column customers.country */
  private char country;
/** The javabean property equivalent of database column customers.postalcode */
  private char postalcode;
/** The javabean property equivalent of database column customers.phone */
  private char phone;
/** The javabean property equivalent of database column customers.companyname */
  private char companyname;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Customers() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable surname
 *
 * @author auto generated.
 */
  public void setSurname(char value) {
    this.surname = value;
  }
/**
 * Gets the value of member variable surname
 *
 * @author atuo generated.
 */
  public char getSurname() {
    return this.surname;
  }
/**
 * Sets the value of member variable givenname
 *
 * @author auto generated.
 */
  public void setGivenname(char value) {
    this.givenname = value;
  }
/**
 * Gets the value of member variable givenname
 *
 * @author atuo generated.
 */
  public char getGivenname() {
    return this.givenname;
  }
/**
 * Sets the value of member variable street
 *
 * @author auto generated.
 */
  public void setStreet(char value) {
    this.street = value;
  }
/**
 * Gets the value of member variable street
 *
 * @author atuo generated.
 */
  public char getStreet() {
    return this.street;
  }
/**
 * Sets the value of member variable city
 *
 * @author auto generated.
 */
  public void setCity(char value) {
    this.city = value;
  }
/**
 * Gets the value of member variable city
 *
 * @author atuo generated.
 */
  public char getCity() {
    return this.city;
  }
/**
 * Sets the value of member variable state
 *
 * @author auto generated.
 */
  public void setState(char value) {
    this.state = value;
  }
/**
 * Gets the value of member variable state
 *
 * @author atuo generated.
 */
  public char getState() {
    return this.state;
  }
/**
 * Sets the value of member variable country
 *
 * @author auto generated.
 */
  public void setCountry(char value) {
    this.country = value;
  }
/**
 * Gets the value of member variable country
 *
 * @author atuo generated.
 */
  public char getCountry() {
    return this.country;
  }
/**
 * Sets the value of member variable postalcode
 *
 * @author auto generated.
 */
  public void setPostalcode(char value) {
    this.postalcode = value;
  }
/**
 * Gets the value of member variable postalcode
 *
 * @author atuo generated.
 */
  public char getPostalcode() {
    return this.postalcode;
  }
/**
 * Sets the value of member variable phone
 *
 * @author auto generated.
 */
  public void setPhone(char value) {
    this.phone = value;
  }
/**
 * Gets the value of member variable phone
 *
 * @author atuo generated.
 */
  public char getPhone() {
    return this.phone;
  }
/**
 * Sets the value of member variable companyname
 *
 * @author auto generated.
 */
  public void setCompanyname(char value) {
    this.companyname = value;
  }
/**
 * Gets the value of member variable companyname
 *
 * @author atuo generated.
 */
  public char getCompanyname() {
    return this.companyname;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}