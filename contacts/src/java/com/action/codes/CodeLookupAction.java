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

import com.bean.VwCodes;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.services.handlers.CodeDetailsFetchHandler;
import com.util.SystemException;



/**
 * This action handler serves the user's request to fetch lookup code values 
 * for one or more general code groups.
 * 
 * @author appdev
 *
 */
public class CodeLookupAction extends AbstractActionHandler implements ICommand {
    
    private static final String COMMAND_LOOKUP = "GeneralCode.GeneralCodeList.lookup";
    
    private static final String GROUP_ID_PROPERTY = "GroupId";

    private static Logger logger = Logger.getLogger("CodeLookupAction");
    
    private int selGrpId[];
    
    private List<VwCodes> codes;
    
    
    
    /**
     * Default constructor.
     *
     */
    public CodeLookupAction() {
        super();
    }
    
    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public CodeLookupAction(Request request, Response response, String command) throws ActionHandlerException {
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
          if (command.equalsIgnoreCase(CodeLookupAction.COMMAND_LOOKUP)) {
              this.doLookup();
          }
      }
      catch (Exception e) {
          throw new ActionHandlerException("Client request for general code lookup failed." , e);
      }
      finally {
          // Ensure that any updates made to the the query object is set on the session. 
          if (this.query != null) {
              this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
          }
      }
  }
    
      
  /**
   * Obtains all of the group id's passed by the client as input parameters.   The name of the input 
   * parameter is expected to be <i>GroupId</i>.
   * 
   * @throws ActionHandlerException when one of the client's group id's a non-numeric value
   */
  protected void receiveClientData() throws ActionHandlerException {
      String strId[] = this.request.getParameterValues(CodeLookupAction.GROUP_ID_PROPERTY);
      if (strId == null) {
          return;
      }
      // Gather all selected group id's
      this.selGrpId = new int[strId.length]; 
      for (int ndx = 0; ndx < strId.length; ndx++) {
          try {
              this.selGrpId[ndx] = Integer.parseInt(strId[ndx]);    
          }
          catch (NumberFormatException e) {
              this.msg = "The selected group contains an invalid value: (index=" + ndx + ", value=" + strId[ndx];
              logger.log(Level.ERROR, this.msg);
              throw new ActionHandlerException(this.msg);
          }
      }
      return;
  }
  
  /**
   * 
   * @throws ActionHandlerException
   */
  protected void doLookup() throws ActionHandlerException {
      this.receiveClientData();
      
      DatabaseTransApi tx = DatabaseTransFactory.create();
      CodesApi api = CodesFactory.createCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
      
      try {
          this.codes = api.findLookupData(this.selGrpId);
          this.sendClientData();
      }
      catch (GeneralCodeException e) {
          this.msg = "Unable to perform general code lookup due to error";
          logger.error(this.msg);
          throw new ActionHandlerException(this.msg, e);
      }
      finally {
          api.close();
          tx.close();
          api = null;
          tx = null;
      }
  }
  
  /**
   * Sends the results of the lookup to the client as XML via the user's request instance.
   * 
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
      String xml = this.getXmlResults();
      this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
  }
  
  /**
   * Uses the results of the query to create the response message, RS_code_lookup.  
   * Mimics a web service functionality by converting the list of java objects to XML and 
   * appending the results of the conversion to the RS_code_lookup message.
   * 
   * @return The XML Message
   * @throws ActionHandlerException
   */
  protected String getXmlResults() throws ActionHandlerException {
      // Mimic web service to return response as XML!
      CodeDetailsFetchHandler srvc = new CodeDetailsFetchHandler(null, this.request);
      return srvc.buildCodeLookupResponsePayload((List <VwCodes>) this.codes);
  }
  
 
  /**
   * Not Implemented
   *  
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
      throw new ActionHandlerException("add method of action handler is not implemented");
  }
  
  /**
   * NOt Implemented
   *  
   * @throws ActionHandlerException
   */
  public void edit() throws ActionHandlerException {
      throw new ActionHandlerException("edit method of action handler is not implemented");
  }
  
  /**
   * Not implemented
   *  
   * @throws ActionHandlerException
   */
  public void save() throws ActionHandlerException {
      throw new ActionHandlerException("save method of action handler is not implemented");
  }
  
  
  /**
   * Not Implemented
   *  
   * @throws ActionHandlerException
   */
  public void delete() throws ActionHandlerException {
      throw new ActionHandlerException("delete method of action handler is not implemented");
  }

 
  
  
}