package com.project;

import java.util.List;

import com.bean.ProjEvent;




/**
 * Provides an interface for maintaining the state of a project entity.
 *    
 * @author appdev
 * @deprecated No longer needed. 
 *
 */
public interface ProjectStateApi {

    /**
     * Creates an event pertaining to a project entity.
     * 
     * @param event Project event
     * @return int
     * @throws ProjectException
     */
    int maintainEvent(ProjEvent event) throws ProjectException;
    
    /**
     * Obtains the bill rate of a project entity.
     * 
     * @param empId The id of the employee
     * @param clientId The id of the client
     * @param projId The project id
     * @return One or more values pertaing to the bill rate
     * @throws ProjectException
     */
    Object getBillRate(int empId, int clientId, int projId) throws ProjectException;
    
    /**
     * Computes the total hours of an event of a project entity.
     * 
     * @param enventId The event id.
     * @return double
     * @throws ProjectException
     */
    double calcHours(int enventId) throws ProjectException;
    
    /**
     * Calculates the hours consumed by an employee of a project entity based 
     * on the specified month of the year.
     *   
     * @param emp the id of the employee
     * @param year The year 
     * @param month The month
     * @return double
     * @throws ProjectException
     */
    double calcHours(int emp, int year, int month) throws ProjectException;
    
    /**
     * Submits a project entity's event.
     * 
     * @param eventId The id of the event that is to be submitted.
     * @return int
     * @throws ProjectException
     */
    int submit(int eventId) throws ProjectException;
    
    /**
     * Approves a project entity's event.
     * 
     * @param eventId The id of the event that is to be approved.
     * @return int
     * @throws ProjectException
     */
    int approve(int eventId) throws ProjectException;
    
    /**
     * Rejects a project entity's event.
     * 
     * @param eventId The id of the event that is to be rejected.
     * @return int
     * @throws ProjectException
     */
    int reject(int eventId) throws ProjectException;
}