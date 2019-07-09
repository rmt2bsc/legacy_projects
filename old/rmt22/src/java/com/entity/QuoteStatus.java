package com.entity;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the quote_status database table/view.
 *
 * @author auto generated.
 */
public class QuoteStatus extends OrmBean {




	// Property name constants that belong to respective DataSource, QuoteStatusView

/** The property name constant equivalent to property, QuoteStatusId, of respective DataSource view. */
  public static final String PROP_QUOTESTATUSID = "QuoteStatusId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column quote_status.quote_status_id */
  private int quoteStatusId;
/** The javabean property equivalent of database column quote_status.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public QuoteStatus() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable quoteStatusId
 *
 * @author auto generated.
 */
  public void setQuoteStatusId(int value) {
    this.quoteStatusId = value;
  }
/**
 * Gets the value of member variable quoteStatusId
 *
 * @author atuo generated.
 */
  public int getQuoteStatusId() {
    return this.quoteStatusId;
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