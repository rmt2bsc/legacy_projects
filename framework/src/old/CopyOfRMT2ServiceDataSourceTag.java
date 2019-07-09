package com.taglib.xml;

import javax.servlet.jsp.JspException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.remoteservices.http.HttpRemoteServicesConsumer;
import com.taglib.RMT2TagSupportBase;

import com.action.ActionHandlerException;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.RMT2SessionBean;
import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;
import com.controller.stateless.scope.HttpVariableScopeFactory;

/**
 * Queries an external data source using the services module and exports the 
 * resuls to the calling JSP as a XML String.  An example of how to use this
 * tag in a JSP goes as follows: 
 * <pre>
 *    &lt;xml:WebServiceQuery id="dsoName" 
 *                            serviceId="getState"
 *                            where=" country_id = 1 and description like \'%A\'"
 *                            order="descrioption asc"/&gt;
 * </pre>
 * 
 * @author roy.terrell
 *
 */
public class CopyOfRMT2ServiceDataSourceTag extends RMT2TagSupportBase {
    private static final long serialVersionUID = 7925895142246509976L;

    /** The name of the mapping schema to assoicate with the datasource */
    protected String serviceId = null;

    /** The selection criteria to apply to the datasource */
    protected String where = null;

    /** The ordering criteria to apply to the datasource */
    protected String order = null;

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(String serviceId) {
	this.serviceId = serviceId;
    }

    /**
     * Sets the selection criteria that is to be used with the datasource
     * 
     * @param value
     */
    public void setWhere(String value) {
	this.where = value;
    }

    /**
     * Sets the order criteria that is to be used with the datasource
     * @param value
     */
    public void setOrder(String value) {
	this.order = value;
    }

    /**
     * Uses an instance of {@link com.remoteservices.http.HttpRemoteServicesConsumer HttpRemoteServicesConsumer} 
     * to execute the service identified as, <i>serviceId</i>, using custom selection and 
     * ordering criteria.  Returns the XML results to the ancestor for exprting to JSP.  
     * 
     * @return A String as XML.
     * @throws JspException General action handler errors.
     */
    protected Object initObject() throws JspException {
	super.initObject();
	Object xml;
	HttpSession session = pageContext.getSession();
	RMT2SessionBean sessionBean = (RMT2SessionBean) session.getAttribute(RMT2ServletConst.SESSION_BEAN);
	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
	HttpRemoteServicesConsumer consumer = new HttpRemoteServicesConsumer();
	try {
	    if (sessionBean != null) {
		request.setAttribute(AuthenticationConst.AUTH_PROP_USERID, sessionBean.getLoginId());
	    }
	    else {
		request.setAttribute(AuthenticationConst.AUTH_PROP_USERID, AuthenticationConst.FAKE_USER);
	    }
	    request.setAttribute("WhereCriteria", this.where);
	    request.setAttribute("OrderCriteria", this.order);
        
        Request genericRequest = HttpVariableScopeFactory.createHttpRequest(request);
        Response genericResponse = HttpVariableScopeFactory.createHttpResponse(response);
	    consumer.processRequest(genericRequest, genericResponse, this.serviceId);
	    xml = consumer.getServiceResults();
	    return xml;
	}
	catch (ActionHandlerException e) {
	    throw new JspException(e);
	}
    }

}