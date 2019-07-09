package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_pref database table/view.
 *
 * @author auto generated.
 */
public class ProjPref extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjPrefView

/** The property name constant equivalent to property, EndPeriodDay, of respective DataSource view. */
  public static final String PROP_ENDPERIODDAY = "EndPeriodDay";
/** The property name constant equivalent to property, EventSubmitFreq, of respective DataSource view. */
  public static final String PROP_EVENTSUBMITFREQ = "EventSubmitFreq";



	/** The javabean property equivalent of database column proj_pref.end_period_day */
  private String endPeriodDay;
/** The javabean property equivalent of database column proj_pref.event_submit_freq */
  private String eventSubmitFreq;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjPref() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable endPeriodDay
 *
 * @author auto generated.
 */
  public void setEndPeriodDay(String value) {
    this.endPeriodDay = value;
  }
/**
 * Gets the value of member variable endPeriodDay
 *
 * @author atuo generated.
 */
  public String getEndPeriodDay() {
    return this.endPeriodDay;
  }
/**
 * Sets the value of member variable eventSubmitFreq
 *
 * @author auto generated.
 */
  public void setEventSubmitFreq(String value) {
    this.eventSubmitFreq = value;
  }
/**
 * Gets the value of member variable eventSubmitFreq
 *
 * @author atuo generated.
 */
  public String getEventSubmitFreq() {
    return this.eventSubmitFreq;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}