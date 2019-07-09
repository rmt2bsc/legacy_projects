package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_employee_type database table/view.
 *
 * @author auto generated.
 */
public class ProjEmployeeType extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjEmployeeTypeView

/** The property name constant equivalent to property, EmpTypeId, of respective DataSource view. */
  public static final String PROP_EMPTYPEID = "EmpTypeId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column proj_employee_type.emp_type_id */
  private int empTypeId;
/** The javabean property equivalent of database column proj_employee_type.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjEmployeeType() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable empTypeId
 *
 * @author auto generated.
 */
  public void setEmpTypeId(int value) {
    this.empTypeId = value;
  }
/**
 * Gets the value of member variable empTypeId
 *
 * @author atuo generated.
 */
  public int getEmpTypeId() {
    return this.empTypeId;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}