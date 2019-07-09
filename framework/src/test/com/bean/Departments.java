package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the departments database table/view.
 *
 * @author auto generated.
 */
public class Departments extends OrmBean {




	// Property name constants that belong to respective DataSource, DepartmentsView

/** The property name constant equivalent to property, Departmentid, of respective DataSource view. */
  public static final String PROP_DEPARTMENTID = "Departmentid";
/** The property name constant equivalent to property, Departmentname, of respective DataSource view. */
  public static final String PROP_DEPARTMENTNAME = "Departmentname";
/** The property name constant equivalent to property, Departmentheadid, of respective DataSource view. */
  public static final String PROP_DEPARTMENTHEADID = "Departmentheadid";



	/** The javabean property equivalent of database column departments.departmentid */
  private int departmentid;
/** The javabean property equivalent of database column departments.departmentname */
  private char departmentname;
/** The javabean property equivalent of database column departments.departmentheadid */
  private int departmentheadid;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Departments() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable departmentid
 *
 * @author auto generated.
 */
  public void setDepartmentid(int value) {
    this.departmentid = value;
  }
/**
 * Gets the value of member variable departmentid
 *
 * @author atuo generated.
 */
  public int getDepartmentid() {
    return this.departmentid;
  }
/**
 * Sets the value of member variable departmentname
 *
 * @author auto generated.
 */
  public void setDepartmentname(char value) {
    this.departmentname = value;
  }
/**
 * Gets the value of member variable departmentname
 *
 * @author atuo generated.
 */
  public char getDepartmentname() {
    return this.departmentname;
  }
/**
 * Sets the value of member variable departmentheadid
 *
 * @author auto generated.
 */
  public void setDepartmentheadid(int value) {
    this.departmentheadid = value;
  }
/**
 * Gets the value of member variable departmentheadid
 *
 * @author atuo generated.
 */
  public int getDepartmentheadid() {
    return this.departmentheadid;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}