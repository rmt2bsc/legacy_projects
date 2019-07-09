package com.apiimpl;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.api.ProjectSetupApi;
import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjTask;
import com.bean.VwClientExt;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.factory.ProjectManagerFactory;

import com.util.ProjectException;

import com.util.SystemException;
import com.util.RMT2Utility;

/**
 * Api Implementation that manages the setup and maintenance of 
 * clients, projects, and tasks.
 * 
 * @author appdev
 *
 */
public class ProjectSetupImpl extends RdbmsDataSourceImpl implements ProjectSetupApi  {
	private String criteria;
	private Logger logger;

   /**
    * Default Constructor
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
   protected ProjectSetupImpl() throws DatabaseException, SystemException   {
	   super();   
	   logger = Logger.getLogger(ProjectSetupImpl.class);
   }

   /**
    * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
    * 
    * @param dbConn
    * @throws DatabaseException
    * @throws SystemException
    */
    public ProjectSetupImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
    	super(dbConn);
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws DatabaseException
     * @throws SystemException
     */
    public ProjectSetupImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
        super(dbConn);
        this.setRequest(_request);
    }
    
 

    public ProjClient findClient(int _clientId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjClient");
    	this.setBaseView("ProjClientView");
    	this.criteria = "id  = " + _clientId;
    	try {
    		List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (ProjClient) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
			throw new ProjectException(this.connector, 1000, this.msgArgs);
		}
	}
    
    public List findClient() throws ProjectException {
        this.setBaseClass("com.bean.VwCustomerName");
        this.setBaseView("VwCustomerNameView");
        try {
            List list = this.find(null, " name");
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }

    
    public VwClientExt findClientExt(int _clientId) throws ProjectException {
    	this.setBaseClass("com.bean.VwClientExt");
    	this.setBaseView("VwClientExtView");
    	this.criteria = "client_id  = " + _clientId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (VwClientExt) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
			throw new ProjectException(this.connector, 1000, this.msgArgs);
		}
	}

    
    
    public VwClientExt findClientByPerBusId(int _perbusId) throws ProjectException {
        this.setBaseClass("com.bean.VwClientExt");
        this.setBaseView("VwClientExtView");
        this.criteria = "perbus_id  = " + _perbusId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (VwClientExt) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    public List findProjects()  throws ProjectException {
        this.setBaseClass("com.bean.ProjProject");
        this.setBaseView("ProjProjectView");
        this.criteria = null;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    public ProjProject findProject(int _projectId) throws ProjectException {
        this.setBaseClass("com.bean.ProjProject");
        this.setBaseView("ProjProjectView");
        this.criteria = "id  = " + _projectId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (ProjProject) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    public List findProjectByClientId(int _clientId) throws ProjectException {
        this.setBaseClass("com.bean.ProjProject");
        this.setBaseView("ProjProjectView");
        this.criteria = "proj_client_id  = " + _clientId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    public ProjTask findTask(int _taskId) throws ProjectException {
        this.setBaseClass("com.bean.ProjTask");
        this.setBaseView("ProjTaskView");
        this.criteria = "id  = " + _taskId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (ProjTask) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    public List findTasks() throws ProjectException {
        this.setBaseClass("com.bean.ProjTask");
        this.setBaseView("ProjTaskView");
        this.criteria = null;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    public List findTasks(boolean _billable) throws ProjectException {
        this.setBaseClass("com.bean.ProjTask");
        this.setBaseView("ProjTaskView");
        this.criteria = " billable = " + (_billable ? 1 : 0);
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    
    public int maintainProject(ProjProject _proj, ProjClient _client) throws ProjectException {
        int projId = 0;
        int clientId = 0;
        
        if (_proj == null) {
        	throw new ProjectException("Project object must be valid");
        }
        if (_client == null) {
        	throw new ProjectException("Client object must be valid");
        }
        
        // Begin to process client data.
        clientId = this.maintainClient(_client);
        _proj.setProjClientId(clientId);
        if (clientId == 0) {
        	_proj.setNull("ProjClientId");
        }
        
        // Begin to process project data.
        if (_proj.getId() == 0) {
        	projId = this.insertProject(_proj);
        }
        if (_proj.getId() > 0) {
        	projId = this.updateProject(_proj);
        }
        return projId;
    }
    
    public int maintainClient(ProjClient _client) throws ProjectException {
        String method = "maintainClient";
        DaoApi dso = DataSourceFactory.createDao(this.connector);
        UserTimestamp ut = null;
        List list = null;
        ProjClient client = null;
        
        // Bypass adding client if null
        if (_client == null) {
        	return 0;
        }
        
        // Bypass adding client if already exist.
        this.setBaseClass("com.bean.ProjClient");
        this.setBaseView("ProjClientView");
        try {
        	list = this.findData(" id = " + _client.getId(), null);	
        }
        catch (SystemException e) {
        	throw new ProjectException(e);
        }
        
        if (list.size() > 0) {
            client = (ProjClient) list.get(0);
            return client.getId();
        }
        
        // Client does not exist, so add.
        try {
            ut = RMT2Utility.getUserTimeStamp(this.request);
            client = ProjectManagerFactory.createClient();
            client.setId(_client.getId());
            client.setDateCreated(ut.getDateCreated());
            client.setDateUpdated(ut.getDateCreated());
            client.setUserId(ut.getLoginId());
            dso.insertRow(client, false);
            return client.getId();
        }
        catch (DatabaseException e) {
            System.out.println(method + " DatabaseException - " + e.getMessage());
            throw new ProjectException(e);
        }
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new ProjectException(e);
        }
    }
    
    
    public int maintainTask(ProjTask _task) throws ProjectException {
        int taskId = 0;
        if (_task == null) {
        	throw new ProjectException("Task object must be valid");
        }
        
        // Begin to process task data.
        if (_task.getId() == 0) {
        	taskId = this.insertTask(_task);
        }
        if (_task.getId() > 0) {
        	taskId = this.updateTask(_task);
        }
        return taskId;
    }
    
    
    /**
     * Add project to the database.
     * 
     * @param _proj The project object.
     * @return The new project id.
     * @throws ProjectException when a validation or database access error occurs.
     */
    protected int insertProject(ProjProject _proj) throws ProjectException {
    	int projId = 0;
    	DaoApi dso = DataSourceFactory.createDao(this.connector);
        UserTimestamp ut = null;
    	
        this.validateProject(_proj);
        
        try {
            ut = RMT2Utility.getUserTimeStamp(this.request);
            _proj.setDateCreated(ut.getDateCreated());
            _proj.setDateUpdated(ut.getDateCreated());
            _proj.setUserId(ut.getLoginId());
            projId = dso.insertRow(_proj, true);
            return projId;
        }
        catch (DatabaseException e) {
            System.out.println(" DatabaseException - " + e.getMessage());
            throw new ProjectException(e);
        }
        catch (SystemException e) {
            System.out.println( " SystemException - " + e.getMessage());
            throw new ProjectException(e);
        }
    }
    
    
    /**
     * Applies project modifications to the database.
     * 
     * @param _proj The modified project data object.
     * @return Project Id
     * @throws ProjectException when a validation or database access error occurs.
     */
    protected int updateProject(ProjProject _proj) throws ProjectException {
    	DaoApi dso = DataSourceFactory.createDao(this.connector);
        UserTimestamp ut = null;
    	
        this.validateProject(_proj);
        
        try {
            ut = RMT2Utility.getUserTimeStamp(this.request);
            _proj.setDateCreated(ut.getDateCreated());
            _proj.setDateUpdated(ut.getDateCreated());
            _proj.setUserId(ut.getLoginId());
            _proj.addCriteria("Id", _proj.getId());
            dso.updateRow(_proj);
            return _proj.getId();
        }
        catch (DatabaseException e) {
            System.out.println(" DatabaseException - " + e.getMessage());
            throw new ProjectException(e);
        }
        catch (SystemException e) {
            System.out.println( " SystemException - " + e.getMessage());
            throw new ProjectException(e);
        }
    }
    

    /**
     * Adds a task to the database.
     * 
     * @param _task Object contain task data.
     * @return The new task id.
     * @throws ProjectException When a task validation error occurs or database access error.
     */
    protected int insertTask(ProjTask _task) throws ProjectException {
    	int taskId = 0;
    	DaoApi dso = DataSourceFactory.createDao(this.connector);
        UserTimestamp ut = null;
    	
        this.validateTask(_task);
        
        try {
            ut = RMT2Utility.getUserTimeStamp(this.request);
            _task.setDateCreated(ut.getDateCreated());
            _task.setDateUpdated(ut.getDateCreated());
            _task.setUserId(ut.getLoginId());
            taskId = dso.insertRow(_task, true);
            return taskId;
        }
        catch (DatabaseException e) {
            System.out.println(" DatabaseException - " + e.getMessage());
            throw new ProjectException(e);
        }
        catch (SystemException e) {
            System.out.println( " SystemException - " + e.getMessage());
            throw new ProjectException(e);
        }
    }
    
    /**
     * Applies any modifications to the database regarding a task. 
     *  
     * @param _task Object containing task data.
     * @return The id of the task.
     * @throws ProjectException When a task validation error occurs or database access error.
     */
    protected int updateTask(ProjTask _task) throws ProjectException {
    	DaoApi dso = DataSourceFactory.createDao(this.connector);
        UserTimestamp ut = null;
    	
        this.validateTask(_task);
        
        try {
            ut = RMT2Utility.getUserTimeStamp(this.request);
            _task.setDateCreated(ut.getDateCreated());
            _task.setDateUpdated(ut.getDateCreated());
            _task.setUserId(ut.getLoginId());
            _task.addCriteria("Id", _task.getId());
            dso.updateRow(_task);
            return _task.getId();
        }
        catch (DatabaseException e) {
            System.out.println(" DatabaseException - " + e.getMessage());
            throw new ProjectException(e);
        }
        catch (SystemException e) {
            System.out.println( " SystemException - " + e.getMessage());
            throw new ProjectException(e);
        }
    }

    /**
     * Validates a project object.
     * 
     * @param _proj The project object that is being validated.
     * @throws ProjectException If data values for description and/or effective date is not provided.
     */
    private void validateProject(ProjProject _proj) throws ProjectException {
    	if (_proj.getDescription() == null) {
    		throw new ProjectException("Project description is required");
    	}
    	
    	if (_proj.getEffectiveDate() == null) {
    		throw new ProjectException("Project effective date is required");
    	}
    }
    
    /**
     * Validates a Task object.
     * 
     * @param _task The task object that is being validated.
     * @throws ProjectException If data values are not provided for description.
     */
    private void validateTask(ProjTask _task) throws ProjectException {
    	if (_task.getDescription() == null) {
    		throw new ProjectException("Task Description is required");
    	}
    }
    
    public int deleteClient(int _clientId) throws ProjectException, DatabaseException  {
        List list = null;
    	int rc = 0;
    	
    	// Verify that client is not tied to any projects
    	this.setBaseClass("com.bean.ProjProject");
    	this.setBaseView("ProjProjectView");
    	this.criteria = " proj_client_id = " + _clientId;
    	try {
    		list = this.findData(this.criteria, null);	
    	}
    	catch (SystemException e) {
    		logger.log(Level.DEBUG, e.getMessage());
    		throw new ProjectException(e.getMessage());
    	}
    	if (list.size() > 0) {
    		this.msg = "Delete Failure: Client " + _clientId + " is linked to one or more projects";
    		logger.log(Level.DEBUG, this.msg);
    		throw new ProjectException(this.msg);
    	}

    	// It is okay to delete client
    	ProjClient client = ProjectManagerFactory.createClient();
    	client.setId(_clientId);
    	DaoApi dso = DataSourceFactory.createDao(this.connector);
    	client.addCriteria("id", client.getId());
   		rc = dso.deleteRow(client);
   		return rc;
    }
    
    public int deleteProject(int _projectId) throws ProjectException, DatabaseException  {
        List list = null;
    	int rc = 0;
    	
    	// Verify that client is not tied to any projects
    	this.setBaseClass("com.bean.ProjProjectTask");
    	this.setBaseView("ProjProjectTaskView");
    	this.criteria = " proj_project_id = " + _projectId;
    	try {
    		list = this.findData(this.criteria, null);	
    	}
    	catch (SystemException e) {
    		logger.log(Level.DEBUG, e.getMessage());
    		throw new ProjectException(e.getMessage());
    	}
    	if (list.size() > 0) {
    		this.msg = "Delete Failure: Project " + _projectId + " is linked to one or more timesheets";
    		logger.log(Level.DEBUG, this.msg);
    		throw new ProjectException(this.msg);
    	}

    	// It is okay to delete client
    	ProjProject project = ProjectManagerFactory.createProject();
    	project.setId(_projectId);
    	DaoApi dso = DataSourceFactory.createDao(this.connector);
    	project.addCriteria("Id", project.getId());
   		rc = dso.deleteRow(project);
   		return rc;
    }
    
    public int deleteTask(int _taskId) throws ProjectException, DatabaseException  {
        List list = null;
    	int rc = 0;
    	
    	// Verify that client is not tied to any projects
    	this.setBaseClass("com.bean.ProjProjectTask");
    	this.setBaseView("ProjProjectTaskView");
    	this.criteria = " proj_task_id = " + _taskId;
    	try {
    		list = this.findData(this.criteria, null);	
    	}
    	catch (SystemException e) {
    		logger.log(Level.DEBUG, e.getMessage());
    		throw new ProjectException(e.getMessage());
    	}
    	if (list.size() > 0) {
    		this.msg = "Delete Failure: Task " + _taskId + " is linked to one or more timesheets";
    		logger.log(Level.DEBUG, this.msg);
    		throw new ProjectException(this.msg);
    	}

    	// It is okay to delete client
    	ProjTask task = ProjectManagerFactory.createTask();
    	task.setId(_taskId);
        DaoApi dso = DataSourceFactory.createDao(this.connector);
        task.addCriteria("Id", task.getId());
   		rc = dso.deleteRow(task);
   		return rc;
    }
    
}
