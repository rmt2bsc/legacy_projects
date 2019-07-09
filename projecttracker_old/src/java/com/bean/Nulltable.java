package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the nulltable database table/view.
 *
 * @author auto generated.
 */
public class Nulltable extends OrmBean {




	// Property name constants that belong to respective DataSource, NulltableView

/** The property name constant equivalent to property, Nullcol, of respective DataSource view. */
  public static final String PROP_NULLCOL = "Nullcol";



	/** The javabean property equivalent of database column nulltable.nullcol */
  private int nullcol;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Nulltable() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable nullcol
 *
 * @author auto generated.
 */
  public void setNullcol(int value) {
    this.nullcol = value;
  }
/**
 * Gets the value of member variable nullcol
 *
 * @author atuo generated.
 */
  public int getNullcol() {
    return this.nullcol;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}