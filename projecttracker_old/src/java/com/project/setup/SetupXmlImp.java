package com.project.setup;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.orm.RdbmsDataSourceImpl;
import com.api.messaging.MessagingException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjTask;

import com.bean.bindings.JaxbProjectTrackerFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;

import com.project.ProjectException;

import com.util.SystemException;
import com.xml.schema.bindings.CustomerCriteriaType;
import com.xml.schema.bindings.CustomerType;
import com.xml.schema.bindings.ObjectFactory;

/**
 * @author rterrell
 * 
 */
class SetupXmlImp extends RdbmsDataSourceImpl implements SetupApi {
    private static Logger logger = Logger.getLogger(SetupXmlImp.class);

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * @throws DatabaseException
     * @throws SystemException
     */
    public SetupXmlImp() throws DatabaseException, SystemException {
	return;
    }

    /**
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public SetupXmlImp(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	logger.log(Level.INFO, "Logger is initialized");
    }

    /**
     * @param dbConn
     * @param request
     * @throws DatabaseException
     * @throws SystemException
     */
    public SetupXmlImp(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Retrieve a list of all project clients. Data is order by client's name in
     * ascending order.
     * 
     * @return A XML document representing a list of
     *         {@link com.project.setup.ProjClient ProjClient}
     * @throws ProjectException
     */
    public String findAllClients() throws ProjectException {
	try {
	    ProjClient obj = SetupFactory.createXmlClient();
	    obj.addOrderBy(ProjClient.PROP_NAME, ProjClient.ORDERBY_ASCENDING);
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieve project data for a given client.
     * 
     * @param clientId
     *            The id of the client to obtain project data.
     * @return A XML document representing a list of
     *         {@link com.bean.ProjProject ProjProject} objects.
     * @throws ProjectException
     */
    public String findProjectByClientId(int clientId) throws ProjectException {
	ProjProject obj = SetupFactory.createXmlProject();
	obj.addCriteria(ProjProject.PROP_CLIENTID, clientId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * 
     */
    public String findClientExt() throws ProjectException {
	// Call service to get customer data
	ObjectFactory f = new ObjectFactory();
	JaxbProjectTrackerFactory jptf = new JaxbProjectTrackerFactory();
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	// Setup customer criteria without setting any properties.
	CustomerCriteriaType criteria = f.createCustomerCriteriaType();
	List<CustomerType> customers;
	try {
	    customers = jptf.getClients(criteria, userSession.getLoginId());
	}
	catch (MessagingException e) {
	    throw new ProjectException(e);
	}

	// Build result message
	return jptf.createProjectClientsAsXml(customers);
    }
    
    public int deleteClient(int clientId) throws ProjectException, DatabaseException {
	return 0;
    }

    public int deleteProject(int projectId) throws ProjectException, DatabaseException {
	return 0;
    }

    public int deleteTask(int taskId) throws ProjectException, DatabaseException {
	return 0;
    }

    public Object findAllProjects() throws ProjectException {
	return null;
    }

    public Object findAllTasks() throws ProjectException {
	return null;
    }

    public Object findClient(int clientId) throws ProjectException {
	return null;
    }

    

    public Object findClientExt(int clientId) throws ProjectException {
	return null;
    }

    public Object findProject(int projectId) throws ProjectException {
	return null;
    }

    public Object findTask(int taskId) throws ProjectException {
	return null;
    }

    public Object findTasks(boolean billable) throws ProjectException {
	return null;
    }

    public int maintainClient(ProjClient client) throws ProjectException {
	return 0;
    }

    public int maintainProject(ProjProject proj, ProjClient client) throws ProjectException {
	return 0;
    }

    public int maintainTask(ProjTask task) throws ProjectException {
	return 0;
    }

}
