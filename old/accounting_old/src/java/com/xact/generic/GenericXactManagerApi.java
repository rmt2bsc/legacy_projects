package com.xact.generic;

import java.util.List;

import com.bean.VwGenericXactList;

import com.xact.XactException;

/**
 * The interface that expects to manage various transaction types generically.
 * 
 * @author Roy Terrell
 *
 */
public interface GenericXactManagerApi {

    /**
     * Release resources pertaining to GenericXactManagerApi interface.
     */
    void close();

    /**
     * 
     * @param criteria
     * @return List of VwGenericXactList objects
     * @throws XactException
     */
    List<VwGenericXactList> findGenericXactList(String criteria) throws XactException;

    /**
     * 
     * @param criteria
     * @return Generic object representing a list of VwGenericXactList
     * @throws XactException
     */
    Object findGenericXact(String criteria) throws XactException;

    /**
     * 
     * @param xactId
     * @return A generic object representing VwGenericXactList
     * @throws XactException
     */
    Object findGenericXact(int xactId) throws XactException;

    /**
     * Retrieves one or more transaction type records
     * 
     * @param criteria The selection criteria to apply to the query of the data source.
     * @return A generic object representing multiple {@link XactType}
     * @throws XactException
     */
    Object findXactType(String criteria) throws XactException;

    /**
     * Retrieves one or more transactio sub type records
     * 
     * @param criteria The selection criteria to apply to the query of the data source.
     * @return A generic object representing multiple {@link XactType}
     * @throws XactException
     */
    Object findXactSubType(String criteria) throws XactException;
}
