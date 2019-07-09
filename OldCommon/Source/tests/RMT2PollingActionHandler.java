package com.action;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.api.RMT2DBTransApi;

import com.bean.RMT2Base;
import com.bean.RMT2DBConnectionBean;

import com.api.RMT2DataSourceApi;
import com.apiimpl.RMT2DataSourceApiImpl;

import com.factory.RMT2DBTransFactory;

import com.servlet.RMT2PollingAbstractServlet;

import com.util.SystemException;

import com.constants.RMT2SystemExceptionConst;
import com.constants.RMT2ServletConst;

import com.util.SystemException;
import com.util.NotFoundException;
import com.util.DatabaseException;
import com.util.BusinessException;
import com.util.RMT2Utility;

import com.constants.RMT2SqlConst;


/**
 * Abstract base class for handling actions from the controller servlet using threads.
 * 
 * @author rterrell
 *
 */
public abstract class RMT2PollingActionHandler extends RMT2ServletActionHandler  {

	protected static final String POLLING = "POLLING";
	protected RMT2PollingAbstractServlet servlet;
	protected String  pollKey;
   protected String pollService;
   protected String successPagePath;
   protected String successPage;
	protected Object outData;
	protected Object inData;
	protected boolean isInputDataOk;
	protected boolean isCriteriaOk;
	protected boolean isOutputDataOk;
	protected String errorMsg;
	protected boolean isError;
	
	
  ////////////////////////////////////
  //    Constructors
  ////////////////////////////////////
  /**
   * Constructor used to initialize this object's servlet context, Http request, and polling servlet objects.
   * 
   * @param _context The servlet context
   * @param _request The Http Request object.
   * @param _servlet A reference to the servlet that created this object
   * @throws SystemException
   */	
  protected RMT2PollingActionHandler(ServletContext _context, HttpServletRequest _request, RMT2PollingAbstractServlet _servlet) throws SystemException {
    super(_context, _request);
    this.methodName = "RMT2ServletPollingActionHandler(ServletContext, HttpServletRequest, Servlet)";
    servlet = _servlet;
    this.pollKey = null;
    this.pollService = null;
    this.successPagePath = null;
    this.successPage = null;
    this.outData = null;
    this.inData = null;
    this.isInputDataOk = false;
    this.isCriteriaOk = false;
    this.isOutputDataOk = false;
    this.errorMsg = null;
    this.isError = false;
  }
  
  
  /**
   * This is the driver of the a multi-threaded database query which is responsible for securing the input data, selection criteria,
   * executing the SQL Query, and sending the results back to the user as separate processes.
   *
   * @return String - process key used to track the user's query results from the calling servlet.
   */	
  public String execClientRequest() {
      this.pollKey = createPollingKey();
  	
      //  Instantiate runnable interfaces
      InputProcess ip = new InputProcess(this);
      CriteriaProcess cp = new CriteriaProcess(this);
      QueryProcess qp = new QueryProcess(this);
	  ReplyProcess rp = new ReplyProcess(this);
  	
	  //  Create threads
	  Thread inputThread = new Thread(ip);
	  Thread criteriaThread = new Thread(cp);
	  Thread queryThread = new Thread(qp);
	  Thread replyThread = new Thread(rp);
	  	
      	// Data Input Thread
      	try {
            System.out.println("[RMT2PollingActionHandler.execClientRequest]  Executing thread to gather input data");
    	  	inputThread.start();
      		inputThread.join();    
      	}
      	catch (InterruptedException e) {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Input data thread completed");
      	}
      	
      //	 Search criteria Thread
      	try {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Executing thread to build search criteria");
      	    criteriaThread.start();
      	    criteriaThread.join();
      	}
      	catch (InterruptedException e) {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Search criteria thread completed");
      	}
      	
      	// Query Thread
      	try {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Executing thread to perform query");
      	    queryThread.start();
      	    queryThread.join();
      	}
      	catch (InterruptedException e) {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Query thread completed");
      	}
      	
      	// Client reply thread
      	try {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Executing thread to reply to client");
      	    replyThread.start();
      	    replyThread.join();
      	}
      	catch (InterruptedException e) {
      	    System.out.println("[RMT2PollingActionHandler.execClientRequest]  Client reply thread completed");
      	}
      	this.request.setAttribute("pollKey", this.pollKey);
      	this.request.setAttribute("pollService", this.pollService);
      	this.request.setAttribute("successPagePath", this.successPagePath);
        this.request.setAttribute("successPage", this.successPage);
      	return this.pollKey;
  }
  
  /**
   * Abstract method that is required to be implemented from inheriting class.   The intent of this method is to gather input data from the 
   * Http Servlet Request object.   This process will have to complete before the buildCriteria() thread is made runnable.
   *  
   * @return int
   */
  protected abstract int getInputData();
  
  /**
   * Abstract method that is required to be implemented from inheriting class.   The intent of this method is to create selection criteria 
   * from the input data obtained from getInputData() thread.   This process will have to complete before the execQuery() thread is made runnable.
   *  
   * @return int
   */
  protected abstract int buildCriteria();
  
  /**
   * Abstract method that is required to be implemented from inheriting class.   The intent of this method is to execute the sql query tailored 
   * from getInputData() and buildCriteria() threads.   This process will have to complete before the sendReply() thread is made runnable.
   *  
   * @return int
   */
  protected abstract int execQuery();
  
  /**
   * Abstract method that is required to be implemented from inheriting class.   The intent of this method is to gather the results of 
   * execQuery() thread, package, and send back to the client.   This is the last thread of the polling client query process.
   *  
   * @return int
   */
  protected abstract int sendReply();
  
  
  //  Necessary threads implemented as inner classes
  /**
   * Runnable Implementation to be used with the getInputData thread
   *  
   * @author rterrell
   *
   */
  class InputProcess implements Runnable {
  	    RMT2PollingActionHandler handler;
  	
  	    InputProcess(RMT2PollingActionHandler _handler) {
  	        handler = _handler;
  	    }
  	
  	    public void run() {
  	        handler.getInputData();
  	    }
  }
  
  /**
   * Runnable Implementation to be used with the buildCriteria thread
   *  
   * @author rterrell
   *
   */
  class CriteriaProcess implements Runnable {
  	    RMT2PollingActionHandler handler;
  	
  	    CriteriaProcess(RMT2PollingActionHandler _handler) {
  	        handler = _handler;
  	    }
  	
  	    public void run() {
  	        handler.buildCriteria();
  	    }
  }
  
  /**
   * Runnable Implementation to be used with the execQuery thread
   *  
   * @author rterrell
   *
   */
  class QueryProcess implements Runnable {
      RMT2PollingActionHandler handler;
  	
      QueryProcess(RMT2PollingActionHandler _handler) {
          handler = _handler;
      }
  	
      public void run() {
          int rc = 0;
          rc = handler.execQuery();
          if (rc == XactQueryPollingAction.RC_FAILURE) {
              isError = true;
          }
      }
  }
  
  /**
   * Runnable Implementation to be used with the sendReply thread
   *  
   * @author rterrell
   *
   */
  class ReplyProcess implements Runnable {
      RMT2PollingActionHandler handler;
  	
      ReplyProcess(RMT2PollingActionHandler _handler) {
          handler = _handler;
      }
  	
      public void run() {
          handler.sendReply();
      }
  }
  
  /**
   * Generates the polling key that is used by the controller to retrun data to a polling client.   The key is comprised of the client's
   * Session Id and the Controller's )Servlet) name.    If the key has allready been generated then the current key value is returned.
   * Otherwise, a new key value is created and returned to the controller.
   * 
   * @return  String - Process key used to track the user's query results from the calling servlet.
   */
  protected String createPollingKey() {
      StringBuffer temp = new StringBuffer(100);
    
      if (this.pollKey != null) {
          return this.pollKey;
      }
      temp.append(this.session.getId());
      temp.append("-");
      temp.append(this.servlet.getClass().getName());
      this.pollKey = temp.toString();
  	
      return this.pollKey;
  }
  
  /////////////////////////////////
  // Getter methods
  /////////////////////////////////

  /**
   * Returns the servlet reference that called this object
   * 
   * @return {@link RMT2PollingAbstractServlet}
   */
  public RMT2PollingAbstractServlet getServlet() {
      return this.servlet;
  }
  /**
   * Returns the user's poll service key
   * 
   * @return String
   */
  public String getPollService() {
      return this.pollService;
  }
  /**
   * Returns the path of the success page to be displayed by the client
   * 
   * @return String
   */
  public String getSuccessPagePath() {
      return this.successPagePath;
  } 
  /**
   * Returns the name of the success page that is to be displayed by the client
   * 
   * @return String
   */
  public String getSuccessPage() {
      return this.successPage;
  }
}