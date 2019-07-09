package com.project;




/**
 Provides an interface for querying data related to projects.
 *    
 * @author appdev
 * @deprecated No longer needed. 
 *
 */
public interface ProjectQueryApi {

   
   /**
    * 
    * @return
    * @throws ProjectException
    */
   Object getEventStatus() throws ProjectException;
   
   /**
    * 
    * @param value
    * @return
    * @throws ProjectException
    */
   Object findClient(int value) throws ProjectException;
   
   /**
    * 
    * @param value
    * @return
    * @throws ProjectException
    */
   Object findClientByBusinessId(int value) throws ProjectException;
   
   /**
    * 
    * @param value
    * @return
    * @throws ProjectException
    */
   Object findProject(int value) throws ProjectException;
   
   /**
    * 
    * @param _criteria
    * @return
    * @throws ProjectException
    */
   Object findProject(String _criteria) throws ProjectException;
   
   /**
    * 
    * @param value
    * @return
    * @throws ProjectException
    */
   Object findProjectByClientId(int value) throws ProjectException;
   
   /**
    * 
    * @param flag
    * @return
    * @throws ProjectException
    */
   Object findRoleByBillable(int flag) throws ProjectException;
   
   /**
    * 
    * @param value
    * @return
    * @throws ProjectException
    */
   Object findEvent(int value) throws ProjectException;
   
   /**
    * 
    * @param _criteria
    * @return
    * @throws ProjectException
    */
   Object findEvent(String _criteria) throws ProjectException;
   
   /**
    * 
    * @param value
    * @return
    * @throws ProjectException
    */
   Object findEventDetailsByEventId(int value) throws ProjectException;
   
   /**
    * 
    * @param _criteria
    * @return
    * @throws ProjectException
    */   
   Object findEventDetails(String _criteria) throws ProjectException;

 
}