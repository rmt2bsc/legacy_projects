package com.api.filehandler;

import java.util.HashMap;
import java.util.Map;

import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * A bean representing the Application's MIME configuration
 * 
 * @author appdev
 *
 */
public class FileListenerConfig extends RMT2BaseBean {

    private static final long serialVersionUID = 518549554653952863L;

    private String dbUrl;
    
    private boolean emailResults;
    
    private String reportEmail;
    
    private String appCode;
    
    private int moduleCount;
    
    private Map<Integer, ModuleConfig> modules;
    
    private String inboundDir;
    
    private String outboundDir;
    
    private String archiveDir;
    
    private String fetchDir;
    
    private int archiveAge;
    
    private int pollFreq;

    private String handlerClass;
    
    private boolean archiveLocal;


    /**
     * @throws SystemException
     */
    FileListenerConfig() throws SystemException {
	super();
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    public void initBean() throws SystemException {
	this.modules = new HashMap<Integer, ModuleConfig>();
	return;
    }


    /**
     * @return the reportEmail
     */
    public String getReportEmail() {
        return reportEmail;
    }

    /**
     * @param reportEmail the reportEmail to set
     */
    public void setReportEmail(String reportEmail) {
        this.reportEmail = reportEmail;
    }

    /**
     * @return the moduleCount
     */
    public int getModuleCount() {
        return moduleCount;
    }

    /**
     * @param moduleCount the moduleCount to set
     */
    public void setModuleCount(int moduleCount) {
        this.moduleCount = moduleCount;
    }

    /**
     * @return the inboundDir
     */
    public String getInboundDir() {
        return inboundDir;
    }

    /**
     * @param inboundDir the inboundDir to set
     */
    public void setInboundDir(String inboundDir) {
        this.inboundDir = inboundDir;
    }

    /**
     * @return the archiveDir
     */
    public String getArchiveDir() {
        return archiveDir;
    }

    /**
     * @param archiveDir the archiveDir to set
     */
    public void setArchiveDir(String archiveDir) {
        this.archiveDir = archiveDir;
    }

    /**
     * @return the archiveAge
     */
    public int getArchiveAge() {
        return archiveAge;
    }

    /**
     * @param archiveAge the archiveAge to set
     */
    public void setArchiveAge(int archiveAge) {
        this.archiveAge = archiveAge;
    }

    /**
     * @return the modules
     */
    public Map<Integer, ModuleConfig> getModules() {
        return modules;
    }

    /**
     * @return the pollFreq
     */
    public int getPollFreq() {
        return pollFreq;
    }

    /**
     * @param pollFreq the pollFreq to set
     */
    public void setPollFreq(int pollFreq) {
        this.pollFreq = pollFreq;
    }

    /**
     * @return the handlerClass
     */
    public String getHandlerClass() {
        return handlerClass;
    }

    /**
     * @param handlerClass the handlerClass to set
     */
    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }

    /**
     * @return the appCode
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * @param appCode the appCode to set
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * @return the outboundDir
     */
    public String getOutboundDir() {
        return outboundDir;
    }

    /**
     * @param outboundDir the outboundDir to set
     */
    public void setOutboundDir(String outboundDir) {
        this.outboundDir = outboundDir;
    }

    /**
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * @return the emailResults
     */
    public boolean isEmailResults() {
        return emailResults;
    }

    /**
     * @param emailResults the emailResults to set
     */
    public void setEmailResults(boolean emailResults) {
        this.emailResults = emailResults;
    }

    /**
     * @return the archiveLocal
     */
    public boolean isArchiveLocal() {
        return archiveLocal;
    }

    /**
     * @param archiveLocal the archiveLocal to set
     */
    public void setArchiveLocal(boolean archiveLocal) {
        this.archiveLocal = archiveLocal;
    }

    /**
     * @return the fetchDir
     */
    public String getFetchDir() {
        return fetchDir;
    }

    /**
     * @param fetchDir the fetchDir to set
     */
    public void setFetchDir(String fetchDir) {
        this.fetchDir = fetchDir;
    }

   

}
