/**
 * 
 */
package com.remoteservices.http;

import com.api.DaoApi;

import com.api.db.DatabaseException;

import com.api.xml.XmlApiFactory;

import com.bean.RMT2Base;

import com.util.NotFoundException;
import com.util.SystemException;

/**
 * Factory for creating message builders.
 * 
 * @author RTerrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 *
 */
public class MessageFactory extends RMT2Base {

    /**
     * Default constructor.
     */
    protected MessageFactory() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates an instance of ServiceMessageBuilder using a SOAP implementation.
     * 
     * @return {@link ServiceMessageBuilder}
     */
    public static ServiceMessageBuilder getSOAPMessageBuilder() {
        ServiceMessageBuilder builder = new SOAPMessageImpl();
        return builder;
    }

    /**
     * Builds a simple XML document that is based on the layout of a ServiceReturnCode 
     * class from a XML messagethat details the results of service call. The message 
     * is comprised of a return code, outcome message text, and resulting dataset, if 
     * applicable.
     * <pre>
     *  &lt;ServiceReturnCode&gt;
     *     &lt;code&gt;1&lt;/code&gt;
     *     &lt;message&gt;An error occured&lt;/message&gt;
     *     &lt;data&gt;(XML document)&lt;/data&gt;
     *  &lt;/ServiceReturnCode&gt;
     * </pre> 
     * 
     * @param code 
     *          The return code of service which is service specific.
     * @param message 
     *          The actual message related to the return code.
     * @param data
     *          XML document returned from the service which can serve 
     *          as data results from the service call.          
     * @return String 
     *          Xml document.&nbsp;&nbsp; as described in the above method description.
     */
    public static String buildReturnCode(int code, String message, String data) {
        StringBuffer xml = new StringBuffer(100);
        xml.append("<ServiceReturnCode>");
        xml.append("<code>");
        xml.append(code);
        xml.append("</code>");
        xml.append("<message>");
        xml.append(message);
        xml.append("</message>");
        if (data != null && data.length() > 0) {
            xml.append("<data>");
            xml.append(data);
            xml.append("</data>");
        }
        xml.append("</ServiceReturnCode>");
        return xml.toString();
    }

    /**
     * Builds a ServiceReturnCode message instance from a XML message.  The XML message is 
     * required to be in the following format:
     *  <pre>
     *	&lt;ServiceReturnCode&gt;
     * 	   &lt;code&gt;1&lt;/code&gt;
     * 	   &lt;message&gt;An error occured&lt;/message&gt;
     *     &lt;data&gt;(XML document)&lt;/data&gt;
     *	&lt;/ServiceReturnCode&gt;
     * </pre> 
     * 
     * @param msg 
     *          XML message containing the return code and message text.
     * @return {@link com.remoteservices.http.}
     * @throws DatabaseException 
     *          Message property cannot be found from within <i>msg</i> or general data access error. 
     * @throws SystemException 
     *          Problem creating the ServiceReturnCode object.
     */
    public static ServiceReturnCode getResults(String msg) throws DatabaseException, SystemException {
        // Process XML result set.
        DaoApi dao = XmlApiFactory.createXmlDao(msg);
        try {
            dao.retrieve("//ServiceReturnCode");
            String code = null;
            String message = null;
            String data = null;
            ServiceReturnCode rc = new ServiceReturnCode();
            if (dao.nextRow()) {
                code = dao.getColumnValue("code");
                message = dao.getColumnValue("message");
                try {
                    data = dao.getColumnValue("data");
                }
                catch (NotFoundException e) {
                    // Soft error.  Okay
                }
                rc.setCode(Integer.parseInt(code));
                rc.setMessage(message);
            }
            return rc;
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
        finally {
            dao.close();
            dao = null;
        }
    }
}
