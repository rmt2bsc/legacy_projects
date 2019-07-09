package com.api.codes;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.controller.Request;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;

import com.bean.OrmBean;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;
import com.bean.VwCodes;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * An implementation of the CodesApi interfaces which manages the retrieval, 
 * modification, and mapping of object data pertaining to general code entities.  
 * The source of the data is expected to be from relational database management 
 * system.
 * 
 * @author RTerrell
 * 
 */
class GeneralCodesImpl extends RdbmsDaoImpl implements CodesApi {
    private Logger logger;


    /**
     * Creates a GeneralCodesImpl object by initializing the
     * DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn {@link DatabaseConnectionBean}
     * @throws SystemException
     * @throws DatabaseException
     */
    public GeneralCodesImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
        super(dbConn);
        this.setBaseView("GeneralCodesGroupView");
        this.setBaseClass("com.bean.GeneralCodesGroup");
        logger = Logger.getLogger("GeneralCodesImpl");
    }

    /**
     * Creates a GeneralCodesImpl object by initializing the
     * DatabaseConnectionBean and a HttpServletRequest.
     * 
     * @param dbConn {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @throws SystemException
     * @throws DatabaseException
     */
    public GeneralCodesImpl(DatabaseConnectionBean dbConn, Request request) 
              throws SystemException, DatabaseException {
        this(dbConn);
        this.request = request;
    }

    
    /**
     * Helper method for retrieving a single general code or general code group 
     * object from the database.
     * 
     * @param obj The ORM object to retrieve data.
     * @return An Object instance of type {@link GeneralCodesGroup} or {@link GeneralCodes}.
     * @throws GeneralCodeException For general database errors.
     */
    private Object retrieveObject(OrmBean obj) throws GeneralCodeException {
        try {
            Object data[] = this.retrieve(obj);
            if (data != null && data.length > 0) {
                return data[0];
            }
            return null;
        }
        catch (DatabaseException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new GeneralCodeException(this.msg);
        }
    }
    
    
    /**
     * Helper method for retrieving a List of general code or general code group 
     * objects from the database.
     * 
     * @param obj The ORM object to retrieve data.
     * @return List of either {@link GeneralCodesGroup} or {@link GeneralCodes} objects.
     * @throws GeneralCodeException For general database errors.
     */
    private List retrieveList(OrmBean obj) throws GeneralCodeException {
        try {
            Object data[] = this.retrieve(obj);
            if (data != null && data.length > 0) {
                return java.util.Arrays.asList(data);
            }
            return null;
        }
        catch (DatabaseException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new GeneralCodeException(this.msg);
        }
    }
    
    /**
     * Finds Genreal Code Group using primary key
     * 
     * @param id The primary key of the group to search.
     * @return {@link GeneralCodesGroup}.
     * @throws GeneralCodeException
     */
    public GeneralCodesGroup findGroupById(int id) throws GeneralCodeException {
        GeneralCodesGroup group = CodesFactory.createGeneralGroup();
        group.addCriteria(GeneralCodesGroup.PROP_CODEGRPID, id);
        return (GeneralCodesGroup) this.retrieveObject(group);
    }

    /**
     * Finds General Code Group by Description
     * 
     * @param descr The description used to search for one or more {@link GeneralCodesGroup} objects.
     * @return A List of GeneralCodesGroup instances as an arbitrary Object.
     * @throws GeneralCodeException
     */
    public List findGroupByDescription(String descr) throws GeneralCodeException {
        GeneralCodesGroup group = CodesFactory.createGeneralGroup();
        group.addCriteria(GeneralCodesGroup.PROP_DESCRIPTION, descr);
        return this.retrieveList(group);
    }

    /**
     * Finds one or more general code groups by using custom criteria (criteria)
     * supplied by the client.
     * 
     * @param criteria SQL predicate String.
     * @return List of {@link GeneralCodesGroup} objects.
     * @throws GeneralCodeException
     */
    public List findGroup(String criteria) throws GeneralCodeException {
        GeneralCodesGroup group = CodesFactory.createGeneralGroup();
        group.addCustomCriteria(criteria);
        return this.retrieveList(group);
    }

    /**
     * Finds all general codes by using general code group id.  Result set is ordered by long description.
     * 
     * @param groupId The group id.
     * @return A List of {@link GeneralCodes}.
     * @throws GeneralCodeException
     */
    public List findCodeByGroup(int groupId) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createGeneralCodes();
        code.addCriteria(GeneralCodes.PROP_CODEGRPID, groupId);
        code.addOrderBy(GeneralCodes.PROP_LONGDESC, GeneralCodes.ORDERBY_ASCENDING);
        return this.retrieveList(code);
    }

    /**
     * Finds general code by using primary key.
     * 
     * @param id The primary key of the Genreal Code to search.
     * @return A {@link GeneralCodes} object.
     * @throws GeneralCodeException
     */
    public GeneralCodes findCodeById(int id) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createGeneralCodes();
        code.addCriteria(GeneralCodes.PROP_CODEID , id);
        return (GeneralCodes) this.retrieveObject(code);
    }

    /**
     * Finds General Code by Description.
     * 
     * @param descr The description used to search for a general code.
     * @return A List of {@link GeneralCodes} objects.
     * @throws GeneralCodeException
     */
    public List findCodeByDescription(String descr) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createGeneralCodes();
        code.addCriteria(GeneralCodes.PROP_LONGDESC, descr);
        return this.retrieveList(code);
    }

    /**
     * Finds one or more general codes by using custom SQL selection criteria
     * supplied by the client.
     * <p>
     * The resultset is order by long description
     * 
     * @param criteria String representing the SQL predicate.
     * @return A List of {@link GeneralCodes} objects.
     * @throws GeneralCodeException
     */
    public List findCode(String criteria) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createGeneralCodes();
        code.addCustomCriteria(criteria);
        code.addOrderBy(GeneralCodes.PROP_LONGDESC, GeneralCodes.ORDERBY_ASCENDING);
        return this.retrieveList(code);
    }

    /**
     * Creates or modifies Genreal Code Group profile and persist the changes to
     * the database.
     * 
     * @param obj
     *            The {@link GeneralCodesGroup} to add or modify.
     * @return The primary key of the target object.
     * @throws GeneralCodeException
     */
    public int maintainGroup(GeneralCodesGroup obj) throws GeneralCodeException {
        int key;

        if (obj == null) {
            throw new GeneralCodeException("General Codes Group object cannot be null");
        }
        if (obj.getDescription() == null || obj.getDescription().length() <= 0) {
            throw new GeneralCodeException("General Codes Group object must have a description");
        }

        if (obj.getCodeGrpId() == 0) {
            key = this.insertCodeGroup(obj);
        }
        else {
            key = this.updateCodeGroup(obj);
        }

        return key;
    }

    /**
     * Adds a General Code Group entry to the database.
     * 
     * @param obj The {@link GeneralCodesGroup} to add.
     * @return The primary key of the GeneralCodesGroup added.
     * @throws GeneralCodeException
     */
    protected int insertCodeGroup(GeneralCodesGroup obj) throws GeneralCodeException {
         try {
             UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
             obj.setDateCreated(ut.getDateCreated());
             obj.setDateUpdated(ut.getDateCreated());
             obj.setUserId(ut.getLoginId());
             int rc = this.insertRow(obj, true);
             obj.setCodeGrpId(rc);
             return obj.getCodeGrpId();
        }
        catch (Exception e) {
            throw new GeneralCodeException(e);
        }
    }

    /**
     * Updates a general code group and persist the changes to the database.
     * 
     * @param obj The GeneralCodesGroup object to update.
     * @return The total number of rows effected by transaction.
     * @throws GeneralCodeException
     */
    protected int updateCodeGroup(GeneralCodesGroup obj) throws GeneralCodeException {
        try {
            obj.addCriteria(GeneralCodesGroup.PROP_CODEGRPID, obj.getCodeGrpId());
            UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
            obj.setDateUpdated(ut.getDateCreated());
            obj.setUserId(ut.getLoginId());
            int rows = 0;
            rows = this.updateRow(obj);
            return rows;
        }
        catch (Exception e) {
            throw new GeneralCodeException(e);
        }
    }

    /**
     * Deletes a General Code Group from the database.
     * 
     * @param obj The {@link GeneralCodesGroup} object to delete.
     * @return int The total number of rows effected.           
     * @throws GeneralCodeException
     */
    public int deleteGroup(GeneralCodesGroup obj) throws GeneralCodeException {
        try {
            int rows = 0;
            obj.addCriteria(GeneralCodesGroup.PROP_CODEGRPID, obj.getCodeGrpId());
            rows = this.deleteRow(obj);
            return rows;
        }
        catch (DatabaseException e) {
            throw new GeneralCodeException(e);
        }
    }

    /**
     * Creates or modifies Genreal Code profile and persist the changes to the
     * database.
     * 
     * @param obj The {@link GeneralCodes} object to maintain.
     * @return The primary key of the GeneralCodes object that was added or
     *         updated.
     * @throws GeneralCodeException
     */
    public int maintainCode(GeneralCodes obj) throws GeneralCodeException {
        int key;

        if (obj == null) {
            throw new GeneralCodeException("General Code object is invalid");
        }
        if (obj.getCodeGrpId() <= 0) {
            throw new GeneralCodeException("General Code group id value is invalid");
        }
        if ((obj.getShortdesc() == null || obj.getShortdesc().length() <= 0)
           && (obj.getLongdesc() == null || obj.getLongdesc().length() <= 0)) {
            throw new GeneralCodeException("General Code object must have either a short or long descripton");
        }

        if (obj.getCodeId() == 0) {
            key = this.insertCode(obj);
        }
        else {
            key = this.updateCode(obj);
        }

        return key;
    }

    /**
     * Adds a General Code object to the database.
     * 
     * @param obj The target {@link GeneralCodes} object to add to the database.
     * @return The primary key of the object added.
     * @throws GeneralCodeException
     */
    protected int insertCode(GeneralCodes obj) throws GeneralCodeException {
        try {
            UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
            obj.setDateCreated(ut.getDateCreated());
            obj.setDateUpdated(ut.getDateCreated());
            obj.setUserId(ut.getLoginId());
            int rc = this.insertRow(obj, true);
            obj.setCodeId(rc);
            return obj.getCodeId();
        }
        catch (Exception e) {
            this.msg = e.getMessage();
            logger.log(Level.DEBUG, this.msg);
            throw new GeneralCodeException(e);
        }
    }

    /**
     * Updates general code and persist the changes to the database.
     * 
     * @param obj The {@link GeneralCodes} object to update.
     * @return The total number of rows effected by the transaction.
     * @throws GeneralCodeException
     */
    protected int updateCode(GeneralCodes obj) throws GeneralCodeException {
        try {
            obj.addCriteria(GeneralCodes.PROP_CODEID, obj.getCodeId());
            UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
            obj.setDateUpdated(ut.getDateCreated());
            obj.setUserId(ut.getLoginId());
            int rows = this.updateRow(obj);
            return rows;        }
        catch (Exception e) {
            throw new GeneralCodeException(e);
        }
    }

    /**
     * Deletes a General Code fro the database using a stored procedure call.
     * 
     * @param obj The {@link GeneralCodes} object to delete from the database.
     * @return int The total number of rows effected.
     * @throws GeneralCodeException
     */
    public int deleteCode(GeneralCodes obj) throws GeneralCodeException {
        try {
            obj.addCriteria(GeneralCodes.PROP_CODEID, obj.getCodeId());
            int rows = this.deleteRow(obj);
            return rows;        }
        catch (Exception e) {
            throw new GeneralCodeException(e);
        }
    }

   
    /**
     * Fetches a list of VwCodes instances based on the general code groups specified in <i>groupId</i>.
     * 
     * @param groupId
     *          one or more integers representing group id's.
     * @return  a list of {@link com.bean.VwCodes VwCodes} instances.
     * @throws GeneralCodeException
     * @see com.api.codes.CodesApi#findLookupData(int[])
     */
    public List <VwCodes> findLookupData(int[] groupId) throws GeneralCodeException {
        // Build selection criteria based on the group id's supplied in groupId array, if available.
        String criteria = null;
        if (groupId != null) {
            StringBuffer buf = null;
            boolean firstTime = true;
            for (int grpId : groupId) {
                if (firstTime) {
                    buf = new StringBuffer();
                    buf.append(" group_id in (");
                    buf.append(grpId);
                    firstTime = false;
                }
                else {
                    buf.append(", ");
                    buf.append(grpId);
                }
            }
            buf.append(")");
            criteria = buf.toString();
        }
        
        // Perform query
        VwCodes obj = CodesFactory.createVmCodes();
        obj.addCustomCriteria(criteria);
        return this.retrieveList(obj);
    }

}