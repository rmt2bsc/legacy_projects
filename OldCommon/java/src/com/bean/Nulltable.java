package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the nulltable database table/view.
 *
 * @author Roy Terrell.
 */
public class Nulltable extends OrmBean {

/** The javabean property equivalent of database column nulltable.nullcol */
  private int nullcol;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public Nulltable() throws SystemException {
  }
/**
 * Sets the value of member variable nullcol
 *
 * @author Roy Terrell.
 */
  public void setNullcol(int value) {
    this.nullcol = value;
  }
/**
 * Gets the value of member variable nullcol
 *
 * @author Roy Terrell.
 */
  public int getNullcol() {
    return this.nullcol;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}