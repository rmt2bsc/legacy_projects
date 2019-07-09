package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_account_state database table/view.
 *
 * @author Roy Terrell.
 */
public class GlAccountState extends OrmBean {

/** The javabean property equivalent of database column gl_account_state.id */
  private int id;
/** The javabean property equivalent of database column gl_account_state.gl_accoun_type_id */
  private int glAccounTypeId;
/** The javabean property equivalent of database column gl_account_state.inc_multiplier */
  private int incMultiplier;
/** The javabean property equivalent of database column gl_account_state.dec_multiplier */
  private int decMultiplier;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public GlAccountState() throws SystemException {
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
 * Sets the value of member variable glAccounTypeId
 *
 * @author Roy Terrell.
 */
  public void setGlAccounTypeId(int value) {
    this.glAccounTypeId = value;
  }
/**
 * Gets the value of member variable glAccounTypeId
 *
 * @author Roy Terrell.
 */
  public int getGlAccounTypeId() {
    return this.glAccounTypeId;
  }
/**
 * Sets the value of member variable incMultiplier
 *
 * @author Roy Terrell.
 */
  public void setIncMultiplier(int value) {
    this.incMultiplier = value;
  }
/**
 * Gets the value of member variable incMultiplier
 *
 * @author Roy Terrell.
 */
  public int getIncMultiplier() {
    return this.incMultiplier;
  }
/**
 * Sets the value of member variable decMultiplier
 *
 * @author Roy Terrell.
 */
  public void setDecMultiplier(int value) {
    this.decMultiplier = value;
  }
/**
 * Gets the value of member variable decMultiplier
 *
 * @author Roy Terrell.
 */
  public int getDecMultiplier() {
    return this.decMultiplier;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}