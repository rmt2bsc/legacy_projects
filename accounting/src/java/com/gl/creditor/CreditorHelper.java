package com.gl.creditor;

import java.math.BigInteger;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.api.messaging.MessagingException;
import com.api.security.authentication.RMT2SessionBean;
import com.bean.Creditor;

import com.bean.bindings.JaxbAccountingFactory;
import com.bean.criteria.CreditorCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import com.gl.codes.GeneralCodesHelper;

import com.util.SystemException;

import com.xml.schema.bindings.BusinessContactCriteria;
import com.xml.schema.bindings.ObjectFactory;

/**
 * Serves as a helper class providing common functionality regarding the 
 * creditor module.
 * 
 * @author RTerrell
 * 
 */
public class CreditorHelper extends GeneralCodesHelper {
    public static final int CRITERIATYPE_CONTACT = 100;

    public static final int CRITERIATYPE_CREDITOR = 200;

    public static final int CRITERIATYPE_ALL = 300;
    
    private static Logger logger;

    private DatabaseConnectionBean dbCon;

    /** Creditor's balance */
    private Double balance;

    /** An ArrayList of Creditors */
    private List creditors;

    /** Creditor */
    private Object cred;

    /** Creditor's Business profile */
    private Object credDetail;

    private Object busTypes;

    private Object busServTypes;
    
    private int criteriaType;

    /** The creditor API object */
    protected CreditorApi api;

    
    /**
     * Default constructor
     */
    private CreditorHelper() {
	super();
	CreditorHelper.logger = Logger.getLogger(CreditorHelper.class);
	this.criteriaType = CreditorHelper.CRITERIATYPE_ALL;
    }

    /**
     * 
     * @param dbCon
     * @throws SystemException
     */
    protected CreditorHelper(DatabaseConnectionBean dbCon) throws SystemException {
	this();
    }

    /**
     * 
     * @param request
     * @param response
     * @param dbCon
     * @throws SystemException
     */
    protected CreditorHelper(Request request, Response response, DatabaseConnectionBean dbCon) throws SystemException {
	super(request, response);
	if (dbCon == null) {
	    throw new SystemException("Database connection is invalid");
	}
	this.dbCon = dbCon;
	try {
	    this.api = CreditorFactory.createApi(this.dbCon, this.request);
	}
	catch (Exception e) {
	    throw new SystemException(e.getMessage());
	}
    }

    /**
     * Return db connection to the connection pool
     * 
     * @throws DatabaseException
     */
    public void close() throws DatabaseException {
        if (this.dbCon != null) {
            this.dbCon.close();
        }
    }
    
    /**
     * Retrieves a single instance of creditor's detail data and its associated
     * address from the database using the creditor's internal id.
     * 
     * @param creditorId
     *            The creditor's internal id which is generally the primary key.
     * @throws CreditorException
     */
    public void fetchCreditor(int creditorId) throws CreditorException {
	int busId;
	try {
	    if (creditorId > 0) {
		this.cred = (Creditor) this.api.findById(creditorId);
		busId = ((Creditor) this.cred).getBusinessId();
	    }
	    else {
		this.cred = CreditorFactory.createCreditor();
		busId = 0;
	    }
	    
	    // Create JAXB criteria object
	    ObjectFactory f = new ObjectFactory();
	    BusinessContactCriteria wsCriteria = f.createBusinessContactCriteria();
	    wsCriteria.getBusinessId().add(BigInteger.valueOf(busId));
	    // Call web service.
	    RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	    JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory(); 
	    List<CreditorExt> extList = jaxbUtil.getCreditorContactData(wsCriteria, userSession.getLoginId());
	    
	    // Be sure to change the JSP's that refer to this.custDetail to expect 
	    // its runtime type to CustomerExt instead of VwBusinessAddress.
	    if (extList.size() == 1) {
		this.credDetail = extList.get(0).toXml();
	    }
	}
	catch (Exception e) {
	    throw new CreditorException(e);
	}

	try {
	    // Invoke service from the contacts app in order to obtain a list of
	    // business types
	    this.busTypes = this.getBusinessTypeCodes();
	    // Invoke service from the contacts app in order to obtain a list of
	    // business service types
	    this.busServTypes = this.getBusinessServiceTypeCodes();
	}
	catch (MessagingException e) {
	    throw new CreditorException(e);
	}

	// Get Creditor's Balance
	this.balance = this.getBalance(((Creditor) this.cred).getCreditorId());
    }
    
    /**
     * Obtains the creditor balance.
     * 
     * @param id
     *            The creditor Id
     * @return Creditor's balance
     */
    public Double getBalance(int id) {
	// Get Customer Balance
	Double balance = null;
	try {
	    balance = new Double(api.findCreditorBalance(id));
	}
	catch (CreditorException e) {
	    CreditorHelper.logger.log(Level.INFO, "Creditor balance could not be obtained...defaulting to zero.");
	    balance = new Double(0);
	}
	return balance;
    }
    
    
    /**
     * Builds selection criteria based on business contact data values, if available.  
     * Creditor criteria is represented by <i>creditor type id</i> and <i>account number</i>.   
     * <i>Name</i>, <i>tax id</i>, and <i>main phone number</i> are the only properties 
     * recognized as business contact data.  Creditor and business contact data are mutual 
     * exclusive when determining which criteria group is active.  
     * 
     * @param criteria 
     *           {@link CreditorCriteria} object containing the data values for build creditor selection criteria.
     * @return String
     *          The selection criteria.
     * @throws ActionHandlerException 
     *          When creditor values and business contact values are discovered for the same 
     *          search transaction.
     */
    public String buildCreditorCriteria(CreditorCriteria criteria) throws ActionHandlerException {
	this.validateCriteria(criteria);
	StringBuffer sql = new StringBuffer();
	if (this.criteriaType == CRITERIATYPE_CONTACT) {
	    if (!criteria.getQry_Name().equals("")) {
		sql.append(CreditorConst.CRITERIATAGS_CONTACT[0]);
		sql.append(" = ");
		sql.append(criteria.getQry_Name().trim());
	    }
	    if (!criteria.getQry_PhoneMain().equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append(CreditorConst.CRITERIATAGS_CONTACT[1]);
		sql.append(" = ");
		sql.append(criteria.getQry_PhoneMain().trim());
	    }
	    if (!criteria.getQry_TaxId().equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append(CreditorConst.CRITERIATAGS_CONTACT[2]);
		sql.append(" = ");
		sql.append(criteria.getQry_TaxId().trim());
	    }
	}
	if (this.criteriaType == CRITERIATYPE_CREDITOR) {
	    if (!criteria.getQry_AccountNo().equals("")) {
		sql.append(CreditorConst.CRITERIATAGS_CREDITOR[0]);
		sql.append(" like ");
		sql.append(criteria.getQry_AccountNo().trim());
	    }
	    if (!criteria.getQry_CreditorTypeId().equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append(CreditorConst.CRITERIATAGS_CREDITOR[1]);
		sql.append(" = ");
		sql.append(criteria.getQry_CreditorTypeId().trim());
	    }
        if (!criteria.getQry_CreditorId().equals("")) {
            if (sql.length() > 0) {
                sql.append(" and ");
            }
            sql.append(CreditorConst.CRITERIATAGS_CREDITOR[2]);
            sql.append(" = ");
            sql.append(criteria.getQry_CreditorId().trim());
        }
	}
	return sql.toString();
    }
    
    /**
     * Determines if search is to be performed using business contact criteria or 
     * creditor criteria based on the data submitted by the client.  Criteria is 
     * only valid when the availability of contact or creditor criteria is mutually 
     * exclusive.  Otherwise, an error is thrown.
     * 
     * @param criteria 
     *           Business contact or creditor selection criteria data.
     * @throws ActionHandlerException 
     *           When bothe the creditor and contact criteria is present.
     */
    public int validateCriteria(CreditorCriteria criteria) throws ActionHandlerException {
	boolean useCredParms = false;
	boolean useContactParms = false;

	useCredParms = (!criteria.getQry_CreditorTypeId().equals("") || !criteria.getQry_AccountNo().equals(""));
	useContactParms = (!criteria.getQry_TaxId().equals("") || !criteria.getQry_Name().equals("") || !criteria.getQry_PhoneMain().equals(""));

	if (useCredParms && useContactParms) {
	    this.msg = "The availability of creditor and business contact criteria must be mutually exclusive";
	    CreditorHelper.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	if (useCredParms) {
	    this.criteriaType = CreditorHelper.CRITERIATYPE_CREDITOR;
	}
	if (useContactParms) {
	    this.criteriaType = CreditorHelper.CRITERIATYPE_CONTACT;
	}
	if (!useContactParms && !useCredParms) {
	    this.criteriaType = CreditorHelper.CRITERIATYPE_ALL;
	}
	return this.criteriaType;
    }
    
    

    /**
     * @return the balance
     */
    public Double getBalance() {
	return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Double balance) {
	this.balance = balance;
    }

    /**
     * @return the busServTypes
     */
    public Object getBusServTypes() {
	return busServTypes;
    }

    /**
     * @param busServTypes the busServTypes to set
     */
    public void setBusServTypes(Object busServTypes) {
	this.busServTypes = busServTypes;
    }

    /**
     * @return the busTypes
     */
    public Object getBusTypes() {
	return busTypes;
    }

    /**
     * @param busTypes the busTypes to set
     */
    public void setBusTypes(Object busTypes) {
	this.busTypes = busTypes;
    }

    /**
     * @return the cred
     */
    public Object getCred() {
	return cred;
    }

    /**
     * @param cred the cred to set
     */
    public void setCred(Object cred) {
	this.cred = cred;
    }

    /**
     * @return the credDetail
     */
    public Object getCredDetail() {
	return credDetail;
    }

    /**
     * @param credDetail the credDetail to set
     */
    public void setCredDetail(Object credDetail) {
	this.credDetail = credDetail;
    }

    /**
     * @return the creditors
     */
    public List getCreditors() {
	return creditors;
    }

    /**
     * @param creditors the creditors to set
     */
    public void setCreditors(List creditors) {
	this.creditors = creditors;
    }

    /**
     * @return the criteriaType
     */
    public int getCriteriaType() {
        return criteriaType;
    }

}
