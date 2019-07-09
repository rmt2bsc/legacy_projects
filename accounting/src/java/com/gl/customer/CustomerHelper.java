package com.gl.customer;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;
import com.api.messaging.MessagingException;
import com.api.security.authentication.RMT2SessionBean;
import com.bean.Customer;

import com.bean.bindings.JaxbAccountingFactory;
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
 * customer module.
 * 
 * @author appdev
 *
 */
public class CustomerHelper extends GeneralCodesHelper {
    private Logger logger;

    private DatabaseConnectionBean dbCon;

    /** Customer's balance */
    private Double balance;

    /** An ArrayList of Customers */
    private List customers;

    /** Customers */
    private Object cust;

    /** Customer's Business of Personal profile */
    private Object custDetail;

    private Object busTypes;

    private Object busServTypes;

    /** The customer API object */
    protected CustomerApi api;


    /**
     * Default constructor
     */
    private CustomerHelper() {
	this.logger = Logger.getLogger("CustomerHelper");
    }

    /**
     * Creates a CustomerHelper object which is iniitialized with a DatabaseConnectionBean.
     * 
     * @param dbCon The database connection bean.
     * @throws SystemException
     */
    protected CustomerHelper(DatabaseConnectionBean dbCon) throws SystemException {
	this();
	if (dbCon == null) {
	    throw new SystemException("Database connection is invalid");
	}
	this.dbCon = dbCon;
    }

    /**
     * Creates a CustomerHelper object which is iniitialized with a DatabaseConnectionBean, 
     * user's request, and user's response.
     * 
     * @param request The user's request object
     * @param response The user's response object
     * @param dbCon The database connection bean.
     * @throws SystemException
     */
    protected CustomerHelper(Request request, Response response, DatabaseConnectionBean dbCon) throws SystemException {
	super(request, response);
	if (dbCon == null) {
	    throw new SystemException("Database connection is invalid");
	}
	this.dbCon = dbCon;
	try {
	    this.api = CustomerFactory.createApi(this.dbCon, this.request);
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
     * Retrieves a single instance of customer's detail data and its associated 
     * address from the database using the customer's internal id. 
     * 
     * @param customerId The customer's internal id which is generally the primary key.
     * @throws CustomerException
     */
    public void fetchCustomer(int customerId) throws CustomerException {
	int busId;
	try {
	    if (customerId > 0) {
		this.cust = (Customer) this.api.findById(customerId);
		busId = ((Customer) this.cust).getBusinessId();
	    }
	    else {
		this.cust = CustomerFactory.createCustomer();
		busId = 0;
	    }
	    
	    // Create JAXB criteria object
	    ObjectFactory f = new ObjectFactory();
	    BusinessContactCriteria wsCriteria = f.createBusinessContactCriteria();
	    wsCriteria.getBusinessId().add(BigInteger.valueOf(busId));
	    // Call web service.
	    RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	    JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory(); 
	    List<CustomerExt> extList = jaxbUtil.getCustomerContactData(wsCriteria, userSession.getLoginId());
	    // Be sure to change the JSP's that refer to this.custDetail to expect 
	    // its runtime type to CustomerExt instead of VwBusinessAddress.
	    if (extList.size() == 1) {
		this.custDetail = extList.get(0).toXml();
	    }
	}
	catch (Exception e) {
	    throw new CustomerException(e);
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
	    throw new CustomerException(e);
	}

	//  Get Customer's Balance
	this.balance = this.getBalance(((Customer) this.cust).getCustomerId());
    }

    /**
     * Obtains the customer balance.
     * 
     * @param id The customer Id
     * @return Customer's balance
     */
    public Double getBalance(int id) {
	// Get Customer Balance
	Double balance = null;
	try {
	    balance = new Double(api.findCustomerBalance(id));
	}
	catch (CustomerException e) {
	    logger.log(Level.INFO, "Customer balance could not be obtained...defaulting to zero.");
	    balance = new Double(0);
	}
	return balance;
    }

    /**
     * @return the balance
     */
    public Double getBalance() {
	return balance;
    }

    /**
     * @return the busServTypes
     */
    public Object getBusServTypes() {
	return busServTypes;
    }

    /**
     * @return the busTypes
     */
    public Object getBusTypes() {
	return busTypes;
    }

    /**
     * @return the cust
     */
    public Object getCust() {
	return cust;
    }

    /**
     * @return the custDetail
     */
    public Object getCustDetail() {
	return custDetail;
    }

    /**
     * @return the customers
     */
    public List getCustomers() {
	return customers;
    }

}
