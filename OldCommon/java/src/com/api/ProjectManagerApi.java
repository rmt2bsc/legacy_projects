package com.api;

import java.util.List;

import com.util.ProjectException;

import com.bean.ProjEmployee;
import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjEvent;


/**
 * 
 * @author appdev
 *
 */
public interface ProjectManagerApi  extends BaseDataSource {
   //  TODO: ensure that this method is addressed in com.action.UserMaintAction.java
   ProjEmployee findEmp(int value) throws ProjectException;
   
   List findEmp(String _criteria) throws ProjectException;
   ProjEmployee findEmpByPersonId(int value) throws ProjectException;
   List findEmpByTitle(int value) throws ProjectException;
   List getEmpTitles() throws ProjectException;;
   List getEmpTypes() throws ProjectException;
   List getEventStatus() throws ProjectException;
   ProjClient findClient(int value) throws ProjectException;
   ProjClient findClientByBusinessId(int value) throws ProjectException;
   ProjProject findProject(int value) throws ProjectException;
   List findProject(String _criteria) throws ProjectException;
   List findProjectByClientId(int value) throws ProjectException;
   List findRoleByBillable(int flag) throws ProjectException;
   ProjEvent findEvent(int value) throws ProjectException;
   List findEvent(String _criteria) throws ProjectException;
   List findEventDetailsByEventId(int value) throws ProjectException;
   List findEventDetails(String _criteria) throws ProjectException;

// TODO: ensure that this method is addressed in com.action.UserMaintAction.java
   int maintainEmployee(ProjEmployee _emp) throws ProjectException;
   int maintainClient(ProjClient _client) throws ProjectException;
   int maintainProject(ProjProject _proj) throws ProjectException;
   int maintainEvent(ProjEvent _event) throws ProjectException;
   List getBillRate(int _empId, int _clientId, int _projId) throws ProjectException;
   double calcHours(int _enventId) throws ProjectException;
   double calcHours(int _emp, int _year, int _month) throws ProjectException;
   int submit(int _eventId) throws ProjectException;
   int approve(int _eventId) throws ProjectException;
   int reject(int _eventId) throws ProjectException;

}