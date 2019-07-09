package com.api.codes;

import java.util.List;

import com.api.BaseDataSource;

import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;

/**
 * Defines the methods that are used to manage the interface for General Codes.
 * 
 * @author RTerrell
 *
 */
 public interface CodesApi extends BaseDataSource {
     
     /**
      * Fetches general code data based on one or more group id's as selection criteria.
      * The implementation should be flexible enough to return codes for all groups, a 
      * select number of groups, or for a single group.
      * 
      * @param groupId
      *          an array of group id's.   If <i>groupId</i> is null or size is equal to 
      *          zero, then codes for all grouops should be returned.   Otherwise, codes 
      *          for the groups specified are returned.
      * @return  a List of general code records which are grouped by their respective group 
      *          id's.  The implementor is responsible for determining the data type(s) the 
      *          List will manage.
      * @throws GeneralCodeException
      */
     List findLookupData(int groupId[]) throws GeneralCodeException;
     
    
    /**
     * Finds general code by using primary key.
     * 
     * @param id The primary key code type object.
     * @return An arbitary object.
     * @throws GeneralCodeException
     */
   Object findCodeById(int id) throws GeneralCodeException;
   
   /**
    * Finds all general codes by using general code group id.
    * 
    * @param groupId The group id.
    * @return An arbitrary object.
    * @throws GeneralCodeException
    */
   Object findCodeByGroup(int groupId) throws GeneralCodeException;
   
   /**
    * Finds General Code by Description.
    * 
    * @param descr The description used to search for the target object.
    * @return A List of arbitrary objects.
    * @throws GeneralCodeException
    */
   Object findCodeByDescription(String descr) throws GeneralCodeException;
   
   /**
    * Finds one or more general codes by using custom criteria supplied by the client.
    * 
    * @param criteria String representing selectio criteria.
    * @return A List of arbitrary objects.
    * @throws GeneralCodeException
    */
   Object findCode(String criteria) throws GeneralCodeException;
   
   /**
    * Creates or modifies Genreal Code profile and persist the changes to the database.
    * 
    * @param _obj The {@link GeneralCodes} object to maintain.
    * @return The primary key of the GeneralCodes object that was added or updated.
    * @throws GeneralCodeException
    */
   int maintainCode(GeneralCodes _code) throws GeneralCodeException;
   
   /**
    * Deletes a General Code fro the database using a stored procedure call.
    * 
    * @param obj The {@link GeneralCodes} object to delete from the database.
    * @return int The total number of rows effected.
    * @throws GeneralCodeException
    */
   int deleteCode(GeneralCodes _grp) throws GeneralCodeException;
   
   
   /**
    * Finds Genreal Code Group using primary key
    * 
    * @param id The primary key of the group to search.
    * @return An arbitrary object.
    * @throws GeneralCodeException
    */    
   Object findGroupById(int id) throws GeneralCodeException;
  
  /**
   * Finds General Code Group by Description
   * 
   * @param descr 
   *          The description used to search for one or more 
   *          {@link GeneralCodesGroup} objects.
   * @return An arbitrary Object. 
   * @throws GeneralCodeException
   */
   Object findGroupByDescription(String descr) throws GeneralCodeException;
  
  /**
   * Finds one or more general code groups by using custom criteria (criteria) 
   * supplied by the client.
   * 
   * @param criteria Custom selection criteria as a String.
   * @return An arbitrary object.
   * @throws GeneralCodeException
   */
   Object findGroup(String criteria) throws GeneralCodeException;
  
  /**
   * Creates or modifies Genereal Code Group profile and persist the changes to the database.
   * 
   * @param obj The {@link GeneralCodesGroup} to add or modify.
   * @return The primary key of the target object.
   * @throws GeneralCodeException
   */
  int maintainGroup(GeneralCodesGroup _grp) throws GeneralCodeException;
  
  /**
   * Deletes a General Code Group from the database.
   * 
   * @param obj The {@link GeneralCodesGroup} object to delete.
   * @return int The total number of rows effected.
   * @throws GeneralCodeException
   */
  int deleteGroup(GeneralCodesGroup _grp) throws GeneralCodeException;
}