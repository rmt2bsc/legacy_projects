package com.gl.customer;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceFactory;

import com.bean.Customer;
import com.bean.RMT2TagQueryBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

//import com.gl.BasicAccountingHelper;
import com.gl.BasicGLApi;

import com.util.SystemException;

/**
 * This abstarct class provides common functionality needed to serve various 
 * user interfaces pertaining to Customer maintenance.
 * 
 * @author Roy Terrell
 *
 */
public abstract class CustomerAction extends AbstractActionHandler implements ICommand {
    private Logger logger;

    /** Customer's balance */
    private Double balance;

    /** An ArrayList of Customers */
    protected List customers;

    /** Customers */
    protected Object cust;

    /** Customer's Business of Personal profile */
    private Object custDetail;

    private Object busTypes;

    private Object busServTypes;

    //    protected BasicAccountingHelper acctHelper;

    /**
     * Default constructor
     * 
     */
    public CustomerAction() {
        super();
        logger = Logger.getLogger("CustomerAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public CustomerAction(Context context, Request request) throws SystemException, DatabaseException {
        super(context, request);
        this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        super.init(context, request);
        //	this.acctHelper = new BasicAccountingHelper();
    }

    /**
     * Processes the client's request using request, response, and command.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        super.processRequest(request, response, command);
        this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
    }

    /**
     * Obtains key customer data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
        try {
            this.cust = CustomerFactory.createCustomer();
            DataSourceFactory.packageBean(this.request, this.cust);
        }
        catch (Exception e) {
            throw new ActionHandlerException(e.getMessage());
        }
    }

    /**
     * <p>
     * Gathers data for the following objects and returns the data to the client
     * via the HttpServletRequest object to be handle at the discretion of the client:
     * <p>
     * <table width="100%" border="1" cellspacing="0" cellpadding="0">
     * <tr>
     * <td><strong>Attribute</strong></td>
     * <td><strong>Type</strong></td>
     * <td><strong>Description</strong></td>
     * <td><strong>Attribute Name</strong></td>
     * </tr>
     * <tr>
     * <td>CUSTOMER</td>
     * <td>{@link Customer}</td>
     * <td>Customer data</td>
     * <td>customer</td>
     * </tr>
     * <tr>
     * <td>CUSTOMER LIST</td>
     * <td>List of {@link CustomerExt}</td>
     * <td>List of Customers</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>BUSINESS CONTACT</td>
     * <td>XML from contact application</td>
     * <td>Business and Address data</td>
     * <td>business</td>
     * </tr>
     * <tr>
     * <td>CUSTOMER BALANCE</td>
     * <td>Double</td>
     * <td>Customer's Balance</td>
     * <td>customerbalance</td>
     * </tr>
     * <tr>
     * <td>BUSINESS TYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Type data</td>
     * <td>businesstypes</td>
     * </tr>
     * <tr>
     * <td>BUSINESS SERVTYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Service Type data</td>
     * <td>businessservicetypes</td>
     * </tr>
     * <tr>
     * <td>MESSAGE</td>
     * <td>String</td>
     * <td>Server message</td>
     * <td>info</td>
     * </tr>
     * </table>
     * 
     * @throws ActionHandlerException
     */
    public void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(CustomerApi.CLIENT_DATA_CUSTOMER, this.cust);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.customers);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, this.custDetail);
        this.request.setAttribute(BasicGLApi.CLIENT_CUSTOMERBAL, this.balance);
        this.request.setAttribute(BasicGLApi.CLIENT_BUSTYPES, this.busTypes);
        this.request.setAttribute(BasicGLApi.CLIENT_BUSSERVTYPES, this.busServTypes);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Retrieves a single instance of customer's detail data and its associated 
     * address from the database using the customer's internal id. 
     * 
     * @param customerId The customer's internal id which is generally the primary key.
     * @throws ActionHandlerException
     */
    private void fetchCustomer(int customerId) throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CustomerHelper helper = CustomerFactory.createCustomerHelper(this.request, this.response, (DatabaseConnectionBean) tx.getConnector());
        try {
            if (helper == null) {
                return;
            }
            helper.fetchCustomer(customerId);
            this.balance = helper.getBalance();
            this.customers = helper.getCustomers();
            this.cust = helper.getCust();
            this.custDetail = helper.getCustDetail();
            this.busTypes = helper.getBusTypes();
            this.busServTypes = helper.getBusServTypes();
            return;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionHandlerException(e);
        }
        finally {
            helper.close();
            helper = null;
        }
    }

    /**
     * Create the data entities needed to represent a new customer. 
     */
    public void add() throws ActionHandlerException {
        // Create query to where customer is not found  
        int customerId = -1;
        this.fetchCustomer(customerId);
    }

    /**
     * Fetches data pertaining to the customer and its contact profile.  The contact profile 
     * consists of business or personal contact profile with address information.
     */
    public void edit() throws ActionHandlerException {
        // Create query to where customer is not found in the event customer object is null
        int customerId = -1;
        if (this.cust != null) {
            customerId = ((Customer) this.cust).getCustomerId();
        }
        this.fetchCustomer(customerId);
        return;
    }

    /**
     * Applies customer updates to the database.  
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CustomerApi api = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        // Apply Customer Updates to database
        try {
            api.maintainCustomer((Customer) this.cust, null);
            tx.commitUOW();
        }
        catch (CustomerException e) {
            tx.rollbackUOW();
            throw new ActionHandlerException(e);
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }

    /**
     * No Action
     */
    public void delete() throws ActionHandlerException {

    }

}