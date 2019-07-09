package com.project;

import java.util.List;

import com.bean.ProjClient;
import com.bean.ProjEvent;
import com.bean.ProjProject;




/**
 * Provides an interface for creating, updating, and deleting project related entities.
 *    
 * @author appdev
 * @deprecated No longer needed.  
 *
 */
public interface ProjectMaintenanceApi {

   
    /**
     * 
     * @param _client
     * @return
     * @throws ProjectException
     * @deprecated This functionality can be found in the setup package
     */
    int maintainClient(ProjClient _client) throws ProjectException;
    
    /**
     * 
     * @param _proj
     * @return
     * @throws ProjectException
     * @deprecated This functionality can be found in the setup package
     */
    int maintainProject(ProjProject _proj) throws ProjectException;
    
    /**
     * 
     * @param _event
     * @return
     * @throws ProjectException
     */
    int maintainEvent(ProjEvent _event) throws ProjectException;
    
    /**
     * 
     * @param _empId
     * @param _clientId
     * @param _projId
     * @return
     * @throws ProjectException
     */
    List getBillRate(int _empId, int _clientId, int _projId) throws ProjectException;
    
    /**
     * 
     * @param _enventId
     * @return
     * @throws ProjectException
     */
    double calcHours(int _enventId) throws ProjectException;
    
    /**
     * 
     * @param _emp
     * @param _year
     * @param _month
     * @return
     * @throws ProjectException
     */
    double calcHours(int _emp, int _year, int _month) throws ProjectException;
    
    /**
     * 
     * @param _eventId
     * @return
     * @throws ProjectException
     */
    int submit(int _eventId) throws ProjectException;
    
    /**
     * 
     * @param _eventId
     * @return
     * @throws ProjectException
     */
    int approve(int _eventId) throws ProjectException;
    
    /**
     * 
     * @param _eventId
     * @return
     * @throws ProjectException
     */
    int reject(int _eventId) throws ProjectException;
}