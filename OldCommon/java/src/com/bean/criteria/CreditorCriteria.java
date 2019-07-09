package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps creditor selection criteria.
 * <p>
 * <b><u>NOTE</u></b>:&nbsp; This class did not capitalize the first
 * character following the prefix, "qry_", of its property names and still
 * functions successfully using the introspection methods of DataSourceAdapter
 * class.
 * 
 * @author Roy Terrell.
 */
public class CreditorCriteria extends AncestorQueryCriteria {
	private String qry_glAccountId;
	private String qry_accountNo;
	private String qry_personId;
	private String qry_businessId;
	private String qry_creditLimit;
	private String qry_creditorTypeId;
	private String qry_busType;
	private String qry_servType;
	private String qry_longname;
	private String qry_shortname;
	private String qry_contactFirstname;
	private String qry_contactLastname;
	private String qry_contactPhone;
	private String qry_contactExt;
	private String qry_taxId;
	private String qry_website;


	/**
	 * Default constructor.
	 * 
	 * @author Roy Terrell.
	 */
	private CreditorCriteria() throws SystemException {
		super();
	}

	/**
	 * Creates an instance of CreditorCriteria class.
	 * 
	 * @return {@link CreditorCriteria}
	 */
	public static CreditorCriteria getInstance() {
		try {
			return new CreditorCriteria();
		} catch (Exception e) {
			return null;
		}
	}
	
	  /**
	   * Initializes all properties to spaces.
	   *
	   * @author Roy Terrell.
	   */
	  	public void initBean() throws SystemException {
	  		super.initBean();
	  		this.qry_glAccountId = "";
	  		this.qry_accountNo = "";
	  		this.qry_personId = "";
	  		this.qry_businessId = "";
	  		this.qry_creditLimit = "";
	  		this.qry_creditorTypeId = "";
	  		this.qry_busType = "";
	  		this.qry_servType = "";
	  		this.qry_longname = "";
	  		this.qry_shortname = "";
	  		this.qry_contactFirstname = "";
	  		this.qry_contactLastname = "";
	  		this.qry_contactPhone = "";
	  		this.qry_contactExt = "";
	  		this.qry_taxId = "";
	  		this.qry_website = "";
	  	}

	/**
	 * Sets the value of member variable glAccountId
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_GlAccountId(String value) {
		this.qry_glAccountId = value;
	}

	/**
	 * Gets the value of member variable glAccountId
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_GlAccountId() {
		return this.qry_glAccountId;
	}

	/**
	 * Sets the value of member variable accountNo
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_AccountNo(String value) {
		this.qry_accountNo = value;
	}

	/**
	 * Gets the value of member variable accountNo
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_AccountNo() {
		return this.qry_accountNo;
	}

	/**
	 * Sets the value of member variable personId
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_PersonId(String value) {
		this.qry_personId = value;
	}

	/**
	 * Gets the value of member variable personId
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_PersonId() {
		return this.qry_personId;
	}

	/**
	 * Sets the value of member variable businessId
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_BusinessId(String value) {
		this.qry_businessId = value;
	}

	/**
	 * Gets the value of member variable businessId
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_BusinessId() {
		return this.qry_businessId;
	}

	/**
	 * Sets the value of member variable creditLimit
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_CreditLimit(String value) {
		this.qry_creditLimit = value;
	}

	/**
	 * Gets the value of member variable creditLimit
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_CreditLimit() {
		return this.qry_creditLimit;
	}

	/**
	 * Sets the value of member variable creditorTypeId
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_CreditorTypeId(String value) {
		this.qry_creditorTypeId = value;
	}

	/**
	 * Gets the value of member variable creditorTypeId
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_CreditorTypeId() {
		return this.qry_creditorTypeId;
	}

	/**
	 * Sets the value of member variable businessBusType
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_BusType(String value) {
		this.qry_busType = value;
	}

	/**
	 * Gets the value of member variable businessBusType
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_BusType() {
		return this.qry_busType;
	}

	/**
	 * Sets the value of member variable businessServType
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_ServType(String value) {
		this.qry_servType = value;
	}

	/**
	 * Gets the value of member variable businessServType
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_ServType() {
		return this.qry_servType;
	}

	/**
	 * Sets the value of member variable businessLongname
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_Longname(String value) {
		this.qry_longname = value;
	}

	/**
	 * Gets the value of member variable businessLongname
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_Longname() {
		return this.qry_longname;
	}

	/**
	 * Sets the value of member variable businessShortname
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_Shortname(String value) {
		this.qry_shortname = value;
	}

	/**
	 * Gets the value of member variable businessShortname
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_Shortname() {
		return this.qry_shortname;
	}

	/**
	 * Sets the value of member variable businessContactFirstname
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_ContactFirstname(String value) {
		this.qry_contactFirstname = value;
	}

	/**
	 * Gets the value of member variable businessContactFirstname
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_ContactFirstname() {
		return this.qry_contactFirstname;
	}

	/**
	 * Sets the value of member variable businessContactLastname
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_ContactLastname(String value) {
		this.qry_contactLastname = value;
	}

	/**
	 * Gets the value of member variable businessContactLastname
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_ContactLastname() {
		return this.qry_contactLastname;
	}

	/**
	 * Sets the value of member variable businessContactPhone
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_ContactPhone(String value) {
		this.qry_contactPhone = value;
	}

	/**
	 * Gets the value of member variable businessContactPhone
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_ContactPhone() {
		return this.qry_contactPhone;
	}

	/**
	 * Sets the value of member variable businessContactExt
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_ContactExt(String value) {
		this.qry_contactExt = value;
	}

	/**
	 * Gets the value of member variable businessContactExt
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_ContactExt() {
		return this.qry_contactExt;
	}

	/**
	 * Sets the value of member variable businessTaxId
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_TaxId(String value) {
		this.qry_taxId = value;
	}

	/**
	 * Gets the value of member variable businessTaxId
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_TaxId() {
		return this.qry_taxId;
	}

	/**
	 * Sets the value of member variable businessWebsite
	 * 
	 * @author Roy Terrell.
	 */
	public void setQry_Website(String value) {
		this.qry_website = value;
	}

	/**
	 * Gets the value of member variable businessWebsite
	 * 
	 * @author Roy Terrell.
	 */
	public String getQry_Website() {
		return this.qry_website;
	}
}