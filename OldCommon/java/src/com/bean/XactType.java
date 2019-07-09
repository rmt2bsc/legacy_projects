package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_type database table/view.
 *
 * @author Roy Terrell.
 */
public class XactType extends OrmBean {

/** The javabean property equivalent of database column xact_type.id */
  private int id;
/** The javabean property equivalent of database column xact_type.xact_category_id */
  private int xactCategoryId;
/** The javabean property equivalent of database column xact_type.description */
  private String description;
/** The javabean property equivalent of database column xact_type.xact_code */
  private String xactCode;
/** The javabean property equivalent of database column xact_type.to_multiplier */
  private int toMultiplier;
/** The javabean property equivalent of database column xact_type.from_multiplier */
  private int fromMultiplier;
/** The javabean property equivalent of database column xact_type.to_acct_type_id */
  private int toAcctTypeId;
/** The javabean property equivalent of database column xact_type.to_acct_catg_id */
  private int toAcctCatgId;
/** The javabean property equivalent of database column xact_type.from_acct_type_id */
  private int fromAcctTypeId;
/** The javabean property equivalent of database column xact_type.from_acct_catg_id */
  private int fromAcctCatgId;
/** The javabean property equivalent of database column xact_type.has_subsidiary */
  private int hasSubsidiary;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public XactType() throws SystemException {
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
 * Sets the value of member variable xactCategoryId
 *
 * @author Roy Terrell.
 */
  public void setXactCategoryId(int value) {
    this.xactCategoryId = value;
  }
/**
 * Gets the value of member variable xactCategoryId
 *
 * @author Roy Terrell.
 */
  public int getXactCategoryId() {
    return this.xactCategoryId;
  }
/**
 * Sets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable xactCode
 *
 * @author Roy Terrell.
 */
  public void setXactCode(String value) {
    this.xactCode = value;
  }
/**
 * Gets the value of member variable xactCode
 *
 * @author Roy Terrell.
 */
  public String getXactCode() {
    return this.xactCode;
  }
/**
 * Sets the value of member variable toMultiplier
 *
 * @author Roy Terrell.
 */
  public void setToMultiplier(int value) {
    this.toMultiplier = value;
  }
/**
 * Gets the value of member variable toMultiplier
 *
 * @author Roy Terrell.
 */
  public int getToMultiplier() {
    return this.toMultiplier;
  }
/**
 * Sets the value of member variable fromMultiplier
 *
 * @author Roy Terrell.
 */
  public void setFromMultiplier(int value) {
    this.fromMultiplier = value;
  }
/**
 * Gets the value of member variable fromMultiplier
 *
 * @author Roy Terrell.
 */
  public int getFromMultiplier() {
    return this.fromMultiplier;
  }
/**
 * Sets the value of member variable toAcctTypeId
 *
 * @author Roy Terrell.
 */
  public void setToAcctTypeId(int value) {
    this.toAcctTypeId = value;
  }
/**
 * Gets the value of member variable toAcctTypeId
 *
 * @author Roy Terrell.
 */
  public int getToAcctTypeId() {
    return this.toAcctTypeId;
  }
/**
 * Sets the value of member variable toAcctCatgId
 *
 * @author Roy Terrell.
 */
  public void setToAcctCatgId(int value) {
    this.toAcctCatgId = value;
  }
/**
 * Gets the value of member variable toAcctCatgId
 *
 * @author Roy Terrell.
 */
  public int getToAcctCatgId() {
    return this.toAcctCatgId;
  }
/**
 * Sets the value of member variable fromAcctTypeId
 *
 * @author Roy Terrell.
 */
  public void setFromAcctTypeId(int value) {
    this.fromAcctTypeId = value;
  }
/**
 * Gets the value of member variable fromAcctTypeId
 *
 * @author Roy Terrell.
 */
  public int getFromAcctTypeId() {
    return this.fromAcctTypeId;
  }
/**
 * Sets the value of member variable fromAcctCatgId
 *
 * @author Roy Terrell.
 */
  public void setFromAcctCatgId(int value) {
    this.fromAcctCatgId = value;
  }
/**
 * Gets the value of member variable fromAcctCatgId
 *
 * @author Roy Terrell.
 */
  public int getFromAcctCatgId() {
    return this.fromAcctCatgId;
  }
/**
 * Sets the value of member variable hasSubsidiary
 *
 * @author Roy Terrell.
 */
  public void setHasSubsidiary(int value) {
    this.hasSubsidiary = value;
  }
/**
 * Gets the value of member variable hasSubsidiary
 *
 * @author Roy Terrell.
 */
  public int getHasSubsidiary() {
    return this.hasSubsidiary;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}