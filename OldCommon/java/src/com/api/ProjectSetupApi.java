package com.api;

import java.util.List;

import com.api.db.DatabaseException;
import com.bean.ProjClient;
import com.bean.VwClientExt;
import com.bean.ProjProject;
import com.bean.ProjTask;
import com.bean.VwCustomerName;

import com.util.ProjectException;

/**
 * An interface that provides methods for setting up the components of a project, maintaining relationships between the company, client, 
 * projects and task, and querying the database for the data components mention previously.
 * 
 * @author appdev
 *
 */
public interface ProjectSetupApi extends BaseDataSource {

   /**
    * Obtains a list of all clients
    * 
    * @return An ArrayList of {@link VwCustomerName} objects
    * @throws ProjectException
    */
   List findClient() throws ProjectException;
   /**
    * Finds a client ustin client id.
    * 
    * @param _clientId The client's id
    * @return {@link ProjClient}
    * @throws ProjectException
    */
   public ProjClient findClient(int _clientId) throws ProjectException;
   
   /**
    * Finds a client using client id.
    * 
    * @param _clientId The client's id
    * @return {@link VwClientExt}
    * @throws ProjectException
    */
   VwClientExt findClientExt(int _clientId) throws ProjectException;
   
   /**
    * finds a client using using the person_id or business_id, hence perbus_id.
    * 
    * @param _perbusId The business profile id or the personal profile id.
    * @return {@link VwClientExt}
    * @throws ProjectException
    */
   VwClientExt findClientByPerBusId(int _perbusId) throws ProjectException;
   
   /**
    * Retrieves all projects that exist in the system.
    * 
    * @return ArrayList {@link ProjProject} objects
    * @throws ProjectException
    */
   List findProjects()  throws ProjectException;
   
   
   /**
    * Finds a project using _projectId.
    * 
    * @param _projectId The id of a project.
    * @return {@link ProjProject}
    * @throws ProjectException
    */
   ProjProject findProject(int _projectId) throws ProjectException;

   /**
    * Find one or more projects using the clinet's id.
    * 
    * @param _clientId The id of the client.
    * @return ArrayList of unknown objects.
    * @throws ProjectException
    */
   List findProjectByClientId(int _clientId) throws ProjectException;
   
   /**
    * Finds a tasks using _taskId.
    * 
    * @param _taskId  The Id of the task to locate.
    * @return {@link ProjTask}
    * @throws ProjectException
    */
   ProjTask findTask(int _taskId) throws ProjectException;
   
   /**
    * Returns a list of all tasks.
    * 
    * @return ArrayList of unknown objects.
    * @throws ProjectException
    */
   List findTasks() throws ProjectException;
   
   /**
    * Retrieves tasks based on the _billable flag.
    * 
    * @param _billable true means to get all billable tasks and false means to get non-billable tasks.
    * @return ArrayList of unknown objects.
    * @throws ProjectException
    */
   List findTasks(boolean _billable) throws ProjectException;

   /**
    * Adds a client to the Proj_Client table.   Implementation should checkto see if client already exist, and if true, bypass 
    * any database updates.   Otherwise, add the client as intended.
    * 
    * @param _client The client object.
    * @return 1=success and -1=failure.
    * @throws ProjectException
    */
   int maintainClient(ProjClient _client) throws ProjectException;
   
   /**
    * Creates a new or modifies an exisiting project using _proj and _resources.
    * 
    * @param _proj Object containing project specific data that is to be persisted in the database.
    * @param _client Object containing client data that is to be persisted in the database.
    * @return The id of the project.
    * @throws ProjectException
    */
   int maintainProject(ProjProject _proj, ProjClient _client) throws ProjectException;
   
   /**
    * Creates a new or modifies an exisiting task using _task.
    * @param _task Object contain task data that is to be persisted in the database.
    * @return The id of the task.
    * @throws ProjectException
    */
   int maintainTask(ProjTask _task) throws ProjectException;
   
   /**
    * Deletes a client from the database.
    * 
    * @param _clientId The id of the client.
    * @return The number of rows effected fromt the transaction
    * @throws ProjectException If the client is referenced in other areas of the database as a foreign key and 
    * if there is a database access error.
    */
   int deleteClient(int _clientId) throws ProjectException, DatabaseException;
   
   /**
    * Deletes a project from the database.
    * 
    * @param _projectId The id of the project.
    * @return The number of rows effected fromt the transaction
    * @throws ProjectException If the project is referenced in other areas of the database as a foreign key and
    * if there is a database access error.
    */
   int deleteProject(int _projectId) throws ProjectException, DatabaseException;
   
   /**
    * Deletes a task from the database.
    * 
    * @param _taskId The id of the task.
    * @return The number of rows effected fromt the transaction
    * @throws ProjectException If the task is referenced in other areas of the database as a foreign key and
    * if there is a database access error.
    */
   int deleteTask(int _taskId) throws ProjectException, DatabaseException;

}
