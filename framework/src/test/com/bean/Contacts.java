package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the contacts database table/view.
 *
 * @author auto generated.
 */
public class Contacts extends OrmBean {




	// Property name constants that belong to respective DataSource, ContactsView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, Surname, of respective DataSource view. */
  public static final String PROP_SURNAME = "Surname";
/** The property name constant equivalent to property, Givenname, of respective DataSource view. */
  public static final String PROP_GIVENNAME = "Givenname";
/** The property name constant equivalent to property, Title, of respective DataSource view. */
  public static final String PROP_TITLE = "Title";
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
/** The property name constant equivalent to property, Fax, of respective DataSource view. */
  public static final String PROP_FAX = "Fax";
/** The property name constant equivalent to property, Customerid, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "Customerid";



	/** The javabean property equivalent of database column contacts.id */
  private int id;
/** The javabean property equivalent of database column contacts.surname */
  private char surname;
/** The javabean property equivalent of database column contacts.givenname */
  private char givenname;
/** The javabean property equivalent of database column contacts.title */
  private String title;
/** The javabean property equivalent of database column contacts.street */
  private char street;
/** The javabean property equivalent of database column contacts.city */
  private char city;
/** The javabean property equivalent of database column contacts.state */
  private char state;
/** The javabean property equivalent of database column contacts.country */
  private char country;
/** The javabean property equivalent of database column contacts.postalcode */
  private char postalcode;
/** The javabean property equivalent of database column contacts.phone */
  private char phone;
/** The javabean property equivalent of database column contacts.fax */
  private char fax;
/** The javabean property equivalent of database column contacts.customerid */
  private int customerid;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Contacts() throws SystemException {
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
 * Sets the value of member variable title
 *
 * @author auto generated.
 */
  public void setTitle(String value) {
    this.title = value;
  }
/**
 * Gets the value of member variable title
 *
 * @author atuo generated.
 */
  public String getTitle() {
    return this.title;
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
 * Sets the value of member variable fax
 *
 * @author auto generated.
 */
  public void setFax(char value) {
    this.fax = value;
  }
/**
 * Gets the value of member variable fax
 *
 * @author atuo generated.
 */
  public char getFax() {
    return this.fax;
  }
/**
 * Sets the value of member variable customerid
 *
 * @author auto generated.
 */
  public void setCustomerid(int value) {
    this.customerid = value;
  }
/**
 * Gets the value of member variable customerid
 *
 * @author atuo generated.
 */
  public int getCustomerid() {
    return this.customerid;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}