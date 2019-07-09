package com.action.codes;


import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.GeneralCodeManagerApi;
import com.api.db.DatabaseException;

import com.bean.GeneralCodes;
import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;

import com.factory.GeneralCodeFactory;

import com.util.SystemException;
import com.util.GeneralCodeException;


/**
 * This class provides action handlers to respond to an associated controller for searching, adding, and deleting General Codes.
 * 
 * @author appdev
 *
 */
public class GeneralCodeAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "GeneralCode.GeneralCodeList.list";
    private static final String COMMAND_ADD = "GeneralCode.GeneralCodeList.add";
    private static final String COMMAND_DELETE = "GeneralCode.GeneralCodeList.delete";
    private static final String COMMAND_EDIT = "GeneralCode.GeneralCodeList.edit";
    private static final String COMMAND_SAVE = "GeneralCode.GeneralCodeEdit.save";
    private static final String COMMAND_BACK = "GeneralCode.GeneralCodeEdit.back";
    private static final String CODE_ID_PROPERTY = "Id";

    private Logger logger;
    private GeneralCodeManagerApi  api;
    private GeneralCodes code;
    private String command;
    private int selCodeId[];
    
    
    /**
     * Default constructor.
     *
     */
    public GeneralCodeAction() {
        super();
        logger = Logger.getLogger("GeneralCodeAction");
    }

    
    
    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public GeneralCodeAction( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
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
            this.api = GeneralCodeFactory.createCodeApi(this.dbConn);            
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
          if (command.equalsIgnoreCase(GeneralCodeAction.COMMAND_LIST)) {
              this.showList();
          }
          if (command.equalsIgnoreCase(GeneralCodeAction.COMMAND_ADD)) {
              this.addData();
          }
          if (command.equalsIgnoreCase(GeneralCodeAction.COMMAND_DELETE)) {
              this.deleteData();
          }
          if (command.equalsIgnoreCase(GeneralCodeAction.COMMAND_EDIT)) {
              this.editData();
          }
          if (command.equalsIgnoreCase(GeneralCodeAction.COMMAND_SAVE)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(GeneralCodeAction.COMMAND_BACK)) {
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
      String strId[] = this.request.getParameterValues(GeneralCodeAction.CODE_ID_PROPERTY);
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
              this.msg = "The selecte group contains an invalid value: (row=" + ndx + ", value=" + strId[ndx];
              throw new ActionHandlerException(this.msg);
          }
      }

      // Try to obtain all data for the a single group item, if applicable.
      if (this.command.equalsIgnoreCase(GeneralCodeAction.COMMAND_SAVE) && this.selCodeId.length == 1) {
          try {
              this.code = GeneralCodeFactory.createCode();
              GeneralCodeFactory.packageBean(this.request, this.code);   
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
      this.request.setAttribute(GeneralConst.REQ_ATTRIB_DATA, this.code);
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
          this.code = GeneralCodeFactory.createCode();
          GeneralCodeFactory.packageBean(this.request, this.code);
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
          this.code = this.api.findCodeById(this.selCodeId[0]);
      }
      catch (GeneralCodeException e) {
          throw new ActionHandlerException(e.getMessage());
      }
      this.sendClientData();
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
    	   this.api.maintainCode(this.code);
    	   this.transObj.commitUOW();
    	   this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO,"Group saved successfully");
       }
       catch (GeneralCodeException e) {
    	   this.transObj.rollbackUOW();
    	   throw new ActionHandlerException(e.getMessage());
       }    
  }
  
  
  /**
   * Deletes one or more general codes based on client's selection.
   *  
   * @throws ActionHandlerException
   */
  public void delete() throws ActionHandlerException {
      for (int ndx = 0; ndx < this.selCodeId.length; ndx++) {
          try {
              this.code = this.api.findCodeById(this.selCodeId[ndx]);
              this.api.deleteCode(this.code);
          }
          catch (GeneralCodeException e) {
              this.transObj.rollbackUOW();
              throw new ActionHandlerException(e.getMessage());
          }                  
      }
      this.transObj.commitUOW();
      return;
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
  }
  
  /**
   * Validates the current General Code Group item.   The precondition to invoking this method is that 
   * this object is properly initialized with the general code group data that is to be validated.
   *  
   * @throws ActionHandlerException if data object was not properly initialized or the general code group description property is null.
   */
  protected void validateGroup() throws ActionHandlerException { 
      if (this.code == null) {
          throw new ActionHandlerException("Validation Error:  General Code Group object is invalid");
      }
      if (this.code.getLongdesc() == null || this.code.getLongdesc().equals("")) {
          throw new ActionHandlerException("Validation Error:  General Code Group's description property is invalid");
      }
  }
  
  
  /**
   * Performs validations for the edit action.
   * 
   * @throws ActionHandlerException if zero or mulitple general code group item selections are discovered.
   */
  protected void validateEditAction() throws ActionHandlerException {
      if (this.selCodeId ==  null || this.selCodeId.length > 1) {
          throw new ActionHandlerException("Error: Only one General Code Group item is required to be selected for the edit operation");
      }
  }
  
  /**
   * Performs validations for the delete action.
   * 
   * @throws ActionHandlerException  if zero general code group item selections were discovered
   */
  protected void validateDeleteAction() throws ActionHandlerException {
      if (this.selCodeId ==  null || this.selCodeId.length < 1) {
          throw new ActionHandlerException("Error: At least one General Code Group item must be selected for the delete operation");
      }
  }
  
  /**
   * Performs validations for the Show Details action.
   * 
   * @throws ActionHandlerException if zero or mulitple general code group item selections are discovered.
   */
  protected void validateDetailsAction() throws ActionHandlerException {
      if (this.selCodeId ==  null || this.selCodeId.length > 1) {
          throw new ActionHandlerException("Error: Only one General Code Group item is required to be selected in order to view details");
      }
  }
}