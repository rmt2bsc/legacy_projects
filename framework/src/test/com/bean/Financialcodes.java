package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the financialcodes database table/view.
 *
 * @author auto generated.
 */
public class Financialcodes extends OrmBean {




	// Property name constants that belong to respective DataSource, FinancialcodesView

/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";
/** The property name constant equivalent to property, Type, of respective DataSource view. */
  public static final String PROP_TYPE = "Type";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column financialcodes.code */
  private char code;
/** The javabean property equivalent of database column financialcodes.type */
  private char type;
/** The javabean property equivalent of database column financialcodes.description */
  private char description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Financialcodes() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable code
 *
 * @author auto generated.
 */
  public void setCode(char value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author atuo generated.
 */
  public char getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable type
 *
 * @author auto generated.
 */
  public void setType(char value) {
    this.type = value;
  }
/**
 * Gets the value of member variable type
 *
 * @author atuo generated.
 */
  public char getType() {
    return this.type;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(char value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public char getDescription() {
    return this.description;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}