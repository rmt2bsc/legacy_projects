package com.project.setup;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.math.BigInteger;
import java.util.List;

import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.orm.RdbmsDataSourceImpl;
import com.api.messaging.MessagingException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjProjectTask;
import com.bean.ProjTask;
import com.bean.VwProjectClient;

import com.bean.bindings.JaxbProjectTrackerFactory;
import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import com.project.ProjectException;

import com.util.NotFoundException;
import com.util.RMT2Date;
import com.util.SystemException;
import com.xml.schema.bindings.CustomerCriteriaType;
import com.xml.schema.bindings.CustomerType;
import com.xml.schema.bindings.ObjectFactory;

/**
 * A database implementation of {@link com.project.setup.SetupApi SetupApi} that manages 
 * the setup and maintenance of clients, projects, and tasks entities.
 * 
 * @author appdev
 *
 */
public class SetupImpl extends RdbmsDataSourceImpl implements SetupApi {
    private static Logger logger  = Logger.getLogger("SetupImpl");

    protected RdbmsDaoQueryHelper daoHelper;

//    private Response response;

    /**
     * Default Constructor which is responsible for setting up the logger.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected SetupImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Create a SetupImpl object containing a database connection.
     * 
     * @param dbConn The database connection bean.
     * @throws DatabaseException
     * @throws SystemException
     */
    public SetupImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);

    }

    /**
     * Create a SetupImpl object which will contain a database connection and the 
     * user's request.
     * 
     * @param dbConn The database connection bean
     * @param request The user's request object
     * @throws DatabaseException
     * @throws SystemException
     */
    public SetupImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn);
	this.setRequest(request);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Create a SetupImpl object which will contain a database connection, the 
     * user's request and the user's response.
     * 
     * @param dbConn The database connection bean.
     * @param request The user's request object
     * @param response The user's response object.
     * @throws DatabaseException
     * @throws SystemException
     */
    public SetupImpl(DatabaseConnectionBean dbConn, Request request, Response response) throws DatabaseException, SystemException {
	this(dbConn, request);
//	this.response = response;
    }

    /**
     * Retrieve a client by client id.
     * 
     * @param clientId 
     *          The id of the client.  Usually corresponds to the customer id.
     * @return {@link ProjClient} as an arbitrary object.
     * @throws ProjectException
     */
    public ProjClient findClient(int clientId) throws ProjectException {
	ProjClient obj = SetupFactory.createClient();
	obj.addCriteria(ProjClient.PROP_CLIENTID, clientId);
	try {
	    return (ProjClient) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }
    
    /**
     * Retrieve a list of all project clients.  Data is order by client's name in ascending order.
     * 
     * @return {@link com.project.setup.ProjClient ProjClient}
     * @throws ProjectException
     */
    public List <ProjClient> findAllClients() throws ProjectException {
	// Get ProjClient objects
	List <ProjClient> clientList;
	try {
	    ProjClient obj = SetupFactory.createClient();
	    obj.addOrderBy(ProjClient.PROP_NAME, ProjClient.ORDERBY_ASCENDING);
	    clientList = this.daoHelper.retrieveList(obj);
	    return clientList;
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }
    
    /**
     * Retrieve the remote extension data of a client.  The remote extension data 
     * lives on the remote system, Contacts, which requires a web service call to
     * <i>getCustomer</i>.
     * 
     * @param clientId 
     *          The id of the client.  Usually corresponds to the customer id.
     * @return {@link com.bean.ProjClient ProjClient} as an arbitrary object.  
     *         No longer returns {@link VwClientExt}.
     * @throws ProjectException
     */
    public ProjClient findClientExt(int clientId) throws ProjectException {
        if (clientId == 0) {
            this.msg = " Client id is invalid.  The client id must be a value greater than zero.";
            logger.error(this.msg);
            throw new ProjectException(this.msg);
        }
        // Call service to get customer data
        ObjectFactory f = new ObjectFactory();
        JaxbProjectTrackerFactory jptf = new JaxbProjectTrackerFactory();
        RMT2SessionBean userSession  = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        
        // Setup customer criteria without setting any properties.
        CustomerCriteriaType criteria = f.createCustomerCriteriaType();
        criteria.getCustomerId().add(BigInteger.valueOf(clientId));
        List<CustomerType> customers;
        try {
            customers = jptf.getClients(criteria, userSession.getLoginId());
        }
        catch (MessagingException e) {
            throw new ProjectException(e);
        }
        
        // Build result message
        List <ProjClient> clients = jptf.createProjectClients(customers);
        if (clients.size() == 0) {
            this.msg = "The contact was not found by client id: " + clientId;
            logger.error(this.msg);
            throw new NotFoundException(this.msg);
        }
        if (clients.size() > 1) {
            this.msg = "The contacts system returned too many clients when querying for client id: " + clientId + ".  Client must be unique.";
            logger.error(this.msg);
            throw new ProjectException(this.msg);
        }
        return clients.get(0);
    }
    
    
    /**
     * Retrieve a complete list of ProjClient objects containing extension data of a client.  
     * The remote extension data lives on the remote system, Contacts, which requires a web 
     * service call to <i>getAllCustomers</i>.
     * 
     * @return A List of {@link com.bean.ProjClient ProjClient} objects  
     * @throws ProjectException
     */
    public List <ProjClient> findClientExt() throws ProjectException {
	// Call service to get customer data
	ObjectFactory f = new ObjectFactory();
	JaxbProjectTrackerFactory jptf = new JaxbProjectTrackerFactory();
	RMT2SessionBean userSession  = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	
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
	return jptf.createProjectClients(customers);
    }

    /**
     * Retrieves all project-client combined that exist in the system.
     * 
     * @return A List of {@link com.bean.VwProjectClient VwProjectClient} objects
     * @throws ProjectException
     */
    public List<VwProjectClient> findAllProjects() throws ProjectException {
	VwProjectClient obj = SetupFactory.createVwProjectClient();
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves a single occurrence of a project using <i>projectId</i>.
     * 
     * @param projectId The id of a project.
     * @return {@link com.bean.ProjProject ProjProject}
     * @throws ProjectException
     */
    public ProjProject findProject(int projectId) throws ProjectException {
	ProjProject obj = SetupFactory.createProject();
	obj.addCriteria(ProjProject.PROP_PROJID, projectId);
	try {
	    return (ProjProject) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieve project data for a given client.
     * 
     * @param clientId The id of the client to obtain project data.
     * @return A List of {@link com.bean.ProjProject ProjProject} objects.
     * @throws ProjectException
     */
    public List <ProjProject> findProjectByClientId(int clientId) throws ProjectException {
	ProjProject obj = SetupFactory.createProject();
	obj.addCriteria(ProjProject.PROP_CLIENTID, clientId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves a tasks using taskId.
     * 
     * @param taskId  The Id of the task to locate.
     * @return A {@link com.bean.ProjTask ProjTask}
     * @throws ProjectException
     */
    public ProjTask findTask(int taskId) throws ProjectException {
	ProjTask obj = SetupFactory.createTask();
	obj.addCriteria(ProjTask.PROP_TASKID, taskId);
	try {
	    return (ProjTask) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves a complete list of tasks that exist in the system.
     * 
     * @return A List of {@link com.bean.ProjTask ProjTask} objects.
     * @throws ProjectException
     */
    public List<ProjTask> findAllTasks() throws ProjectException {
	ProjTask obj = SetupFactory.createTask();
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves tasks based on their billable status.  This method offers the 
     * flexibility to obtain those tasks that are billable or non-billable.
     * 
     * @param billable 
     *          true means to get all billable tasks and false means to get 
     *          non-billable tasks.
     * @return ArrayList of unknown objects.
     * @throws ProjectException
     */
    public List<ProjTask> findTasks(boolean billable) throws ProjectException {
	ProjTask obj = SetupFactory.createTask();
	obj.addCriteria(ProjTask.PROP_BILLABLE, billable ? 1 : 0);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Creates a new or modifies an exisiting project using proj and client. The 
     * changes are persisted tot he database.
     * 
     * @param proj 
     *          Object containing project specific data that is to be persisted 
     *          in the database.
     * @param client 
     *          Object containing client data that is to be persisted in the database.
     * @return The id of the project persisted.
     * @throws ProjectException
     */
    public int maintainProject(ProjProject proj, ProjClient client) throws ProjectException {
	int projId = 0;
	int clientId = 0;

	if (proj == null) {
	    throw new ProjectException("Project object must be valid");
	}
	if (client == null) {
	    throw new ProjectException("Client object must be valid");
	}

	// Begin to process client data.
	clientId = this.maintainClient(client);
	proj.setClientId(clientId);
	if (clientId == 0) {
	    proj.setNull("ProjClientId");
	}

	// Begin to process project data.
	if (proj.getProjId() == 0) {
	    projId = this.insertProject(proj);
	}
	else if (proj.getProjId() > 0) {
	    projId = this.updateProject(proj);
	}
	return projId;
    }

    /**
     * Basically retrieves the client from local system.  If the client does not exist, it is 
     * obtained from the Contacts system, added to the Proj_Client table, and returned to the 
     * caller.   Implementation should check to see if client already exist, and if true, bypass 
     * any database updates.  Otherwise, add the client as intended.
     * 
     * @param client 
     *           The client object.
     * @return Returns 1 for success and -1 for failure.
     * @throws ProjectException 
     *           Remote client data is unobtainable or general database access error.
     */
    public int maintainClient(ProjClient client) throws ProjectException {
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;
	ProjClient cust = null;

	// Bypass adding client if null
	if (client == null) {
	    return 0;
	}

	// Bypass adding client if already exist in local system.
	cust = this.findClient(client.getClientId());
	if (cust != null) {
	    return client.getClientId();
	}

	// Get extension data for client, which possibly lives in the contact application, in order to add to local system.
	ProjClient remoteData = null;
	try {
	    remoteData = this.findClientExt(client.getClientId());
	}
	catch (NotFoundException e) {
	    throw new ProjectException(e);
	}
	if (remoteData == null) {
	    this.msg = "Client update failed due to remote data could not be obtained";
	    logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}

	// Copy remote data.
	cust = SetupFactory.createClient();
	cust.setBusinessId(remoteData.getBusinessId());
	cust.setName(remoteData.getName());
	cust.setAccountNo(remoteData.getAccountNo());
	cust.setContactEmail(remoteData.getContactEmail());
	cust.setContactExt(remoteData.getContactExt());
	cust.setContactFirstname(remoteData.getContactFirstname());
	cust.setContactLastname(remoteData.getContactLastname());
	cust.setContactPhone(remoteData.getContactPhone());

	// Client does not exist, so add.
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    cust.setClientId(client.getClientId());
	    cust.setDateCreated(ut.getDateCreated());
	    cust.setDateUpdated(ut.getDateCreated());
	    cust.setUserId(ut.getLoginId());
	    dso.insertRow(cust, false);
	    return cust.getClientId();
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Creates a new or modifies an exisiting task.
     * 
     * @param task The task object that is to be persisted in the database.
     * @return The id of the task persisted.
     * @throws ProjectException
     */
    public int maintainTask(ProjTask task) throws ProjectException {
	int taskId = 0;
	if (task == null) {
	    throw new ProjectException("Task object must be valid");
	}

	// Begin to process task data.
	if (task.getTaskId() == 0) {
	    taskId = this.insertTask(task);
	    task.setTaskId(taskId);
	}
	else if (task.getTaskId() > 0) {
	    taskId = this.updateTask(task);
	}
	return taskId;
    }

    /**
     * Add project to the database.
     * 
     * @param proj The project object.
     * @return The new project id.
     * @throws ProjectException when a validation or database access error occurs.
     */
    protected int insertProject(ProjProject proj) throws ProjectException {
	int projId = 0;
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	this.validateProject(proj);

	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    proj.setDateCreated(ut.getDateCreated());
	    proj.setDateUpdated(ut.getDateCreated());
	    proj.setUserId(ut.getLoginId());
	    projId = dso.insertRow(proj, true);
	    proj.setProjId(projId);
	    return projId;
	}
	catch (DatabaseException e) {
	    System.out.println(" DatabaseException - " + e.getMessage());
	    throw new ProjectException(e);
	}
	catch (SystemException e) {
	    System.out.println(" SystemException - " + e.getMessage());
	    throw new ProjectException(e);
	}
    }

    /**
     * Applies project modifications to the database.
     * 
     * @param proj The modified project data object.
     * @return Project Id
     * @throws ProjectException when a validation or database access error occurs.
     */
    protected int updateProject(ProjProject proj) throws ProjectException {
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	this.validateProject(proj);

	SetupApi api = SetupFactory.createApi(this.connector);
	try {
	    ProjProject oldProj = (ProjProject) api.findProject(proj.getProjId());
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    proj.setDateCreated(oldProj.getDateCreated());
	    proj.setDateUpdated(ut.getDateCreated());
	    proj.setUserId(ut.getLoginId());
	    proj.addCriteria(ProjProject.PROP_PROJID, proj.getProjId());
	    dso.updateRow(proj);
	    return proj.getProjId();
	}
	catch (DatabaseException e) {
	    System.out.println(" DatabaseException - " + e.getMessage());
	    throw new ProjectException(e);
	}
	catch (SystemException e) {
	    System.out.println(" SystemException - " + e.getMessage());
	    throw new ProjectException(e);
	}
	finally {
	    //  Do not close api since the assoicated connection belongs to the caller.
	    api = null;
	}
    }

    /**
     * Adds a task to the database.
     * 
     * @param task Object contain task data.
     * @return The new task id.
     * @throws ProjectException When a task validation error occurs or database access error.
     */
    protected int insertTask(ProjTask task) throws ProjectException {
	int taskId = 0;
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	this.validateTask(task);

	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    task.setDateCreated(ut.getDateCreated());
	    task.setDateUpdated(ut.getDateCreated());
	    task.setUserId(ut.getLoginId());
	    taskId = dso.insertRow(task, true);
	    return taskId;
	}
	catch (DatabaseException e) {
	    System.out.println(" DatabaseException - " + e.getMessage());
	    throw new ProjectException(e);
	}
	catch (SystemException e) {
	    System.out.println(" SystemException - " + e.getMessage());
	    throw new ProjectException(e);
	}
    }

    /**
     * Applies any modifications to the database regarding a task. 
     *  
     * @param task Object containing task data.
     * @return The id of the task.
     * @throws ProjectException When a task validation error occurs or database access error.
     */
    protected int updateTask(ProjTask task) throws ProjectException {
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	this.validateTask(task);

	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    task.setDateCreated(ut.getDateCreated());
	    task.setDateUpdated(ut.getDateCreated());
	    task.setUserId(ut.getLoginId());
	    task.addCriteria("TaskId", task.getTaskId());
	    dso.updateRow(task);
	    return task.getTaskId();
	}
	catch (DatabaseException e) {
	    System.out.println(" DatabaseException - " + e.getMessage());
	    throw new ProjectException(e);
	}
	catch (SystemException e) {
	    System.out.println(" SystemException - " + e.getMessage());
	    throw new ProjectException(e);
	}
    }

    /**
     * Validates a project object.
     * 
     * @param proj The project object that is being validated.
     * @throws ProjectException If data values for description and/or effective date is not provided.
     */
    private void validateProject(ProjProject proj) throws ProjectException {
	if (proj.getDescription() == null) {
	    throw new ProjectException("Project description is required");
	}

	if (proj.getEffectiveDate() == null) {
	    throw new ProjectException("Project effective date is required");
	}
    }

    /**
     * Validates a Task object.
     * 
     * @param task The task object that is being validated.
     * @throws ProjectException If data values are not provided for description.
     */
    private void validateTask(ProjTask task) throws ProjectException {
	if (task.getDescription() == null) {
	    throw new ProjectException("Task Description is required");
	}
    }

    /**
     * Deletes a single client from the database identified by its client id.
     * 
     * @param clientId The id of the client.
     * @return The number of rows effected from the transaction
     * @throws ProjectException 
     *           If the client is referenced in other areas of the database 
     *           as a foreign key and if there is a database access error.
     */
    public int deleteClient(int clientId) throws ProjectException, DatabaseException {
	int rc = 0;

	// Verify that client is not tied to any projects
	ProjProject proj = SetupFactory.createProject();
	proj.addCriteria(ProjProject.PROP_CLIENTID, clientId);
	List<ProjProject> list = null;
	try {
	    list = this.daoHelper.retrieveList(proj);
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    throw new ProjectException(e.getMessage());
	}
	if (list != null && list.size() > 0) {
	    this.msg = "Delete Failure: Client " + clientId + " is linked to one or more projects";
	    logger.log(Level.DEBUG, this.msg);
	    throw new ProjectException(this.msg);
	}

	// It is okay to delete client
	ProjClient client = SetupFactory.createClient();
	client.setClientId(clientId);
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	client.addCriteria("id", client.getClientId());
	rc = dso.deleteRow(client);
	return rc;
    }

    /**
     * Deletes a project from the database.
     * 
     * @param projectId The id of the project.
     * @return The number of rows effected fromt the transaction
     * @throws ProjectException 
     *           If the project is referenced in other areas of the database as a 
     *           foreign key and if there is a database access error.
     */
    public int deleteProject(int projectId) throws ProjectException, DatabaseException {
	int rc = 0;

	// Verify that client is not tied to any projects
	ProjProjectTask ppt = SetupFactory.createProjectTask();
	ppt.addCriteria(ProjProjectTask.PROP_PROJID, projectId);
	List<ProjProjectTask> list = null;
	try {
	    list = this.daoHelper.retrieveList(ppt);
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    throw new ProjectException(e.getMessage());
	}
	if (list != null && list.size() > 0) {
	    this.msg = "Delete Failure: Project " + projectId + " is linked to one or more tasks and/or timesheets";
	    logger.log(Level.DEBUG, this.msg);
	    throw new ProjectException(this.msg);
	}

	// It is okay to delete client
	ProjProject project = SetupFactory.createProject();
	project.setProjId(projectId);
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	project.addCriteria(ProjProject.PROP_PROJID, project.getProjId());
	rc = dso.deleteRow(project);
	return rc;
    }

    /**
     * Delete a task from the database.
     * 
     * @param taskId The id of the task.
     * @return The number of rows effected fromt the transaction
     * @throws ProjectException 
     *           If the task is referenced in other areas of the database as a 
     *           foreign key and if there is a database access error.
     */
    public int deleteTask(int taskId) throws ProjectException, DatabaseException {
	int rc = 0;

	// Verify that task is not tied to any projects
	ProjProjectTask ppt = SetupFactory.createProjectTask();
	ppt.addCriteria(ProjProjectTask.PROP_TASKID, taskId);
	List<ProjProjectTask> list = null;
	try {
	    list = this.daoHelper.retrieveList(ppt);
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    throw new ProjectException(e.getMessage());
	}
	if (list != null && list.size() > 0) {
	    this.msg = "Delete Failure: Task " + taskId + " is linked to one or more projects and/or timesheets";
	    logger.log(Level.DEBUG, this.msg);
	    throw new ProjectException(this.msg);
	}

	// It is okay to delete client
	ProjTask task = SetupFactory.createTask();
	task.setTaskId(taskId);
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	task.addCriteria("TaskId", task.getTaskId());
	rc = dso.deleteRow(task);
	return rc;
    }

}
