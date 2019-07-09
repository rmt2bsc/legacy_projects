package com.action.reports;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.PDFRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.EngineException;

import org.eclipse.birt.report.engine.api.script.ScriptException;

import org.eclipse.birt.report.engine.api.script.element.IReportDesign;
import org.eclipse.birt.report.engine.api.script.element.IDataSet;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.RMT2ServletActionHandler;

import com.bean.RMT2BirtEngine;
import com.constants.RMT2ServletConst;

import com.util.RMT2Utility;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.ActionHandlerException;

/**
 * Abstract base class for handling client requests for producing BIRT web reports.
 *
 * @author rterrell
 *
 */
public abstract class RMT2BirtActionHandler extends RMT2ServletActionHandler  {
	
	private IReportEngine birtReportEngine = null;
	private IReportRunnable reportObj;
	private IReportDesign reportDesign;
    private IRunAndRenderTask reportTask;
    private ResourceBundle configProps;
	private String reportPath;
    private String reportName;
    private String imagePath;
    private int rptProcessor;
    
    private static Logger logger;
	private static final String  RPT_FORMAT_PDF = "pdf";
	private static final String  RPT_FORMAT_HTML = "html";
    private final static String CONFIG_FILE = "BirtConfig";
    private final static String REPORT_EXT = "reportExt";
    private final static String VIEWER_URL = getEnvironment() +  ".viewerURL";
    private final static String REPORT_PATH = "rptDesignPath";
    private final static String IMAGE_PATH = "rptImagePath";
    private final static String RPT_PROCESSOR = "rptProcessor";
    private final static String OUTPUT_FORMAT = "outputFormat";
    private final static int RPT_PROCESSOR_VIEWER = 100;
    private final static int RPT_PROCESSOR_ENGINE = 200;
 
     
    
      /**
       * Default constructor.
       */
      public RMT2BirtActionHandler() {
    	  super();
    	  logger = Logger.getLogger("RMT2BirtActionHandler");
      }
    
      /**
       * Constructor responsible for initializing this object's servlet context and Http Request objects
       *
       * @param _context The servlet context passed by the servlet
       * @param _request The Http Servle Request object passed by the servlet
       * @throws SystemException
       */
      public RMT2BirtActionHandler(HttpServletRequest _request, HttpServletResponse _response) throws SystemException {
          super(null, _request);
          this.response = _response;
          this.methodName = "RMT2BirtActionHandler(ServletContext, HttpServletRequest)";
          this.init(null, _request);
      }
    
    
      /**
       * Initializes the RMT2BirtActionHandler using _conext and _request.  Also, the BIRT engine 
       * is initialized and the the BIRT configuration property collection is loaded.
       *
       * @throws SystemException
       */
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          this.methodName = "init(ServletContext, HttpServletRequest)";
          super.init(_context, _request);

          try {
              configProps = RMT2Utility.loadAppConfigProperties(CONFIG_FILE);    
          }
          catch (SystemException e) {
              e.printStackTrace();
          }
          
          // Determine Report processor type
          String temp = this.configProps.getString(RMT2BirtActionHandler.RPT_PROCESSOR);
          try {
              this.rptProcessor = Integer.parseInt(temp);    
          }
          catch (NumberFormatException e) {
              this.rptProcessor = RMT2BirtActionHandler.RPT_PROCESSOR_VIEWER;
          }
          
          // Get Image directory path
          this.imagePath = this.configProps.getString(RMT2BirtActionHandler.IMAGE_PATH);
      }
    

      /**
       * Abstract class that handles the client's request to generate a BIRT based web report.   The steps taken to process a report request goes as follows:
       * <ul>
       *    <li> Initialize data needed to interact with the servlet engine</li>
       *    <li>Obtain data from the client</li>
       *    <li>Locate the image directory, if needed</li>
       *    <li>Oopen the report design</li>
       *    <li>Set any report parameters as requested by the client</li>
       *    <li>Determine report output format (pdf or html)</li>
       *    <li>Initialize the output stream for the report</li>
       *    <li>Run the report</li>
       *    <li>Perform any tasks needed to complete the response before returning to the client</li>
       * </ul>
       *  
       * @param req The Http Servelt Request object
       * @param resp The Http Servlet Response object
       * @param reportName The name of the report to generate
       * @throws ServletException If a problem occurs trying to access the properties of the servlet object.
       * @throws IOException 
       * @throws ActionHandlerException
       */
      public void processRequest(HttpServletRequest req, HttpServletResponse resp, String _reportName) throws  ActionHandlerException {
    	  try {
    		  this.init(null, req);
    		  this.response = resp;
    	  }
    	  catch (SystemException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    	  }

    	  //  Get client data fro the request object
    	  this.receiveClientData();
          this.reportName = _reportName;

          switch (this.rptProcessor) {
	          case RMT2BirtActionHandler.RPT_PROCESSOR_VIEWER:
	              this.processReportViewer();
	              break;
	          case RMT2BirtActionHandler.RPT_PROCESSOR_ENGINE:
	              this.birtReportEngine = RMT2BirtEngine.getBirtEngine(this.context);
	              
	              // Try to load BIRT engine configuration file
	              if (!RMT2BirtEngine.isConfigPropsLoaded()) {
	                  RMT2BirtEngine.initBirtConfig();
	              }
	              this.processReportEngine();
	              break;
	              
	          default:
	              this.processReportViewer();    
          }
      }
      
      
      /**
       * Prepares the report to be rendered by the BIRT report Viewer Servlet
       * 
       * @throws ActionHandlerException
       */
      private void processReportViewer() throws  ActionHandlerException {
          // Setup URL without parameters
          String url = this.configProps.getString(RMT2BirtActionHandler.VIEWER_URL);
          String rptPath = this.configProps.getString(RMT2BirtActionHandler.REPORT_PATH);
          String ext = this.configProps.getString(RMT2BirtActionHandler.REPORT_EXT);
           url +="__report=" +  rptPath +  "/" + this.reportName + ext;
           // Get parameters
          url = this.setViewerParms(url);
          // Set delayed response url
          this.request.setAttribute(RMT2ServletConst.REQUEST_DELAYED_RESPONSE, url);
          this.request.setAttribute(RMT2ServletConst.RESPONSE_PROTOCOL, String.valueOf(RMT2ServletConst.RESPONSE_PROTOCOL_HTTP));
          return;
      }
      
      /**
       * Prepares the report to be renderd by the BIRT Report Engine API.
       * 
       * @throws ActionHandlerException
       */
      private void processReportEngine() throws  ActionHandlerException {
          try  {
              // Build report name and launch the engine
        	  String rptPath = this.configProps.getString(RMT2BirtActionHandler.REPORT_PATH);
        	  String rptExt = this.configProps.getString(RMT2BirtActionHandler.REPORT_EXT);
              this.reportPath =  this.context.getRealPath(rptPath) +"/" + this.reportName + rptExt;
              //Open report
              this.reportObj = this.birtReportEngine.openReportDesign(this.reportPath);
              // Get Report design
              this.reportDesign = this.reportObj.getDesignInstance();
//              String contextPath = this.context.getRealPath("/images");
//             this.reportDesign.setUserProperty("context", contextPath);
              
              // Create task to run and render report
              this.reportTask = this.birtReportEngine.createRunAndRenderTask(this.reportObj );
              // Get and set report parameters
              this.setEngineParms(this.reportTask);
              // Edit any other properties pertaining to the runtime state of the report or its design.
              this.edit();

              // Determine report output format and setup the appropriate rendering options.   Default to HTML.
              String outputFormat = this.configProps.getString(RMT2BirtActionHandler.OUTPUT_FORMAT);
              HTMLRenderOption options = null;
              if (outputFormat == null) {
                  options = this.setupHTMLOptions(this.reportTask);
              }
              else if (outputFormat.equalsIgnoreCase(RMT2BirtActionHandler.RPT_FORMAT_PDF)) {
                  options = this.setupPDFOptions(this.reportTask);
              }
              else if (outputFormat.equalsIgnoreCase(RMT2BirtActionHandler.RPT_FORMAT_HTML)) {
                  options = this.setupHTMLOptions(this.reportTask);
              }
              this.reportTask.setRenderOption(options);
              
              // Run report
              this.reportTask.run();
              this.reportTask.close();
              
              this.request.setAttribute(RMT2ServletConst.RESPONSE_PROTOCOL, String.valueOf(RMT2ServletConst.RESPONSE_PROTOCOL_NORMAL));
        
              // Prepare to send results back to the client
              this.sendClientData();
              return;
          }
          catch (EngineException e) {
              e.printStackTrace();
              throw new ActionHandlerException( e );
          }
      }
      
      

      /**
       * Setup HTML render application context and options.
       * 
       * @param task The task to associate context and options
       * @return the HTMLRenderOption
       * @throws ActionHandlerException If a problem occurs creating output stream.
       */
      private HTMLRenderOption setupHTMLOptions(IEngineTask task) throws ActionHandlerException {
          // Setup image directory
          HTMLRenderContext renderContext = new HTMLRenderContext();
          String servletContext = this.request.getContextPath();
          renderContext.setBaseImageURL(servletContext + this.imagePath);
          String servletPath = this.context.getRealPath( this.imagePath);
          renderContext.setImageDirectory(servletPath);
          
           renderContext.setBaseURL(servletContext);
          logger.log( Level.ERROR, "image directory " + servletPath);
          HashMap contextMap = new HashMap();
          contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext);
          task.setAppContext( contextMap );

		  // Set output options
		  HTMLRenderOption options = new HTMLRenderOption();
			
		  // Determine report output format.   Default to HTML.
          // this.response.setHeader ("Content-Disposition","inline; filename=test.pdf");
          this.response.setContentType("text/html");
		  options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);

		  //  Setup output stream
		  try {
			  options.setOutputStream( this.response.getOutputStream());  
		  }
		  catch (IOException e) {
    		  e.printStackTrace();
    		  throw new ActionHandlerException( e );
    	  } 
		  return options;
      }
      
      /**
       * Setup PDF render application context and options.
       * 
       * @param task The task to associate context and options
       * @return the HTMLRenderOption
       * @throws ActionHandlerException If a problem occurs creating output stream.
       */
      private HTMLRenderOption setupPDFOptions(IEngineTask task) throws ActionHandlerException {
          // Setup image directory
          PDFRenderContext renderContext = new PDFRenderContext();
          String servletContext = this.request.getContextPath();
          String servletPath = this.context.getRealPath(this.imagePath);
          
          renderContext.setBaseURL(servletContext);
          logger.log( Level.ERROR, "image directory " + servletPath);
          HashMap contextMap = new HashMap();
          contextMap.put( EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, renderContext);
          task.setAppContext( contextMap );

		  // Set output options
		  HTMLRenderOption options = new HTMLRenderOption();
			
		  // Determine report output format.   Default to HTML.
          // this.response.setHeader ("Content-Disposition","inline; filename=test.pdf");
          this.response.setContentType("application/pdf");
		  options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

		  //  Setup output stream
		  try {
			  options.setOutputStream( this.response.getOutputStream());  
		  }
		  catch (IOException e) {
    		  e.printStackTrace();
    		  throw new ActionHandlerException( e );
    	  } 
		  return options;
      }      
      
    /**
     * Obtains the BIRT Report Engine
     * 
     * @return An instance of IReportEngine
     */
    protected IReportEngine getBirtReportEngine() {
    	return this.birtReportEngine;
    }
    
    /**
     * Obtains the BIRT Report object
     * 
     * @return An instance of IReportRunnable
     */
    protected IReportRunnable getReportObj() {
    	return this.reportObj;
    }
    
    /**
     * Obtains the design representation of the BIRT report.
     * 
     * @return An instance of IReportDesign
     */
    protected IReportDesign getReportDesign() {
    	return this.reportDesign;
    }
    
    /**
     * Obtains the run/render task of the BIRT report.
     *  
     * @return
     */
    protected IRunAndRenderTask getReportTask() {
        return this.reportTask;
    }
    
    /**
     * Obtains the query text of one of the report's DataSets.
     * 
     * @param _dataSetName The target dataset.
     * @return SQL query text or null if data set is not found using _dataSetName.
     */
    protected String getSQL(String _dataSetName) {
    	IDataSet ds = this.getDataSet(_dataSetName); 
    	if (ds == null) {
    		return null;
    	}
    	return ds.getQueryText();
    }
    
    /**
     * Sets the query text for a particular data set belonging to the current report using _dataSetName and _query.   
     * The assignment of the query text is skipped provided _query is invalid or the DataSet cannot be found by 
     * _dataDataSetName. 
     * 
     * @param _dataSetName The name of the target DataSet
     * @param _query The SQL query String to assing to the DataSet.
     */
    protected void setSQL(String _dataSetName, String _query) {
    	if (_query == null || _query.equals("")) {
    		logger.log(Level.DEBUG, "SQL query was not modified for BIRT DataSet, " + _dataSetName + ", since query parameter is invalid for report,  " + this.reportPath);
    		return;
    	}
    	
    	IDataSet ds = this.getDataSet(_dataSetName);
    	if (ds == null) {
    		logger.log(Level.DEBUG, "SQL query was not modified for BIRT DataSet, " + _dataSetName);
    		return;
    	}
    	try {
    		ds.setQueryText(_query);	
    	}
    	catch (ScriptException e) {
    		logger.log(Level.ERROR, e.getMessage());
    	}
    }
    
    /**
     * Locates a DataSet object related to the report by its name.
     * 
     * @param _dataSetName The name of the DataSet to locate
     * @return IDataSet object when found.  Otherwise, null is returned. 
     */
    private IDataSet getDataSet(String _dataSetName) {
    	IDataSet ds = this.reportDesign.getDataSet(_dataSetName); 
    	if (ds == null) {
    		logger.log(Level.DEBUG, "DataSet, " + _dataSetName + ",  was not found for report,  " + this.reportPath);
    		return ds;
    	}
    	logger.log(Level.DEBUG, "DataSet, " + _dataSetName + ",  was found for report,  " + this.reportPath);
    	return ds;
    }
    
    
      /**
       * No Action
       *  
       * @throws ActionHandlerException
       */
    public  void add()  throws ActionHandlerException {
          return;
      }

      /**
       * No Action
       * 
       * @throws ActionHandlerException
       */
    public void edit()  throws ActionHandlerException {
          return;
      }

    /**
     * No Action
     * 
     * @throws ActionHandlerException
     */
  public void delete()  throws ActionHandlerException {
        return;
    }

      /**
       * This method must be implemented in order to provide customized functionality for adding parameters to the report design to be processed by the BIRT report Engine.
       * 
       * @param task The report task that receives the parameters.
       * @throws ActionHandlerException
       */
      protected abstract void setEngineParms(IEngineTask task) throws ActionHandlerException;
      
      
      /**
       * This method must be implemented in order to provide customized functionality for adding parameters to the report design to be processed by the BIRT report Engine.
       * 
       * @param viewrURL
       * @return String
       * @throws ActionHandlerException
       */
      protected abstract String setViewerParms(String viewrURL) throws ActionHandlerException;

      
      /**
       * This method must be implemented in order to provide customized functionality for retrieveing data from the client's request.
       * 
       * @throws ActionHandlerException
       */
      protected abstract void receiveClientData() throws ActionHandlerException;
      
      /**
       * This method must be implemented in order to provide customized functionality for sending data back to the client.
       * 
       * @throws ActionHandlerException
       */
      protected abstract void sendClientData() throws ActionHandlerException;

      /**
       * No Action
       * 
       * @throws ActionHandlerException 
       * @throws DatabaseException when a database access error occurs.
       */
  	  public  void save() throws ActionHandlerException, DatabaseException {
  		  return;
  	  }
}
