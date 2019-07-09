package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_category database table/view.
 *
 * @author auto generated.
 */
public class XactCategory extends OrmBean {




	// Property name constants that belong to respective DataSource, XactCategoryView

/** The property name constant equivalent to property, XactCatgId, of respective DataSource view. */
  public static final String PROP_XACTCATGID = "XactCatgId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";



	/** The javabean property equivalent of database column xact_category.xact_catg_id */
  private int xactCatgId;
/** The javabean property equivalent of database column xact_category.description */
  private String description;
/** The javabean property equivalent of database column xact_category.code */
  private String code;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public XactCategory() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable xactCatgId
 *
 * @author auto generated.
 */
  public void setXactCatgId(int value) {
    this.xactCatgId = value;
  }
/**
 * Gets the value of member variable xactCatgId
 *
 * @author atuo generated.
 */
  public int getXactCatgId() {
    return this.xactCatgId;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable code
 *
 * @author auto generated.
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author atuo generated.
 */
  public String getCode() {
    return this.code;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}