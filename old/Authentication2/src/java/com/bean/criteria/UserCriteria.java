package com.bean.criteria;

import com.bean.criteria.AncestorQueryCriteria;

import com.util.SystemException;

/**
 * The user criteria class used for tracking UI selection criteria for the User
 * Search page.
 * 
 * @author roy.terrell
 * 
 */
public class UserCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = 7946204820105663394L;
    private String qry_Username;
	private String qry_Description;
	private String qry_Password;
	private int qry_TotalLogons;
	private String qry_Active;
	private String qry_LoggedIn;
	private String qry_StartDate;
	private String qry_TerminationDate;
	private String qry_Firstname;
	private String qry_Midname;
	private String qry_Lastname;
	private String qry_BirthDate;
	private String qry_Ssn;
	private String qry_Email;


	/**
	 * Default constructor
	 * 
	 * @throws SystemException
	 */
	private UserCriteria() throws SystemException {
		super();
	}

	/**
	 * Creates a new instance of UserCriteria class.
	 * 
	 * @return {@link UserCriteria} or null if class can not be instantiated.
	 */
	public static UserCriteria getInstance() {
		try {
			return new UserCriteria();	
		}
		catch (SystemException e) {
			return null;
		}
	}
	/**
	 * @param value
	 */
	public void setQry_Username(String value) {
		this.qry_Username = value;
	}

	/**
	 * @return
	 */
	public String getQry_Username() {
		return this.qry_Username;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_Description(String value) {
		this.qry_Description = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Description() {
		return this.qry_Description;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_Password(String value) {
		this.qry_Password = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Password() {
		return this.qry_Password;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_totalLogons(int value) {
		this.qry_TotalLogons = value;
	}

	/**
	 * 
	 * @return
	 */
	public int getQry_totalLogons() {
		return this.qry_TotalLogons;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_Active(String value) {
		this.qry_Active = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Active() {
		return this.qry_Active;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_StartDate(String value) {
		this.qry_StartDate = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_StartDate() {
		return this.qry_StartDate;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_TerminationDate(String value) {
		this.qry_TerminationDate = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_TerminationDate() {
		return this.qry_TerminationDate;
	}

	
	/**
	 * 
	 * @param value
	 */
	public void setQry_Firstname(String value) {
		this.qry_Firstname = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Firstname() {
		return this.qry_Firstname;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_Midname(String value) {
		this.qry_Midname = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Midname() {
		return this.qry_Midname;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_Lastname(String value) {
		this.qry_Lastname = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Lastname() {
		return this.qry_Lastname;
	}


	/**
	 * 
	 * @param value
	 */
	public void setQry_BirthDate(String value) {
		this.qry_BirthDate = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_BirthDate() {
		return this.qry_BirthDate;
	}


	/**
	 * 
	 * @param value
	 */
	public void setQry_Ssn(String value) {
		this.qry_Ssn = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Ssn() {
		return this.qry_Ssn;
	}

	/**
	 * 
	 * @param value
	 */
	public void setQry_Email(String value) {
		this.qry_Email = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getQry_Email() {
		return this.qry_Email;
	}
	
	/**
	 * @return the qry_LoggedIn
	 */
	public String getQry_LoggedIn() {
	    return qry_LoggedIn;
	}

	 /**
	  * @param qry_LoggedIn the qry_LoggedIn to set
	  */
	 public void setQry_LoggedIn(String qry_LoggedIn) {      
	     this.qry_LoggedIn = qry_LoggedIn;
	 }
	    

	/**
	 * Initializes all non-numeric values.
	 */
	public void initBean() throws SystemException {
		super.initBean();
		this.qry_Username = "";
		this.qry_Description = "";
		this.qry_Password = "";
		this.qry_Firstname = "";
		this.qry_Midname = "";
		this.qry_Lastname = "";
		this.qry_Ssn = "";
		this.qry_Email = "";
		this.qry_StartDate = "";
		this.qry_TerminationDate = "";
		this.qry_BirthDate = "";
	}

   

}