package com.action.codes;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;
import com.api.codes.GeneralCodeException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;

import com.services.handlers.CodeDetailsFetchHandler;
import com.util.SystemException;



/**
 * This class provides action handlers to respond to an associated controller 
 * for searching, adding, and deleting General Codes.
 * 
 * @author appdev
 *
 */
public class CodeAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_BACK = "GeneralCode.GeneralCodeList.back";

    private static final String COMMAND_ADD = "GeneralCode.GeneralCodeList.add";

    private static final String COMMAND_EDIT = "GeneralCode.GeneralCodeList.edit";

    private static final String COMMAND_SAVE = "GeneralCode.GeneralCodeEdit.save";

    private static final String COMMAND_DELETE = "GeneralCode.GeneralCodeEdit.delete";

    private static final String COMMAND_LIST = "GeneralCode.GeneralCodeEdit.list";

    private static final String CODE_ID_PROPERTY = "CodeId";

    private Logger logger;

    private GeneralCodes code;

    private GeneralCodesGroup grp;

    private List codes;

    private int selCodeId[];

    /**
     * Default constructor.
     *
     */
    public CodeAction() {
        super();
        logger = Logger.getLogger("CodeAction");
    }

    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public CodeAction(Request request, Response response, String command) throws ActionHandlerException {
        this();
        try {
            this.init(null, request);
            this.init();
        }
        catch (Exception e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
    }

    /**
     * Initializes the general codes api.
     * 
     * @param _context the servet context
     * @param _request the http servlet request
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
        return;
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
        try {
            this.init(null, request);
            this.init();

            this.command = command;
            if (command.equalsIgnoreCase(CodeAction.COMMAND_LIST)) {
                this.showCodeList();
            }
            if (command.equalsIgnoreCase(CodeAction.COMMAND_ADD)) {
                this.addData();
            }
            if (command.equalsIgnoreCase(CodeAction.COMMAND_DELETE)) {
                this.deleteData();
            }
            if (command.equalsIgnoreCase(CodeAction.COMMAND_EDIT)) {
                this.editData();
            }
            if (command.equalsIgnoreCase(CodeAction.COMMAND_SAVE)) {
                this.saveData();
            }
            if (command.equalsIgnoreCase(CodeAction.COMMAND_BACK)) {
                this.doBack();
            }
        }
        catch (Exception e) {
            throw new ActionHandlerException("General code client request failed.", e);
        }
        finally {
            // Ensure that any updates made to the the query object is set on the session. 
            if (this.query != null) {
                this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
            }
        }
    }

    /**
     * This method is capable of obtaining the clients input from the either the General Code 
     * List or Edit JSP's.
     * 
     * @throws ActionHandlerException when one of the client's group id's a non-numeric value
     */
    protected void receiveClientData() throws ActionHandlerException {
        String strId[] = this.request.getParameterValues(CodeAction.CODE_ID_PROPERTY);
        if (strId == null) {
            return;
        }
        // Gather all selected group id's
        this.selCodeId = new int[strId.length];
        for (int ndx = 0; ndx < strId.length; ndx++) {
            try {
                this.selCodeId[ndx] = Integer.parseInt(strId[ndx]);
            }
            catch (NumberFormatException e) {
                this.msg = "The selected group contains an invalid value: (row=" + ndx + ", value=" + strId[ndx];
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionHandlerException(this.msg);
            }
        }

        // Try to obtain all data for the a single group item, if applicable.
        if (this.selCodeId.length == 1) {
            try {
                this.code = CodesFactory.createGeneralCodes();
                CodesFactory.packageBean(this.request, this.code);
            }
            catch (Exception e) {
                throw new ActionHandlerException(e.getMessage());
            }
        }
    }

    /**
     * This method sends a single GeneralCode object, if applicable, a list of codes pertaining to the 
     * group, and a single GeneralCodesGroup object to the client for presentation. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.code);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.codes);
        this.request.setAttribute(GeneralConst.REQ_ATTRIB_DATA, this.grp);

        String xml = this.getXmlResults();
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
    }

    /**
     * Sends a update confirmation to client as the XML message, RS_common_reply.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    protected String getXmlResults() throws ActionHandlerException {
	CodeDetailsFetchHandler srvc = new CodeDetailsFetchHandler(null, this.request);
	return srvc.buildCodeDetailsResponsePayload(this.code, this.msg);
    }

    /**
     * Drives the process of displaying the list of General Codes by Group
     * 
     * @throws ActionHandlerException
     */
    protected void showCodeList() throws ActionHandlerException {
        this.getGroupFromRequest();
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.codes = (List) api.findCodeByGroup(this.grp.getCodeGrpId());
        }
        catch (GeneralCodeException e) {
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
        this.sendClientData();
    }

    /**
     * Retrieve the group record from the database using the group id value stored in the request.
     * 
     * @return int as the group id.
     */
    private int getGroupFromRequest() throws ActionHandlerException {
        String temp = this.request.getParameter("CodeGrpId");
        int grpId;
        try {
            grpId = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Problem occurred obtaining group id for the current general code record";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.grp = (GeneralCodesGroup) api.findGroupById(grpId);
        }
        catch (GeneralCodeException e) {
            throw new ActionHandlerException(e);
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
        return grpId;
    }

    /**
     * Drives the process of invoking the General Code Edit JSP page for adding a general code 
     * item to the system.
     *  
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
        int grpId = this.getGroupFromRequest();
        try {
            this.code = CodesFactory.createGeneralCodes();
            CodesFactory.packageBean(this.request, this.code);
            this.code.setCodeGrpId(grpId);
        }
        catch (SystemException e) {
            throw new ActionHandlerException(e.getMessage());
        }
        this.sendClientData();
    }

    /**
     * Drives the process of invoking the General Code Edit JSP page for modifying existing general code 
     * item in the system.
     *  
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
        this.getGroupFromRequest();
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            // Get group from database.
            this.code = (GeneralCodes) api.findCodeById(this.code.getCodeId());
        }
        catch (Exception e) {
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }

    /**
     * Drives the process of persisting any General Code item changes to the database.
     *  
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
        this.validate();
        // Save data.
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            api.maintainCode(this.code);
            tx.commitUOW();
            this.msg = "Lookup code saved successfully";
            this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
        }
        catch (GeneralCodeException e) {
            tx.rollbackUOW();
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }

    /**
     * Deletes one  general codes record from the database based on client's selection.
     *  
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            api.deleteCode(this.code);
            tx.commitUOW();
            this.msg = "Code was deleted successfully";
            this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
            return;
        }
        catch (GeneralCodeException e) {
            tx.rollbackUOW();
            this.msg = "Code delete failed.  " + e.getMessage();
            this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }

    /**
     * Validates the current General Code item.   The precondition to invoking this method is that 
     * this object is properly initialized with the general code data that is to be validated.
     *  
     * @throws ActionHandlerException 
     *           data object was not properly initialized or the general code group description 
     *           property is null.
     */
    protected void validate() throws ActionHandlerException {
        if (this.code == null) {
            this.msg = "Validation Error:  General Code object is invalid";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        if (this.code.getLongdesc() == null || this.code.getLongdesc().equals("")) {
            this.msg = "Validation Error:  General Code's description property is invalid";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
    }

    /**
     * Returns the user to the list of groups page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
        return;
    }

}