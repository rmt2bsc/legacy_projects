package com.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.project.ProjectException;

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;

import com.remoteservices.http.AbstractExternalServerAction;

import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * Project Tracker web service that performs various client and project related queries
 * The result of each query is returned to the caller as a XML String as the service 
 * payload in the following format:
 * 
 * See {@link com.remoteservices.http.AbstractExternalServerAction
 * AbstractExternalServerAction} for a detailed description of RSPayload format.
 * <p>
 * 
 * @author RTerrell
 * @deprecated  use {@link com.services.projectclients.ClientProjectsService ClientProjectsService}
 * 
 */
public class ProjectProducer extends AbstractExternalServerAction {
    private static final String ARG_CLIENTID = "ClientId";

    private static final String SRVC_getClientProjects = "Services.getClientProjects";

    private static Logger logger = Logger.getLogger(ProjectProducer.class);

    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public ProjectProducer() throws SystemException {
	super();
	ProjectProducer.logger.log(Level.INFO, "Logger Initialized");
    }

    /**
     * Processes the user's request to retrieve client and project data filtered
     * by client id.
     *  
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
	String xml = null;

	Properties prop = (Properties) this.inData;

	if (this.command.equalsIgnoreCase(ProjectProducer.SRVC_getClientProjects)) {
	    xml = this.getClientProjects(prop);
	}

	this.outData = xml;
    }

    /**
     * Provides basic functionality for retreiving the service request
     * paramaters to be used by descendent. The parameters are basically
     * transferred from the HttpServletRequest object to a Properties collection
     * which in turn is stored internally as the service's input data.
     * 
     * @throws ActionHandlerException
     */
    public void receiveClientData() throws ActionHandlerException {
	Properties prop = RMT2Utility.getRequestData(this.request);
	this.inData = prop;
	logger.log(Level.DEBUG, "Service producer parameters accessed successfully");
    }

    /**
     * Retrieves all projects related to a client.
     * 
     * @param props
     *            should contain value of the client id as <i>ClinetID</i>
     * @return XML document
     * @throws ActionHandlerException
     *             Genereal errors
     */
    protected String getClientProjects(Properties props) throws ActionHandlerException {
	Object xml;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);

	int clientId;
	try {
	    String val = props.getProperty(ProjectProducer.ARG_CLIENTID);
	    clientId = Integer.parseInt(val);
	}
	catch (NumberFormatException e) {
	    clientId = 0;
	}
	try {
	    xml = api.findProjectByClientId(clientId);
	    if (xml != null) {
		return xml.toString();
	    }
	    return null;
	}
	catch (ProjectException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Builds the results of an insert, update, or delete as XML operation which
     * details the business id, address id, total count of records effected from
     * the transaction, and whether the target data was new or existing.
     * 
     * @param businessId
     *            The contact's business id.
     * @param addressId
     *            The contact's address id.
     * @param count
     *            The count of rows effected from the transaction
     * @param newRec
     *            Is true for insert operations and false for update/delete
     *            operations.
     * @return String Xml document.&nbsp;&nbsp;The data layout of the results
     *         goes as follows:
     * 
     *         <pre>
     *     &lt;BusinessId&gt;3212&lt;/BusinessId&gt;
     *     &lt;AddressId&gt;84328&lt;/AddressId&gt;
     *     &lt;RowCount&gt;2&lt;/RowCount&gt;
     *     &lt;MaintType&gt;insert&lt;/MaintType&gt;
     *          <ol>
     *            <li><b>PersonId</b> - The internal id of the business entity processed.</li>
     *            <li><b>AddressId</b> - The internal id of the business' address entity processed.</li>
     *            <li><b>RowCount</b> - The total number of rows effected by the transaction.</li>
     *            <li><b>MaintType</b> - Indicates whether an insert or an update/delete was performed to maintain the contact.</li>
     *          </ol>
     * </pre>
     */
    protected String buildUpdateMessage(int customerId, int count, boolean newRec) {
	// Setup return message
	StringBuffer xml = new StringBuffer(100);
	xml.append("<Error>");
	xml.append("<CustomerId>");
	xml.append(customerId);
	xml.append("</CustomerId>");
	xml.append("<RowCount>");
	xml.append(count);
	xml.append("</RowCount>");
	xml.append("<MaintType>");
	xml.append((newRec ? "insert" : "update/delete"));
	xml.append("</MaintType>");
	xml.append("</Error>");
	return xml.toString();
    }
}
