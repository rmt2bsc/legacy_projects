package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_category database table/view.
 *
 * @author Roy Terrell.
 */
public class XactCategory extends OrmBean {

/** The javabean property equivalent of database column xact_category.id */
  private int id;
/** The javabean property equivalent of database column xact_category.description */
  private String description;
/** The javabean property equivalent of database column xact_category.code */
  private String code;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public XactCategory() throws SystemException {
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
 * Sets the value of member variable code
 *
 * @author Roy Terrell.
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author Roy Terrell.
 */
  public String getCode() {
    return this.code;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}