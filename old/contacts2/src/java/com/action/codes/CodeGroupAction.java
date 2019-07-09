package com.action.codes;

import java.util.List;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;
import com.api.codes.GeneralCodeException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.GeneralCodesGroup;

import com.bean.RMT2TagQueryBean;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;

import com.services.handlers.CodeDetailsFetchHandler;
import com.util.SystemException;

/**
 * This class provides action handlers to respond to an associated controller for 
 * searching, adding, and deleting General Code Group information.
 * 
 * @author appdev
 *
 */
public class CodeGroupAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "GeneralCodeGroup.GeneralCodeGroupList.list";

    private static final String COMMAND_ADD = "GeneralCodeGroup.GeneralCodeGroupList.add";

    private static final String COMMAND_EDIT = "GeneralCodeGroup.GeneralCodeGroupList.edit";

    private static final String COMMAND_DETAILS = "GeneralCodeGroup.GeneralCodeGroupList.details";

    private static final String COMMAND_DELETE = "GeneralCodeGroup.GeneralCodeGroupEdit.delete";

    private static final String COMMAND_SAVE = "GeneralCodeGroup.GeneralCodeGroupEdit.save";

    private static final String COMMAND_BACK = "GeneralCodeGroup.GeneralCodeGroupEdit.back";

    private static final String GROUP_ID_PROPERTY = "CodeGrpId";

    private Logger logger;

    private GeneralCodesGroup grp;

    private List codes;

    private String command;

    private int selGroupId[];

    /**
     * Default constructor.
     *
     */
    public CodeGroupAction() {
        super();
        logger = Logger.getLogger("CodeGroupAction");
    }

    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public CodeGroupAction(Request request, Response response, String command) throws ActionHandlerException {
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
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_LIST)) {
                this.showList();
            }
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_ADD)) {
                this.addData();
            }
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_DELETE)) {
                this.deleteData();
            }
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_EDIT)) {
                this.editData();
            }
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_DETAILS)) {
                this.getDetails();
            }
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_SAVE)) {
                this.saveData();
            }
            if (command.equalsIgnoreCase(CodeGroupAction.COMMAND_BACK)) {
                this.doBack();
            }
        }
        catch (Exception e) {
            this.msg = e.getMessage();
            //          this.transObj.rollbackUOW();    
            throw new ActionHandlerException(this.msg);
        }
        finally {
            // Ensure that any updates made to the the query object is set on the session. 
            if (this.query != null) {
                this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
            }
        }
    }

    /**
     * This obtains the clients input from the JSP which is generally a list of General Code 
     * Group Id's
     * 
     * @throws ActionHandlerException when one of the client's group id's a non-numeric value
     */
    protected void receiveClientData() throws ActionHandlerException {
        String strId[] = this.request.getParameterValues(CodeGroupAction.GROUP_ID_PROPERTY);
        if (strId == null) {
            return;
        }
        // Gather all selected group id's
        this.selGroupId = new int[strId.length];
        for (int ndx = 0; ndx < strId.length; ndx++) {
            try {
                this.selGroupId[ndx] = Integer.parseInt(strId[ndx]);
            }
            catch (NumberFormatException e) {
                this.msg = "The selecte group contains an invalid value: (row=" + ndx + ", value=" + strId[ndx];
                throw new ActionHandlerException(this.msg);
            }
        }

        // Try to obtain all data for the a single group item, if applicable.
        if (this.command.equalsIgnoreCase(CodeGroupAction.COMMAND_SAVE) && this.selGroupId.length == 1) {
            try {
                this.grp = CodesFactory.createGeneralGroup();
                CodesFactory.packageBean(this.request, this.grp);
            }
            catch (SystemException e) {
                throw new ActionHandlerException(e.getMessage());
            }
        }
    }

    /**
     * This method perorms any initialization routines prior to displaying the General Code 
     * Group list to the client.
     * <p>   
     * The initializatio tasks are:
     * <ul>
     *   <li>Resets the session query bean</li>
     * </ul> 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        try {
            this.request.setAttribute(GeneralConst.REQ_ATTRIB_DATA, this.grp);
            this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.codes);
            this.query = new RMT2TagQueryBean();
        }
        catch (SystemException e) {
            // do nothing
        }
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
	return srvc.buildCodeGroupResponsePayload(this.grp, this.msg);
    }

    /**
     * Drives the process of displaying the list of General Code Groups
     * 
     * @throws ActionHandlerException
     */
    protected void showList() throws ActionHandlerException {
        this.sendClientData();
    }

    /**
     * Drives the process of invoking the General Code Group Edit JSP page for adding a 
     * general code group item to the system.
     *  
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
        this.grp = CodesFactory.createGeneralGroup();
        this.sendClientData();
    }

    /**
     * Drives the process of invoking the General Code Group Edit JSP page for modifying existing general code group item in the system.
     *  
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
        // Get group from database.
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.grp = (GeneralCodesGroup) api.findGroupById(this.selGroupId[0]);
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
    }

    /**
     * Drives the process of persisting General Code Group item changes to a data provider.
     *  
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
        this.validate();
        // Save data.
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            api.maintainGroup(this.grp);
            tx.commitUOW();
            this.msg = "Group saved successfully";
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
     * Drives the process of deleting one or more General Code Group items from the system.
     *  
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            for (int ndx = 0; ndx < this.selGroupId.length; ndx++) {
                try {
                    this.grp = (GeneralCodesGroup) api.findGroupById(this.selGroupId[ndx]);
                    api.deleteGroup(this.grp);
                }
                catch (GeneralCodeException e) {
                    tx.rollbackUOW();
                    throw new ActionHandlerException(e.getMessage());
                }
            }
            tx.commitUOW();
            this.msg = "Group deleted successfully";
        }
        catch (ActionHandlerException e) {
            this.msg = e.getMessage();
            throw e;
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }

    /**
     * Drives the process of invoking the General Codes Edit JSP page for displaying all 
     * related general code items of a particular general code group.
     *  
     * @throws ActionHandlerException
     */
    protected void getDetails() throws ActionHandlerException {
        this.receiveClientData();
        // Get group record
        this.edit();

        // Get group from database.
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.codes = (List) api.findCodeByGroup(this.selGroupId[0]);
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
     * Validates the current General Code Group item.   The precondition to invoking this 
     * method is that this object is properly initialized with the general code group data 
     * that is to be validated.
     *  
     * @throws ActionHandlerException 
     *            data object was not properly initialized or the general code group description 
     *            property is null.
     */
    protected void validate() throws ActionHandlerException {
        if (this.grp == null) {
            throw new ActionHandlerException("Validation Error:  General Code Group object is invalid");
        }
        if (this.grp.getDescription() == null || this.grp.getDescription().equals("")) {
            throw new ActionHandlerException("Validation Error:  General Code Group's description property is invalid");
        }
    }

    /**
     * Actionhandler for returning the user to the home page.
     *  
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
        return;
    }

}