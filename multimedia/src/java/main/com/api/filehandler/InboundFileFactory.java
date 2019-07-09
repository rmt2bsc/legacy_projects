package com.api.filehandler;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.BatchFileProcessor;
import com.api.config.HttpSystemPropertyConfig;
import com.bean.RMT2Base;

import com.util.RMT2File;

/**
 * A factory for creating MIME file hanlder related objects.
 * 
 * @author appdev
 *
 */
public class InboundFileFactory extends RMT2Base {

    private static Logger logger = Logger.getLogger("InboundFileFactory");
    
    public static final String configFile = "MimeConfig";
    
    public static final String ENV = System.getProperty(HttpSystemPropertyConfig.PROPNAME_ENV);

    /**
     * Create
     */
    private InboundFileFactory() {
	InboundFileFactory.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Creates a FileListenerConfig instance from the default MIME configuration proerties 
     * file, MimeConfig.properties.
     * 
     * @return {@link com.api.filehandler.FileListenerConfig FileListenerConfig}
     */
    public static FileListenerConfig getConfigInstance() {
	String propFile = MimeFileProcessor.CONFIG_CLASSPATH + InboundFileFactory.ENV + "_" + InboundFileFactory.configFile;
	logger.log(Level.INFO, "Looking for MIME Configuration in, " + propFile);
	return InboundFileFactory.getConfigInstance(propFile);
    }
    
    /**
     * Creates a FileListenerConfig instance from a specified configuration file.
     * 
     * @param propFile
     *         a .properties file containing the MIME configuration.
     * @return {@link com.api.filehandler.FileListenerConfig FileListenerConfig}
     */
    public static FileListenerConfig getConfigInstance(String propFile) {
	FileListenerConfig config = new FileListenerConfig();
	String msg = null;
	String propVal = null;

	// Set MIME Database URL
	propVal = RMT2File.getPropertyValue(propFile, "mime.dbURL");
	logger.log(Level.INFO, "mime.dbURL = " + propVal);
	config.setDbUrl(propVal);

	// Set MIME file handler class
	propVal = RMT2File.getPropertyValue(propFile, "mime.handler");
	logger.log(Level.INFO, "mime.handler = " + propVal);
	config.setHandlerClass(propVal);

	// Set email results indicator
	boolean flag = false;
	try {
	    flag = Boolean.parseBoolean(RMT2File.getPropertyValue(propFile, "mime.emailResults"));
	}
	catch (Exception e) {
	    flag = false;
	}
	finally {
	    propVal = RMT2File.getPropertyValue(propFile, "mime.emailResults");
            logger.log(Level.INFO, "mime.emailResults = " + propVal);
	    config.setEmailResults(flag);
	}

	// Set Reporting email recipient
	config.setReportEmail(RMT2File.getPropertyValue(propFile, "mime.reportEmail"));

	// Set Application code
	propVal = RMT2File.getPropertyValue(propFile, "mime.appCode");
        logger.log(Level.INFO, "mime.appCode = " + propVal);
	config.setAppCode(propVal);

	// Set module count
	int moduleCount = 0;
	try {
	    moduleCount = Integer.parseInt(RMT2File.getPropertyValue(propFile, "mime.moduleCount"));
	}
	catch (NumberFormatException e) {
	    moduleCount = 10000;
	    msg = "Module count for MIME listener could not be determined.  Defaulting to zero";
	    InboundFileFactory.logger.log(Level.WARN, msg);
	}
	finally {
	    propVal = RMT2File.getPropertyValue(propFile, "mime.moduleCount");
	    logger.log(Level.INFO, "mime.moduleCount = " + propVal);
	    config.setModuleCount(moduleCount);
	}

	// Load module specific configurations such as datasource, table, primary key, foreign key hashes, and etc.
	for (int ndx = 0; ndx < moduleCount; ndx++) {
	    String moduleCode = RMT2File.getPropertyValue(propFile, "mime.module." + ndx);
//	    int modId = Integer.parseInt(moduleCode);
	    String filePattern = RMT2File.getPropertyValue(propFile, "mime.module." + ndx + ".filePattern");
	    String dbUrl = RMT2File.getPropertyValue(propFile, "mime.module." + ndx + ".datasource.url");
	    String table = RMT2File.getPropertyValue(propFile, "mime.module." + ndx + ".datasource.table");
	    String primaryKey = RMT2File.getPropertyValue(propFile, "mime.module." + ndx + ".datasource.pk");
	    String foreignKey = RMT2File.getPropertyValue(propFile, "mime.module." + ndx + ".datasource.fk");

	    ModuleConfig mod = new ModuleConfig(ndx); 
	    mod.setFilePattern(filePattern);
	    mod.setDbUrl(dbUrl);
	    mod.setTable(table);
	    mod.setPrimaryKey(primaryKey);
	    mod.setForeignKey(foreignKey);
	    config.getModules().put(ndx, mod);
	}

	// Set Polling frequency
	int pollFreq = 0;
	try {
	    pollFreq = Integer.parseInt(RMT2File.getPropertyValue(propFile, "mime.pollFreq"));
	}
	catch (NumberFormatException e) {
	    pollFreq = 10000;
	    msg = "Polling frequency for MIME listener could not be determined.  Defaulting to 10 seconds";
	    InboundFileFactory.logger.log(Level.WARN, msg);
	}
	finally {
	    propVal = RMT2File.getPropertyValue(propFile, "mime.pollFreq");
	    logger.log(Level.INFO, "mime.pollFreq = " + propVal);
	    config.setPollFreq(pollFreq);
	}

	// Set In Bound Directory
	propVal = RMT2File.getPropertyValue(propFile, "mime.inboundDir");
	logger.log(Level.INFO, "mime.inboundDir = " + propVal);
	config.setInboundDir(propVal);

	// Set Out Bound Directory
	propVal = RMT2File.getPropertyValue(propFile, "mime.outboundDir");
	logger.log(Level.INFO, "mime.outboundDir = " + propVal);
	config.setOutboundDir(propVal);

	// Set Archive directory
	propVal = RMT2File.getPropertyValue(propFile, "mime.archiveDir");
	logger.log(Level.INFO, "mime.archiveDir = " + propVal);
	config.setArchiveDir(propVal);

	// Set archive age
	int archiveAge = 0;
	try {
	    archiveAge = Integer.parseInt(RMT2File.getPropertyValue(propFile, "mime.archiveAge"));
	}
	catch (NumberFormatException e) {
	    archiveAge = 72;
	    msg = "Archiving age for MIME listener could not be determined.  Defaulting to 72 hours";
	    InboundFileFactory.logger.log(Level.WARN, msg);
	}
	finally {
	    propVal = RMT2File.getPropertyValue(propFile, "mime.archiveAge");
	    logger.log(Level.INFO, "mime.archiveAge = " + propVal);
	    config.setArchiveAge(archiveAge);
	}
	return config;
    }
    
  
    /**
     * Creates an instance of BatchFileProcessor iterface using the implementation, 
     * CommonMimeFileProcessorImp.
     * 
     * @param config
     *          the application's MIME configuration instance.
     * @return {@link com.api.filehandler.MimeFileProcessor MimeFileProcessor}
     */
    public static MimeFileProcessor createCommonMimeFileProcessor(FileListenerConfig config) {
	MimeFileProcessor api;
	try {
	    api = new CommonMimeFileProcessorImp(config);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

}
