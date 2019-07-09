package com.api.codes;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;

import com.util.SystemException;

/**
 * An implementation of the CodesApi interfaces which manages the retrieval, 
 * modification, and mapping of XML data pertaining to general code entities.  
 * The source of the data is expected to be from relational database management 
 * system.  All queries pertaining to this implementation return XML as their
 * result set.
 * 
 * @author RTerrell
 * @deprecated Will be removed in future versions.  Use JAXB to bind DB results to java.
 * 
 */
class GeneralXmlCodesImpl extends RdbmsDaoImpl implements CodesApi {
    private Logger logger;

    /**
     * Creates a GeneralCodesImpl object by initializing the
     * DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn {@link DatabaseConnectionBean}
     * @throws SystemException
     * @throws DatabaseException
     */
    public GeneralXmlCodesImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
        super(dbConn);
        logger = Logger.getLogger("GeneralCodesImpl");
    }

    /**
     * Creates a GeneralCodesImpl object by initializing the
     * DatabaseConnectionBean and a HttpServletRequest.
     * 
     * @param dbConn
     *            {@link DatabaseConnectionBean}
     * @param request
     *            HttpServletRequest
     * @throws SystemException
     * @throws DatabaseException
     */
    public GeneralXmlCodesImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
        this(dbConn);
        this.request = request;
    }

    
    /**
     * Helper method for retrieving general code and general code group XML data.
     * 
     * @param obj The ORM object to retrieve data.
     * @return String result set as XML.
     * @throws GeneralCodeException For general database errors.
     */
    private String retrieveXMLData(OrmBean obj) throws GeneralCodeException {
        try {
            Object data[] = this.retrieve(obj);
            if (data != null && data.length > 0) {
            return (String) data[0];
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
     * @param id
     *            The primary key of the group to search.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findGroupById(int id) throws GeneralCodeException {
        GeneralCodesGroup group = CodesFactory.createXmlGeneralGroup();
        group.addCriteria(GeneralCodesGroup.PROP_CODEGRPID, id);
        return this.retrieveXMLData(group);
    }

    /**
     * Finds General Code Group by Description
     * 
     * @param descr
     *            The description used to search for one or more
     *            {@link GeneralCodesGroup} objects.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findGroupByDescription(String descr) throws GeneralCodeException {
        GeneralCodesGroup group = CodesFactory.createXmlGeneralGroup();
        group.addLikeClause(GeneralCodesGroup.PROP_DESCRIPTION, descr);
        return this.retrieveXMLData(group);
    }

    /**
     * Finds one or more general code groups by using custom criteria
     * supplied by the client.
     * 
     * @param criteria
     *            SQL predicate String.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findGroup(String criteria) throws GeneralCodeException {
        GeneralCodesGroup group = CodesFactory.createXmlGeneralGroup();
        group.addCustomCriteria(criteria);
        return this.retrieveXMLData(group);
    }

    /**
     * Finds all general codes by using general code group id.
     * 
     * @param groupId
     *            The group id.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findCodeByGroup(int groupId) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createXmlGeneralCodes();
        code.addCriteria(GeneralCodes.PROP_CODEGRPID, groupId);
        code.addOrderBy(GeneralCodes.PROP_LONGDESC, GeneralCodes.ORDERBY_ASCENDING);
        return this.retrieveXMLData(code);
    }

    /**
     * Finds general code by using primary key.
     * 
     * @param id
     *            The primary key of the Genreal Code to search.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findCodeById(int id) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createXmlGeneralCodes();
        code.addCriteria(GeneralCodes.PROP_CODEID, id);
        return this.retrieveXMLData(code);
    }

    /**
     * Finds General Code by Description.
     * 
     * @param descr
     *            The description used to search for a general code.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findCodeByDescription(String descr) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createXmlGeneralCodes();
        code.addCriteria(GeneralCodes.PROP_LONGDESC, descr);
        code.addOrderBy(GeneralCodes.PROP_LONGDESC, GeneralCodes.ORDERBY_ASCENDING);
        return this.retrieveXMLData(code);
    }

    /**
     * Finds one or more general codes by using custom SQL selection criteria
     * supplied by the client.
     * <p>
     * The resultset is order by long description.
     * 
     * @param criteria
     *            String representing the SQL predicate.
     * @return XML String
     * @throws GeneralCodeException
     */
    public String findCode(String criteria) throws GeneralCodeException {
        GeneralCodes code = CodesFactory.createXmlGeneralCodes();
        code.addCustomCriteria(criteria);
        code.addOrderBy(GeneralCodes.PROP_LONGDESC, GeneralCodes.ORDERBY_ASCENDING);
        return this.retrieveXMLData(code);
    }

    /**
     * Not implemented.
     * 
     * @param obj
     *            The {@link GeneralCodesGroup} to add or modify.
     * @return 1.
     * @throws GeneralCodeException
     */
    public int maintainGroup(GeneralCodesGroup obj) throws GeneralCodeException {
        return 1;
    }

    /**
     * Not implemented.
     * 
     * @param obj The {@link GeneralCodesGroup} object to delete.
     * @return zero
     * @throws GeneralCodeException
     */
    public int deleteGroup(GeneralCodesGroup _obj) throws GeneralCodeException {
        return 0;
    }

    /**
     * Not implemented.
     * 
     * @param obj
     *            The {@link GeneralCodes} object to maintain.
     * @return 1.
     * @throws GeneralCodeException
     */
    public int maintainCode(GeneralCodes obj) throws GeneralCodeException {
        return 1;
    }

    /**
     * Not implemented.
     * 
     * @param obj The {@link GeneralCodes} object to delete from the database.
     * @return zero
     * @throws GeneralCodeException
     */
    public int deleteCode(GeneralCodes obj) throws GeneralCodeException {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.api.codes.CodesApi#findLookupData(int[])
     */
    public List<Object> findLookupData(int[] groupId) throws GeneralCodeException {
        // TODO Auto-generated method stub
        return null;
    }

}