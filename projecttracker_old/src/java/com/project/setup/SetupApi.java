package com.project.setup;


import com.api.BaseDataSource;
import com.api.db.DatabaseException;

import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjTask;

import com.project.ProjectException;
import com.util.NotFoundException;


/**
 * An interface that provides methods for querying and maintaining project, task, 
 * and client data entities. 
 * 
 * @author appdev
 *
 */
public interface SetupApi extends BaseDataSource {

   /**
    * Obtains a list of all clients
    * 
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findAllClients() throws ProjectException;
   
   /**
    * Finds a client using client id.
    * 
    * @param clientId The client's id
    * @return An arbitrary object.
    * @throws ProjectException
    */
   Object findClient(int clientId) throws ProjectException;
   
   /**
    * Find a single client with extended data using client id.
    * 
    * @param clientId The client's id
    * @return An arbitrary object.
    * @throws ProjectException
    *          when two or more clients are returned as a result of <i>clientId</i> not being unique.
    * @throws NotFoundException
    *          when the client is not found.         
    */
   Object findClientExt(int clientId) throws ProjectException;
   
   /**
    * Find all clients with extended data.
    * @return
    * @throws ProjectException
    */
   Object findClientExt() throws ProjectException;
   
    
   /**
    * Find all projects.
    * 
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findAllProjects()  throws ProjectException;
   
   
   /**
    * Finds a project using projectId.
    * 
    * @param projectId The id of a project.
    * @return An arbitrary object.
    * @throws ProjectException
    */
   Object findProject(int projectId) throws ProjectException;

   /**
    * Find one or more projects using the clinet's id.
    * 
    * @param clientId The id of the client.
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findProjectByClientId(int clientId) throws ProjectException;
   
   /**
    * Finds a tasks using _taskId.
    * 
    * @param taskId  The Id of the task to locate.
    * @return {@link ProjTask}
    * @throws ProjectException
    */
   Object findTask(int taskId) throws ProjectException;
   
   /**
    * Returns a list of all tasks.
    * 
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findAllTasks() throws ProjectException;
   
   /**
    * Find tasks based on the _billable flag.
    * 
    * @param billable 
    *          A boolean value which indicates to target billable or non-billable 
    *          tasks.
    * @return ArrayList of unknown objects.
    * @throws ProjectException
    */
   Object findTasks(boolean billable) throws ProjectException;

   /**
    * Maintains a client.
    * 
    * @param client The client object.
    * @return int
    * @throws ProjectException
    */
   int maintainClient(ProjClient client) throws ProjectException;
   
   /**
    * Maintains a project and the client it belongs.
    * 
    * @param proj The project to maintain.
    * @param client The client of the project to maintain.
    * @return int
    * @throws ProjectException
    */
   int maintainProject(ProjProject proj, ProjClient client) throws ProjectException;
   
   /**
    * Maintains a project task.
    * 
    * @param task Object containing task data.
    * @return The id of the task.
    * @throws ProjectException
    */
   int maintainTask(ProjTask task) throws ProjectException;
   
   /**
    * Deletes a client from the database.
    * 
    * @param clientId The id of the client.
    * @return int
    * @throws ProjectException 
    */
   int deleteClient(int clientId) throws ProjectException, DatabaseException;
   
   /**
    * Delete a project.
    * 
    * @param projectId The id of the project.
    * @return int
    * @throws ProjectException
    */
   int deleteProject(int projectId) throws ProjectException, DatabaseException;
   
   /**
    * Delete a task.
    * 
    * @param taskId The id of the task.
    * @return int
    * @throws ProjectException
    */
   int deleteTask(int taskId) throws ProjectException, DatabaseException;

}
