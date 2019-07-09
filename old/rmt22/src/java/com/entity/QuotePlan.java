package com.entity;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the quote_plan database table/view.
 *
 * @author auto generated.
 */
public class QuotePlan extends OrmBean {




	// Property name constants that belong to respective DataSource, QuotePlanView

/** The property name constant equivalent to property, QuotePlanId, of respective DataSource view. */
  public static final String PROP_QUOTEPLANID = "QuotePlanId";
/** The property name constant equivalent to property, QuoteId, of respective DataSource view. */
  public static final String PROP_QUOTEID = "QuoteId";
/** The property name constant equivalent to property, CompanyDescr, of respective DataSource view. */
  public static final String PROP_COMPANYDESCR = "CompanyDescr";
/** The property name constant equivalent to property, WebsitePurpose, of respective DataSource view. */
  public static final String PROP_WEBSITEPURPOSE = "WebsitePurpose";
/** The property name constant equivalent to property, TargetAudience, of respective DataSource view. */
  public static final String PROP_TARGETAUDIENCE = "TargetAudience";
/** The property name constant equivalent to property, ProposedDeadline, of respective DataSource view. */
  public static final String PROP_PROPOSEDDEADLINE = "ProposedDeadline";
/** The property name constant equivalent to property, HaveLogo, of respective DataSource view. */
  public static final String PROP_HAVELOGO = "HaveLogo";
/** The property name constant equivalent to property, NeedLogo, of respective DataSource view. */
  public static final String PROP_NEEDLOGO = "NeedLogo";
/** The property name constant equivalent to property, BudgetRange, of respective DataSource view. */
  public static final String PROP_BUDGETRANGE = "BudgetRange";
/** The property name constant equivalent to property, TotalPages, of respective DataSource view. */
  public static final String PROP_TOTALPAGES = "TotalPages";
/** The property name constant equivalent to property, HaveContentStruct, of respective DataSource view. */
  public static final String PROP_HAVECONTENTSTRUCT = "HaveContentStruct";
/** The property name constant equivalent to property, ContentDetails, of respective DataSource view. */
  public static final String PROP_CONTENTDETAILS = "ContentDetails";
/** The property name constant equivalent to property, NeedContentStruct, of respective DataSource view. */
  public static final String PROP_NEEDCONTENTSTRUCT = "NeedContentStruct";
/** The property name constant equivalent to property, InterestWebsites, of respective DataSource view. */
  public static final String PROP_INTERESTWEBSITES = "InterestWebsites";
/** The property name constant equivalent to property, LookAndFeel, of respective DataSource view. */
  public static final String PROP_LOOKANDFEEL = "LookAndFeel";
/** The property name constant equivalent to property, FuncCritical, of respective DataSource view. */
  public static final String PROP_FUNCCRITICAL = "FuncCritical";
/** The property name constant equivalent to property, NeedCms, of respective DataSource view. */
  public static final String PROP_NEEDCMS = "NeedCms";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";



	/** The javabean property equivalent of database column quote_plan.quote_plan_id */
  private int quotePlanId;
/** The javabean property equivalent of database column quote_plan.quote_id */
  private int quoteId;
/** The javabean property equivalent of database column quote_plan.company_descr */
  private String companyDescr;
/** The javabean property equivalent of database column quote_plan.website_purpose */
  private String websitePurpose;
/** The javabean property equivalent of database column quote_plan.target_audience */
  private String targetAudience;
/** The javabean property equivalent of database column quote_plan.proposed_deadline */
  private java.util.Date proposedDeadline;
/** The javabean property equivalent of database column quote_plan.have_logo */
  private int haveLogo;
/** The javabean property equivalent of database column quote_plan.need_logo */
  private int needLogo;
/** The javabean property equivalent of database column quote_plan.budget_range */
  private String budgetRange;
/** The javabean property equivalent of database column quote_plan.total_pages */
  private int totalPages;
/** The javabean property equivalent of database column quote_plan.have_content_struct */
  private int haveContentStruct;
/** The javabean property equivalent of database column quote_plan.content_details */
  private String contentDetails;
/** The javabean property equivalent of database column quote_plan.need_content_struct */
  private int needContentStruct;
/** The javabean property equivalent of database column quote_plan.interest_websites */
  private String interestWebsites;
/** The javabean property equivalent of database column quote_plan.look_and_feel */
  private String lookAndFeel;
/** The javabean property equivalent of database column quote_plan.func_critical */
  private String funcCritical;
/** The javabean property equivalent of database column quote_plan.need_cms */
  private int needCms;
/** The javabean property equivalent of database column quote_plan.comments */
  private String comments;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public QuotePlan() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable quotePlanId
 *
 * @author auto generated.
 */
  public void setQuotePlanId(int value) {
    this.quotePlanId = value;
  }
/**
 * Gets the value of member variable quotePlanId
 *
 * @author atuo generated.
 */
  public int getQuotePlanId() {
    return this.quotePlanId;
  }
/**
 * Sets the value of member variable quoteId
 *
 * @author auto generated.
 */
  public void setQuoteId(int value) {
    this.quoteId = value;
  }
/**
 * Gets the value of member variable quoteId
 *
 * @author atuo generated.
 */
  public int getQuoteId() {
    return this.quoteId;
  }
/**
 * Sets the value of member variable companyDescr
 *
 * @author auto generated.
 */
  public void setCompanyDescr(String value) {
    this.companyDescr = value;
  }
/**
 * Gets the value of member variable companyDescr
 *
 * @author atuo generated.
 */
  public String getCompanyDescr() {
    return this.companyDescr;
  }
/**
 * Sets the value of member variable websitePurpose
 *
 * @author auto generated.
 */
  public void setWebsitePurpose(String value) {
    this.websitePurpose = value;
  }
/**
 * Gets the value of member variable websitePurpose
 *
 * @author atuo generated.
 */
  public String getWebsitePurpose() {
    return this.websitePurpose;
  }
/**
 * Sets the value of member variable targetAudience
 *
 * @author auto generated.
 */
  public void setTargetAudience(String value) {
    this.targetAudience = value;
  }
/**
 * Gets the value of member variable targetAudience
 *
 * @author atuo generated.
 */
  public String getTargetAudience() {
    return this.targetAudience;
  }
/**
 * Sets the value of member variable proposedDeadline
 *
 * @author auto generated.
 */
  public void setProposedDeadline(java.util.Date value) {
    this.proposedDeadline = value;
  }
/**
 * Gets the value of member variable proposedDeadline
 *
 * @author atuo generated.
 */
  public java.util.Date getProposedDeadline() {
    return this.proposedDeadline;
  }
/**
 * Sets the value of member variable haveLogo
 *
 * @author auto generated.
 */
  public void setHaveLogo(int value) {
    this.haveLogo = value;
  }
/**
 * Gets the value of member variable haveLogo
 *
 * @author atuo generated.
 */
  public int getHaveLogo() {
    return this.haveLogo;
  }
/**
 * Sets the value of member variable needLogo
 *
 * @author auto generated.
 */
  public void setNeedLogo(int value) {
    this.needLogo = value;
  }
/**
 * Gets the value of member variable needLogo
 *
 * @author atuo generated.
 */
  public int getNeedLogo() {
    return this.needLogo;
  }
/**
 * Sets the value of member variable budgetRange
 *
 * @author auto generated.
 */
  public void setBudgetRange(String value) {
    this.budgetRange = value;
  }
/**
 * Gets the value of member variable budgetRange
 *
 * @author atuo generated.
 */
  public String getBudgetRange() {
    return this.budgetRange;
  }
/**
 * Sets the value of member variable totalPages
 *
 * @author auto generated.
 */
  public void setTotalPages(int value) {
    this.totalPages = value;
  }
/**
 * Gets the value of member variable totalPages
 *
 * @author atuo generated.
 */
  public int getTotalPages() {
    return this.totalPages;
  }
/**
 * Sets the value of member variable haveContentStruct
 *
 * @author auto generated.
 */
  public void setHaveContentStruct(int value) {
    this.haveContentStruct = value;
  }
/**
 * Gets the value of member variable haveContentStruct
 *
 * @author atuo generated.
 */
  public int getHaveContentStruct() {
    return this.haveContentStruct;
  }
/**
 * Sets the value of member variable contentDetails
 *
 * @author auto generated.
 */
  public void setContentDetails(String value) {
    this.contentDetails = value;
  }
/**
 * Gets the value of member variable contentDetails
 *
 * @author atuo generated.
 */
  public String getContentDetails() {
    return this.contentDetails;
  }
/**
 * Sets the value of member variable needContentStruct
 *
 * @author auto generated.
 */
  public void setNeedContentStruct(int value) {
    this.needContentStruct = value;
  }
/**
 * Gets the value of member variable needContentStruct
 *
 * @author atuo generated.
 */
  public int getNeedContentStruct() {
    return this.needContentStruct;
  }
/**
 * Sets the value of member variable interestWebsites
 *
 * @author auto generated.
 */
  public void setInterestWebsites(String value) {
    this.interestWebsites = value;
  }
/**
 * Gets the value of member variable interestWebsites
 *
 * @author atuo generated.
 */
  public String getInterestWebsites() {
    return this.interestWebsites;
  }
/**
 * Sets the value of member variable lookAndFeel
 *
 * @author auto generated.
 */
  public void setLookAndFeel(String value) {
    this.lookAndFeel = value;
  }
/**
 * Gets the value of member variable lookAndFeel
 *
 * @author atuo generated.
 */
  public String getLookAndFeel() {
    return this.lookAndFeel;
  }
/**
 * Sets the value of member variable funcCritical
 *
 * @author auto generated.
 */
  public void setFuncCritical(String value) {
    this.funcCritical = value;
  }
/**
 * Gets the value of member variable funcCritical
 *
 * @author atuo generated.
 */
  public String getFuncCritical() {
    return this.funcCritical;
  }
/**
 * Sets the value of member variable needCms
 *
 * @author auto generated.
 */
  public void setNeedCms(int value) {
    this.needCms = value;
  }
/**
 * Gets the value of member variable needCms
 *
 * @author atuo generated.
 */
  public int getNeedCms() {
    return this.needCms;
  }
/**
 * Sets the value of member variable comments
 *
 * @author auto generated.
 */
  public void setComments(String value) {
    this.comments = value;
  }
/**
 * Gets the value of member variable comments
 *
 * @author atuo generated.
 */
  public String getComments() {
    return this.comments;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}