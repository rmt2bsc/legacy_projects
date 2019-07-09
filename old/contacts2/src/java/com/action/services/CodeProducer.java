package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.codes.GeneralCodeException;
import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;



/**
 * Web service for obtaining general code data base on either the code id or 
 * code group id.  The result of each query is returned to the caller as 
 * a XML String as the service payload.
 * <p>   
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * 
 * @author RTerrell
 * @deprecated No Longer used.  will be removed from future versions
 *
 */
public class CodeProducer extends AbstractContactProducer {
    private static final String ARG_CODEID = "CodeId";
    private static final String ARG_GROUPID = "GroupId";
    
    private static final String SRVC_CODE = "Services.getCode";
    private static final String SRVC_GROUP = "Services.getCodeGroup";
    private static final String SRVC_CODESGROUP = "Services.getCodeByGroup";
    private static final String SRVC_CODECRITERIA = "Services.getCodeByCriteria";
    private Logger logger;
       
   
    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public CodeProducer() throws SystemException {
	super();
        this.logger = Logger.getLogger("CodeProducer");
    }
    

    /**
     * Processes the user's request to retrieve General code data filtered by 
     * code id or code group id.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        String temp;
        String xml = null;
        
        Properties prop = (Properties) this.inData;
        
        if (this.command.equalsIgnoreCase(CodeProducer.SRVC_CODE)) {
            temp = prop.getProperty(CodeProducer.ARG_CODEID);
            xml = this.getCode(temp);
        }
        if (this.command.equalsIgnoreCase(CodeProducer.SRVC_GROUP)) {
            temp = prop.getProperty(CodeProducer.ARG_GROUPID);
            xml = this.getGroup(temp);
        }
        if (this.command.equalsIgnoreCase(CodeProducer.SRVC_CODESGROUP)) {
            temp = prop.getProperty(CodeProducer.ARG_GROUPID);
            xml = this.getCodeByGroup(temp);
        }
        if (this.command.equalsIgnoreCase(CodeProducer.SRVC_CODECRITERIA)) {
            xml = this.getCodesByCriteria(prop);
        }
        this.outData = xml;
    }
    
    /**
     * Retrieves general code data based on code id.
     * 
     * @param codeIdStr The code id
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getCode(String codeIdStr) throws ActionHandlerException {
        Object xml;
        if (codeIdStr ==null) {
            throw new ActionHandlerException("General Code id is required for service, " + this.command);
        }
        int codeId;
        try {
            codeId = Integer.parseInt(codeIdStr);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid General Code Id was passed: " + codeIdStr;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createXmlCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findCodeById(codeId);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (GeneralCodeException e) {
            e.printStackTrace();
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
    	    api.close();
    	    tx.close();
    	    api = null;
    	    tx = null;
    	}
    }
    
    /**
     * Retrieves a list of groups based on group code id.
     * 
     * @param groupIdStr The group id as a String.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getGroup(String groupIdStr) throws ActionHandlerException {
        Object xml;
        if (groupIdStr == null) {
            throw new ActionHandlerException("General Code Group Id is required for service, " + this.command);
        }
        int groupId;
        try {
            groupId = Integer.parseInt(groupIdStr);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid General Code Group Id was passed: " + groupIdStr;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createXmlCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findGroupById(groupId);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (GeneralCodeException e) {
            e.printStackTrace();
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
    	    api.close();
    	    tx.close();
    	    api = null;
    	    tx = null;
    	}
    }
    
    /**
     * Retrieves a list of general code data based on group id.
     * 
     * @param groupIdStr The group id as a String.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getCodeByGroup(String groupIdStr) throws ActionHandlerException {
        Object xml;
        if (groupIdStr == null) {
            throw new ActionHandlerException("State is required for service, " + this.command);
        }
        int groupId;
        try {
            groupId = Integer.parseInt(groupIdStr);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid General Code Group Id was passed: " + groupIdStr;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createXmlCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findCodeByGroup(groupId);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (GeneralCodeException e) {
            e.printStackTrace();
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
    	    api.close();
    	    tx.close();
    	    api = null;
    	    tx = null;
    	}
    }    
  

    /**
     * 
     * @param prop
     * @return
     * @throws ActionHandlerException
     */
    protected String getCodesByCriteria(Properties prop) throws ActionHandlerException {
        Object xml;
        String whereSql = prop.getProperty(CodeProducer.ARG_SELECTCRITERIA);
        StringBuffer sql = new StringBuffer(100);
        
        if (whereSql != null) {
            if (sql.length() > 0) {
        	sql.append(" and ");
            }
            sql.append(whereSql);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CodesApi api = CodesFactory.createXmlCodesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findCode(sql.toString());
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (GeneralCodeException e) {
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
    	    api.close();
    	    tx.close();
    	    api = null;
    	    tx = null;
    	}
    }    
}
