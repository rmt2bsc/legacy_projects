package com.api.filehandler;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.RMT2Base;
import com.util.RMT2File;

/**
 * @author appdev
 *
 */
public class InboundFileListener extends RMT2Base implements Runnable {
    private static Logger logger = Logger.getLogger("InboundFileListener");

    private boolean continueToRun;

    private FileListenerConfig config;

    /**
     * Creates an InboundFileListener object initialized with one or more application 
     * module configurations.
     */
    public InboundFileListener() {
	this.init();
    	InboundFileListener.logger.log(Level.INFO, "Logger initialized");
	return;
    }

    /**
     * Get MIME configuration from MimeConfig.properties (Each application should 
     * provide this file)
     */
    public void init() {
	InboundFileListener.logger.log(Level.INFO, "Initializing MIME listener");
	this.config = InboundFileFactory.getConfigInstance();
	this.continueToRun = true;
    }

    /**
     * This is the logic that is executed when the Thread is started.   First, It checks if the 
     * archive directory is locatable and is accessible.  If not accessible, then the thread is 
     * aborted.  Next, an indefinite loop is entered which processes one or more files in the 
     * inbound directory that may available, and then goes into sleep mode for the amount of 
     * time specified in the application's MIME configuration.  
     */
    public void run() {
	InboundFileListener.logger.log(Level.INFO, "Starting MIME listener");
	
	// More than likely the archive destination is a network share.  Verify if user has the 
	// correct permissions to access archive share.
	this.config.setArchiveLocal(true);
	if (this.config.getArchiveDir().startsWith("//")) {
	    // This is a network share and not a directory that is local to this machine
	    if (!RMT2File.isNetworkShareAccessible(this.config.getArchiveDir())) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("MIME Thread failed to start.   The MIME archive destination, ");
		msgBuf.append(this.config.getArchiveDir());
		msgBuf.append(", is a network share and is inaccessible to the user account currently logged on to this machine.  Check permissions for this network share.");
		InboundFileListener.logger.log(Level.FATAL, msgBuf.toString());
		// Abort thread.
		return;
	    }
	    this.config.setArchiveLocal(false);
	    logger.log(Level.INFO, "Connecting to shared resource, " + config.getArchiveDir() + ", to archive images remotely");
	}
	InboundFileListener.logger.log(Level.INFO, "MIME listener started successfully");
	
	MimeFileProcessor processor = null;
	while (this.continueToRun) {
	    try {
		InboundFileListener.logger.log(Level.DEBUG, "MIME listener looking for files to process...");
		int fileCount = 0;
		processor = InboundFileFactory.createCommonMimeFileProcessor(this.config);
		if (processor.isFilesAvailable()) {
		    processor.initConnection();
		    Integer rc = (Integer) processor.processFiles(null, null);
		    fileCount = rc.intValue();
		    if (fileCount > 0) {
		        String msgCount = fileCount + " MIME files were processed for all modules"; 
		        InboundFileListener.logger.log(Level.INFO, msgCount);    
		        // Attempt to send report.
		        if (this.config.isEmailResults()) {
		            try {
		                processor.sendDropReport();
		            }
		            catch (Exception e) {
		                // Do nothing
		            }
		        }
		    }		    
		}
		else {
		    InboundFileListener.logger.debug("No files available for MIME thread to process!");
		}
	    }
	    catch (Exception e) {
		this.msg = e.getMessage();
		InboundFileListener.logger.log(Level.ERROR, this.msg);
	    }
	    finally {
		if (processor != null) {
		    processor.close();    
		}
	    }

	    try {
		InboundFileListener.logger.log(Level.DEBUG, "MIME listener sleeping...");
		Thread.sleep(this.config.getPollFreq());
	    }
	    catch (InterruptedException e) {
		// Do nothing...This thread sleep was interrupted by another thread.
	    }
	}
    }

    /**
     * Signals to the current thread to terminate processing loop.
     */
    public void stopProcess() {
	this.continueToRun = false;
    }
}
