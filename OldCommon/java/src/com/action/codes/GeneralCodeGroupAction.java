package com.action.codes;


import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.GeneralCodeGroupManagerApi;
import com.api.db.DatabaseException;

import com.bean.GeneralCodesGroup;
import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;

import com.factory.GeneralCodeFactory;

import com.util.SystemException;
import com.util.GeneralCodeException;


/**
 * This class provides action handlers to respond to an associated controller for searching, adding, and deleting General Code Group information.
 * 
 * @author appdev
 *
 */
public class GeneralCodeGroupAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "GeneralCodeGroup.GeneralCodeGroupList.list";
    private static final String COMMAND_ADD = "GeneralCodeGroup.GeneralCodeGroupList.add";
    private static final String COMMAND_DELETE = "GeneralCodeGroup.GeneralCodeGroupList.delete";
    private static final String COMMAND_EDIT = "GeneralCodeGroup.GeneralCodeGroupList.edit";
    private static final String COMMAND_DETAILS = "GeneralCodeGroup.GeneralCodeGroupList.details";
    private static final String COMMAND_SAVE = "GeneralCodeGroup.GeneralCodeGroupEdit.save";
    private static final String COMMAND_BACK = "GeneralCodeGroup.GeneralCodeGroupEdit.back";
    private static final String GROUP_ID_PROPERTY = "Id";

    private Logger logger;
    private GeneralCodeGroupManagerApi  api;
    private GeneralCodesGroup grp;
    private String command;
    private int selGroupId[];
    
    
    /**
     * Default constructor.
     *
     */
    public GeneralCodeGroupAction() {
        super();
        logger = Logger.getLogger("GeneralCodeGroupAction");
    }

    
    
    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public GeneralCodeGroupAction( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
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
    
    
    
    protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
        super.init(_context, _request);
        try {
            this.api = GeneralCodeFactory.createGroupApi(this.dbConn);            
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new SystemException(e);
        }
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
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
      try {
          this.init(null, request);
          this.init();

          this.command = command;
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_LIST)) {
              this.showList();
          }
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_ADD)) {
              this.addData();
          }
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_DELETE)) {
              this.deleteData();
          }
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_EDIT)) {
              this.editData();
          }
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_DETAILS)) {
              this.getDetails();
          }
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_SAVE)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_BACK)) {
              this.showList();
          }
      }
      catch (Exception e) {
          this.msg = e.getMessage();
          this.transObj.rollbackUOW();    
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
   * This obtains the clients input from the JSP which is generally a list of General Code Group Id's
   * 
   * @throws ActionHandlerException when one of the client's group id's a non-numeric value
   */
  protected void receiveClientData() throws ActionHandlerException {
      String strId[] = this.request.getParameterValues(GeneralCodeGroupAction.GROUP_ID_PROPERTY);
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
      if (this.command.equalsIgnoreCase(GeneralCodeGroupAction.COMMAND_SAVE) && this.selGroupId.length == 1) {
          try {
              this.grp = GeneralCodeFactory.createGroup();
              GeneralCodeFactory.packageBean(this.request, this.grp);   
          }
          catch (SystemException e) {
              throw new ActionHandlerException(e.getMessage());
          }          
      }
  }
  
  /**
   * This method perorms any initialization routines prior to displaying the General Code Group list to the client.
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
          this.query = new RMT2TagQueryBean();
      }
      catch (SystemException e) {
          // do nothing
      }
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
   * Drives the process of invoking the General Code Group Edit JSP page for adding a general code group item to the system.
   *  
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
      try {
          this.grp = GeneralCodeFactory.createGroup();
      }
      catch (SystemException e) {
          throw new ActionHandlerException(e.getMessage());
      }
      this.sendClientData();
  }
  
  /**
   * Drives the process of invoking the General Code Group Edit JSP page for modifying existing general code group item in the system.
   *  
   * @throws ActionHandlerException
   */
  public void edit() throws ActionHandlerException {
      this.receiveClientData();
      this.validateEditAction();
      
      // Get group from database.
      try {
          this.grp = this.api.findGroupById(this.selGroupId[0]);
      }
      catch (GeneralCodeException e) {
          throw new ActionHandlerException(e.getMessage());
      }
  }
  
  /**
   * Drives the process of persisting General Code Group item changes to a data provider.
   *  
   * @throws ActionHandlerException
   */
  public void save() throws ActionHandlerException {
      this.validateGroup();
      // Save data.
      try {
          this.api.maintainGroup(this.grp);
          this.transObj.commitUOW();
          this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO,"Group saved successfully");
      }
      catch (GeneralCodeException e) {
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e.getMessage());
      }    
  }
  

  /**
   * Drives the process of deleting one or more General Code Group items from the system.
   *  
   * @throws ActionHandlerException
   */
  public void delete() throws ActionHandlerException {
      for (int ndx = 0; ndx < this.selGroupId.length; ndx++) {
          try {
              this.grp = this.api.findGroupById(this.selGroupId[ndx]);
              this.api.deleteGroup(this.grp);
          }
          catch (GeneralCodeException e) {
              this.transObj.rollbackUOW();
              throw new ActionHandlerException(e.getMessage());
          }                  
      }
      this.transObj.commitUOW();
  }

  
  /**
   * Drives the process of deleting one or more General Code Group items from the system.
   *  
   * @throws ActionHandlerException
   */
  protected void deleteData() throws ActionHandlerException {
      this.receiveClientData();
      this.validateDeleteAction();
      this.delete();
  }
  
  
  /**
   * Drives the process of invoking the General Codes Edit JSP page for displaying all related general code items of a particular general code group.
   *  
   * @throws ActionHandlerException
   */
  protected void getDetails() throws ActionHandlerException {
      this.receiveClientData();
      this.validateDetailsAction();
      this.sendClientData();
      
      // Overwrite data set a framework level with the group id.
      this.query.setWhereClause("group_id = " + this.selGroupId[0]);
      try {
          this.query.removeKeyValues("group_id");
          this.query.addKeyValues("group_id", String.valueOf(this.selGroupId[0]));    
      }
      catch (Exception e) {
          throw new ActionHandlerException(e.getMessage());
      }
      
  }
  
  /**
   * Validates the current General Code Group item.   The precondition to invoking this method is that 
   * this object is properly initialized with the general code group data that is to be validated.
   *  
   * @throws ActionHandlerException if data object was not properly initialized or the general code group description property is null.
   */
  protected void validateGroup() throws ActionHandlerException { 
      if (this.grp == null) {
          throw new ActionHandlerException("Validation Error:  General Code Group object is invalid");
      }
      if (this.grp.getDescription() == null || this.grp.getDescription().equals("")) {
          throw new ActionHandlerException("Validation Error:  General Code Group's description property is invalid");
      }
  }
  
  
  /**
   * Performs validations for the edit action.
   * 
   * @throws ActionHandlerException if zero or mulitple general code group item selections are discovered.
   */
  protected void validateEditAction() throws ActionHandlerException {
      if (this.selGroupId ==  null || this.selGroupId.length > 1) {
          throw new ActionHandlerException("Error: Only one General Code Group item is required to be selected for the edit operation");
      }
  }
  
  /**
   * Performs validations for the delete action.
   * 
   * @throws ActionHandlerException  if zero general code group item selections were discovered
   */
  protected void validateDeleteAction() throws ActionHandlerException {
      if (this.selGroupId ==  null || this.selGroupId.length < 1) {
          throw new ActionHandlerException("Error: At least one General Code Group item must be selected for the delete operation");
      }
  }
  
  /**
   * Performs validations for the Show Details action.
   * 
   * @throws ActionHandlerException if zero or mulitple general code group item selections are discovered.
   */
  protected void validateDetailsAction() throws ActionHandlerException {
      if (this.selGroupId ==  null || this.selGroupId.length > 1) {
          throw new ActionHandlerException("Error: Only one General Code Group item is required to be selected in order to view details");
      }
  }
}