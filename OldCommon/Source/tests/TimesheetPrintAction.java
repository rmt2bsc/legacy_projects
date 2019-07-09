package com.action.reports;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.eclipse.birt.report.engine.api.IEngineTask;

import com.action.ICommand;
import com.action.reports.RMT2BirtActionHandler;

import com.bean.ProjTimesheet;
import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;
import com.factory.ProjectManagerFactory;

import com.util.DatabaseException;
import com.util.SystemException;
import com.util.NotFoundException;
import com.util.ActionHandlerException;
import com.util.RMT2Utility;


/**
 * This class provides action handlers to respond to the client's commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages. 
 * Handles requests pertaining to adding, deleting, saving, submitting (finalize), approving, declining and invoicing timehseets.
 * 
 * @author Roy Terrell
 *
 */
public class TimesheetPrintAction extends RMT2BirtActionHandler implements ICommand {
    private static Logger logger = Logger.getLogger("TimesheetPrintAction");
    private RMT2TagQueryBean query;
    	
	/**
	 * Default constructor.
	 *
	 */
    public TimesheetPrintAction() {
    	super();
    	logger = Logger.getLogger("TimesheetPrintAction");
    }
    
    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.  This parameter is pretty much ignored 
     *                                   since report identification relies on other meansin order to be executed.
     * @Throws SystemException when an error needs to be reported.
     */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
	  try {
          super.processRequest(request, response, "timesheet");
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e);
	  }
      finally {
          // Ensure that any updates made to the the query object is set on the session. 
          if (this.query != null) {
              this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
          }
      }
  }
  
  
  /**
   * Applies the report parmaters, timesheet id and client id,  to the report engines run.render task object. 
   * 
   * @param task The run/render task object.
   * @throws ActionHandlerException if a database access error occurs.
   */
  protected void setEngineParms(IEngineTask task)  throws ActionHandlerException {
      ProjTimesheet ts;
      try {
          // Get timesheet data from request
          ts = ProjectManagerFactory.createTimesheet();
          ProjectManagerFactory.packageBean(this.request, ts);
      }
      catch(Exception e) {
          logger.log(Level.ERROR, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
      }
      HashMap parms = new HashMap();
      parms.put("timesheetIdParm", String.valueOf(ts.getId()));
      parms.put("clientIdParm", String.valueOf(ts.getProjClientId()));
      task.setParameterValues(parms);
	  return;
  }
  

  /**
   * Applies the report parmaters, timesheet id and client id,  to the report viewer URL.
   * 
   * @param viewerURL the URL to apply report paramters towards. 
   * @throws ActionHandlerException  If parameters are unobtainable.
   */
  protected String setViewerParms(String viewerURL)  throws ActionHandlerException {
      ProjTimesheet ts;
      try {
          // Get timesheet data from request
          ts = ProjectManagerFactory.createTimesheet();
          ProjectManagerFactory.packageBean(this.request, ts);
      }
      catch(Exception e) {
          logger.log(Level.ERROR, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
      }
      viewerURL += "&timesheetIdParm=" + ts.getId();
      viewerURL += "&clientIdParm=" + ts.getProjClientId();
      return viewerURL;
  }

  
  /**
   * Obtains the report parameters from the client's request object.
   * 
   * @throws ActionHandlerException if a validation error occurs.
   */
  protected void receiveClientData() throws ActionHandlerException {
	  try {
	  }
	  catch(Exception e) {
		  logger.log(Level.ERROR, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
	  }
  }
  
	
  /**
   * No Action
   * 
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
	  return;  
  }
  
}