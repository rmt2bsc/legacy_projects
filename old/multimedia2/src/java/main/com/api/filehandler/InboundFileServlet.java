package com.api.filehandler;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;


/**
 * Servlet responsible for creating and starting an instance of the File Handler thread 
 * using a stock implementation of InboundFileListener runnable interface.
 */
public class InboundFileServlet extends HttpServlet {

    private static final long serialVersionUID = 6183498279595854520L;

    private InboundFileListener listener = null;

    /**
     * Creates and starts an instance of the InboundFileListener runnable interface 
     * as the File Handler thread.
     */
    @Override
    public void init() throws ServletException {
	super.init();
	// Initialize logging.  Each application should have its own logging configuration
	this.listener = new InboundFileListener();
	Thread t = new Thread(this.listener);
	t.start();
    }
    
   /**
    * Terminates the File Handler thread.
    */
    @Override
    public void destroy() {
	super.destroy();
	this.listener.stopProcess();
    }

}
