package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the address database table/view.
 *
 * @author Roy Terrell.
 */
public class Address extends OrmBean {

/** The javabean property equivalent of database column address.id */
  private int id;
/** The javabean property equivalent of database column address.person_id */
  private int personId;
/** The javabean property equivalent of database column address.business_id */
  private int businessId;
/** The javabean property equivalent of database column address.addr1 */
  private String addr1;
/** The javabean property equivalent of database column address.addr2 */
  private String addr2;
/** The javabean property equivalent of database column address.addr3 */
  private String addr3;
/** The javabean property equivalent of database column address.addr4 */
  private String addr4;
/** The javabean property equivalent of database column address.zip */
  private int zip;
/** The javabean property equivalent of database column address.zipext */
  private String zipext;
/** The javabean property equivalent of database column address.phone_home */
  private String phoneHome;
/** The javabean property equivalent of database column address.phone_work */
  private String phoneWork;
/** The javabean property equivalent of database column address.phone_ext */
  private String phoneExt;
/** The javabean property equivalent of database column address.phone_main */
  private String phoneMain;
/** The javabean property equivalent of database column address.phone_cell */
  private String phoneCell;
/** The javabean property equivalent of database column address.phone_fax */
  private String phoneFax;
/** The javabean property equivalent of database column address.phone_pager */
  private String phonePager;
/** The javabean property equivalent of database column address.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column address.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column address.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public Address() throws SystemException {
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
 * Sets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable addr1
 *
 * @author Roy Terrell.
 */
  public void setAddr1(String value) {
    this.addr1 = value;
  }
/**
 * Gets the value of member variable addr1
 *
 * @author Roy Terrell.
 */
  public String getAddr1() {
    return this.addr1;
  }
/**
 * Sets the value of member variable addr2
 *
 * @author Roy Terrell.
 */
  public void setAddr2(String value) {
    this.addr2 = value;
  }
/**
 * Gets the value of member variable addr2
 *
 * @author Roy Terrell.
 */
  public String getAddr2() {
    return this.addr2;
  }
/**
 * Sets the value of member variable addr3
 *
 * @author Roy Terrell.
 */
  public void setAddr3(String value) {
    this.addr3 = value;
  }
/**
 * Gets the value of member variable addr3
 *
 * @author Roy Terrell.
 */
  public String getAddr3() {
    return this.addr3;
  }
/**
 * Sets the value of member variable addr4
 *
 * @author Roy Terrell.
 */
  public void setAddr4(String value) {
    this.addr4 = value;
  }
/**
 * Gets the value of member variable addr4
 *
 * @author Roy Terrell.
 */
  public String getAddr4() {
    return this.addr4;
  }
/**
 * Sets the value of member variable zip
 *
 * @author Roy Terrell.
 */
  public void setZip(int value) {
    this.zip = value;
  }
/**
 * Gets the value of member variable zip
 *
 * @author Roy Terrell.
 */
  public int getZip() {
    return this.zip;
  }
/**
 * Sets the value of member variable zipext
 *
 * @author Roy Terrell.
 */
  public void setZipext(String value) {
    this.zipext = value;
  }
/**
 * Gets the value of member variable zipext
 *
 * @author Roy Terrell.
 */
  public String getZipext() {
    return this.zipext;
  }
/**
 * Sets the value of member variable phoneHome
 *
 * @author Roy Terrell.
 */
  public void setPhoneHome(String value) {
    this.phoneHome = value;
  }
/**
 * Gets the value of member variable phoneHome
 *
 * @author Roy Terrell.
 */
  public String getPhoneHome() {
    return this.phoneHome;
  }
/**
 * Sets the value of member variable phoneWork
 *
 * @author Roy Terrell.
 */
  public void setPhoneWork(String value) {
    this.phoneWork = value;
  }
/**
 * Gets the value of member variable phoneWork
 *
 * @author Roy Terrell.
 */
  public String getPhoneWork() {
    return this.phoneWork;
  }
/**
 * Sets the value of member variable phoneExt
 *
 * @author Roy Terrell.
 */
  public void setPhoneExt(String value) {
    this.phoneExt = value;
  }
/**
 * Gets the value of member variable phoneExt
 *
 * @author Roy Terrell.
 */
  public String getPhoneExt() {
    return this.phoneExt;
  }
/**
 * Sets the value of member variable phoneMain
 *
 * @author Roy Terrell.
 */
  public void setPhoneMain(String value) {
    this.phoneMain = value;
  }
/**
 * Gets the value of member variable phoneMain
 *
 * @author Roy Terrell.
 */
  public String getPhoneMain() {
    return this.phoneMain;
  }
/**
 * Sets the value of member variable phoneCell
 *
 * @author Roy Terrell.
 */
  public void setPhoneCell(String value) {
    this.phoneCell = value;
  }
/**
 * Gets the value of member variable phoneCell
 *
 * @author Roy Terrell.
 */
  public String getPhoneCell() {
    return this.phoneCell;
  }
/**
 * Sets the value of member variable phoneFax
 *
 * @author Roy Terrell.
 */
  public void setPhoneFax(String value) {
    this.phoneFax = value;
  }
/**
 * Gets the value of member variable phoneFax
 *
 * @author Roy Terrell.
 */
  public String getPhoneFax() {
    return this.phoneFax;
  }
/**
 * Sets the value of member variable phonePager
 *
 * @author Roy Terrell.
 */
  public void setPhonePager(String value) {
    this.phonePager = value;
  }
/**
 * Gets the value of member variable phonePager
 *
 * @author Roy Terrell.
 */
  public String getPhonePager() {
    return this.phonePager;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}