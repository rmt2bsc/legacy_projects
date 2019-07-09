package com.employee;

import java.math.BigInteger;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceAdapter;
import com.api.messaging.MessageException;
import com.api.messaging.webservice.soap.client.SoapClientWrapper;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjEmployee;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2File;
import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationUserProfile;
import com.xml.schema.bindings.RSAuthenticationUserProfile;
import com.xml.schema.misc.PayloadFactory;

/**
 * This class provides common functionality to manage new and existing employees.
 * 
 * @author Roy Terrell
 *
 */
public class EmployeeCommonAction extends AbstractActionHandler {

    private static Logger logger = Logger.getLogger(EmployeeCommonAction.class);

    private static String lookupData;

    protected static String EMPTY_EMP_DOC = "<VwEmployeeExtView></VwEmployeeExtView>";

    protected static String NEW_EMP_DOC = "<VwEmployeeExtView><vw_employee_ext><start_date></start_date><ssn></ssn><employee_type></employee_type><shortname></shortname><lastname></lastname><type_id></type_id><company_name /><email></email><firstname></firstname><employee_id></employee_id><termination_date /><employee_title></employee_title><title_id></title_id><is_manager></is_manager><manager_id></manager_id><login_name></login_name><login_id></login_id></vw_employee_ext></VwEmployeeExtView>";

    private Object managerList;

    private Object empTypeList;

    private Object empTitleList;

    protected Object employees;

    protected Object employee;

    protected ProjEmployee emp;

    protected String dropDownData;

    protected String userProfiles;

    /**
     * Default constructor which creates a EmployeeCommonAction object 
     * and sets up the logger.
     *
     */
    public EmployeeCommonAction() {
        super();
        EmployeeCommonAction.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Creates a EmployeeCommonAction object containing the application context 
     * and the user's request.   
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public EmployeeCommonAction(Context context, Request request) throws SystemException, DatabaseException {
        super(context, request);
        this.init(this.context, this.request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        super.processRequest(request, response, command);
    }

    /**
     * Retrieve the selected customer id from a list of customers.
     *  
     * @throws ActionHandlerException 
     *           Problem Identifying Customer Id from a list of customers
     */
    public void receiveClientData() throws ActionHandlerException {
        this.emp = EmployeeFactory.createEmployee();
        DataSourceAdapter.packageBean(this.request, this.emp);

        // TODO:  Get list of managers, employee types, and employee titles as XML
        this.managerList = null;
        this.empTitleList = null;
        this.empTypeList = null;

        return;
    }

    /**
     * Preserves the user's input values regarding the selection criteria. 
     */
    public void sendClientData() throws ActionHandlerException {
        this.request.setAttribute("employeeList", this.employees);
        this.request.setAttribute("employee", this.employee);
        this.request.setAttribute("lookupData", this.dropDownData);
        this.request.setAttribute("userProfiles", this.userProfiles);

        // Set another copy of the user profile data for AJAX call. 
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, this.userProfiles);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
        // Setup new employee document
        this.employee = EmployeeCommonAction.NEW_EMP_DOC;
        this.dropDownData = this.fetchLookupData();

        SoapClientWrapper client = new SoapClientWrapper();
        ObjectFactory f = new ObjectFactory();
        try {
            RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
            RQAuthenticationUserProfile ws = f.createRQAuthenticationUserProfile();
            HeaderType header = PayloadFactory.createHeader("RQ_authentication_user_profile", "SYNC", "REQUEST", userSession.getLoginId());
            ws.setHeader(header);

            String loginId = this.request.getParameter("login_id");
            if (loginId != null) {
                ws.setLoginId(BigInteger.valueOf(Integer.parseInt(loginId)));
            }
            String msg = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
            // Send web service request
            SOAPMessage resp = client.callSoap(msg);
            if (client.isSoapError(resp)) {
                String errMsg = client.getSoapErrorMessage(resp);
                logger.error(errMsg);
                throw new ActionHandlerException(errMsg);
            }
            // Get response message
            RSAuthenticationUserProfile soapResp = (RSAuthenticationUserProfile) client.getSoapResponsePayload();
            String xml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp, false);

            // Setup XML response on the user's request instance so that the controller can send back to the client
            this.userProfiles = xml;
            logger.info(xml);
        }
        catch (MessageException e) {
            throw new ActionHandlerException(e);
        }
    }

    /**
     * Fetches the list customers from the database using the where clause criteria 
     * previously stored on the session during the phase of the request to builds 
     * the query predicate.
     * 
     * @throws ActionHandlerException
     */
    protected void listData() throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            String criteria = this.query.getWhereClause();
            this.employees = api.findEmployeeExt(criteria);

            if (this.employees == null) {
                // Create Empty result set
                this.employees = EmployeeCommonAction.EMPTY_EMP_DOC;
            }

            this.dropDownData = this.fetchLookupData();
        }
        catch (Exception e) {
            throw new ActionHandlerException(e);
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }

    }

    protected String fetchLookupData() {
        if (lookupData != null) {
            return lookupData.toString();
        }

        DatabaseTransApi tx = null;
        EmployeeApi api = null;
        try {
            tx = DatabaseTransFactory.create();
            api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
            try {
                this.empTypeList = api.findEmployeeTypes();
            }
            catch (EmployeeException e) {
                this.empTypeList = "";
            }
            try {
                this.empTitleList = api.findEmployeeTitles();
            }
            catch (EmployeeException e) {
                this.empTitleList = "";
            }
            try {
                this.managerList = api.findManagers();
            }
            catch (EmployeeException e) {
                this.managerList = "";
            }

            StringBuffer buf = new StringBuffer();
            String tagStart = "<lookup>";
            String tagEnd = "</lookup>";
            buf.append(tagStart);
            buf.append(this.empTypeList);
            buf.append(this.empTitleList);
            buf.append(this.managerList);
            buf.append(tagEnd);

            RMT2File.createFile(buf.toString(), "c:\\tmp\\test.xml");
            lookupData = buf.toString();
            return buf.toString();    
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
        finally {
            api.close();
            api = null;
        }
    }

    /**
     * 
     * @param empId
     * @throws ActionHandlerException
     */
    protected void refreshEmployee(int empId) throws ActionHandlerException {
        // Fetch Single employee record
        DatabaseTransApi tx = DatabaseTransFactory.create();
        EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.employee = api.findEmployeeExt(empId);
            if (this.employee == null) {
                // Create Empty result set
                this.employee = EmployeeCommonAction.EMPTY_EMP_DOC;
            }
            this.dropDownData = this.fetchLookupData();
        }
        catch (NumberFormatException e) {
            this.msg = null;
            throw new ActionHandlerException(e);
        }
        catch (Exception e) {
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
     * @return the managerList
     */
    public Object getManagerList() {
        return managerList;
    }

    /**
     * @return the empTypeList
     */
    public Object getEmpTypeList() {
        return empTypeList;
    }

    /**
     * @return the empTitleList
     */
    public Object getEmpTitleList() {
        return empTitleList;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#save()
     */
    public void save() throws ActionHandlerException, DatabaseException {
        // TODO Auto-generated method stub

    }

}