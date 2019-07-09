package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_balance_type database table/view.
 *
 * @author auto generated.
 */
public class GlBalanceType extends OrmBean {




	// Property name constants that belong to respective DataSource, GlBalanceTypeView

/** The property name constant equivalent to property, AcctBaltypeId, of respective DataSource view. */
  public static final String PROP_ACCTBALTYPEID = "AcctBaltypeId";
/** The property name constant equivalent to property, LongDesc, of respective DataSource view. */
  public static final String PROP_LONGDESC = "LongDesc";
/** The property name constant equivalent to property, ShortDesc, of respective DataSource view. */
  public static final String PROP_SHORTDESC = "ShortDesc";



	/** The javabean property equivalent of database column gl_balance_type.acct_baltype_id */
  private int acctBaltypeId;
/** The javabean property equivalent of database column gl_balance_type.long_desc */
  private String longDesc;
/** The javabean property equivalent of database column gl_balance_type.short_desc */
  private String shortDesc;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public GlBalanceType() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctBaltypeId
 *
 * @author auto generated.
 */
  public void setAcctBaltypeId(int value) {
    this.acctBaltypeId = value;
  }
/**
 * Gets the value of member variable acctBaltypeId
 *
 * @author atuo generated.
 */
  public int getAcctBaltypeId() {
    return this.acctBaltypeId;
  }
/**
 * Sets the value of member variable longDesc
 *
 * @author auto generated.
 */
  public void setLongDesc(String value) {
    this.longDesc = value;
  }
/**
 * Gets the value of member variable longDesc
 *
 * @author atuo generated.
 */
  public String getLongDesc() {
    return this.longDesc;
  }
/**
 * Sets the value of member variable shortDesc
 *
 * @author auto generated.
 */
  public void setShortDesc(String value) {
    this.shortDesc = value;
  }
/**
 * Gets the value of member variable shortDesc
 *
 * @author atuo generated.
 */
  public String getShortDesc() {
    return this.shortDesc;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}