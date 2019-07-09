package com.api.filehandler;


import com.api.BatchFileProcessor;

/**
 * @author appdev
 *
 */
public interface MimeFileProcessor extends BatchFileProcessor {

    static final String CONFIG_CLASSPATH = "com.resources.config.";
    
    /**
     * The implementation of the method should function to obtain a listing of 
     * file names to process for a specified moudedl identified as <i>moduleId</i>.
     * 
     * @param moduleId
     * 
     */
    void setModuleId(int moduleId);
    
    /**
     * Create and send MIME processing report to client
     */
    void sendDropReport();
    
    /**
     * Verifies if data files are available for processing.
     * 
     * @return true when files are availabe, and false otherwise.
     */
    boolean isFilesAvailable();
}
