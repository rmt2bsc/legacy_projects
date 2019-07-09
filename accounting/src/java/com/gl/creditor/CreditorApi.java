package com.gl.creditor;

import com.api.BaseDataSource;
import com.bean.Creditor;
import com.bean.criteria.CreditorCriteria;

/**
 * An interface that provides the method prototypes required to be implemented 
 * in order to fetch, persist, and validate creditor objects.
 * 
 * @author RTerrell
 *
 */
public interface CreditorApi extends BaseDataSource {

    /** Represents Creditor data */
    static final String CLIENT_DATA_CREDITOR = "creditor";

    /** Represents the creditor's balance */
    static final String CLIENT_DATA_CREDITOR_BAL = "creditorbalance";

    /**
     * Fetch creditor using custom selection criteria.
     * 
     * @param criteria 
     *          The selection criteria that is to be applied to the query of the implementation. 
     * @return A List of abitrary objects.
     * @throws CreditorException
     */
    Object findCreditor(String criteria) throws CreditorException;

    /**
     * Fetch creditor object using its id.
     * 
     * @param id creditor's internal unique id
     * @return An arbitrary object.
     * @throws CreditorException
     */
    Object findById(int id) throws CreditorException;

    /**
     * Fetch one or more creditors based on a business contact.
     * 
     * @param businessId The internal unique id of the business.
     * @return A List of abitrary objects.
     * @throws CreditorException
     */
    Object findByBusinessId(int businessId) throws CreditorException;

    /**
     * Fetch creditors based on a particular creditor type.
     * 
     * @param creditorTypeId The credior type id.
     * @return A List of abitrary objects.
     * @throws CreditorException
     */
    Object findByCreditorType(int creditorTypeId) throws CreditorException;

    /**
     * Fetch creditors using an account number.
     * 
     * @param acctNo The creditor's account number
     * @return A List of abitrary objects.
     * @throws CreditorException
     */
    Object findByAcctNo(String acctNo) throws CreditorException;

    /**
     * Fetch the business contact information related to a particular creditor.
     * 
     * @param creditorId the creditor's internal unique id.
     * @return An arbitrary object.
     * @throws CreditorException
     */
    Object findCreditorBusiness(int creditorId) throws CreditorException;

    /**
     * Fetch the business contact information related to a particular creditor 
     * using custom selection criteria.
     * 
     * @param criteria selection criteria suited for a particular kind of query.
     * @return An abitrary object.
     * @throws CreditorException
     * @deprecated
     */
    Object findCreditorBusiness(String criteria) throws CreditorException;
    
    
    /**
     * 
     * @param criteria
     * @return
     * @throws CreditorException
     */
    Object findCreditorBusiness(CreditorCriteria criteria) throws CreditorException;

    /**
     * Obtain the creditor's balance.
     * 
     * @param id internal unique id
     * @return double as the creditor's balance.
     * @throws CreditorException
     */
    double findCreditorBalance(int id) throws CreditorException;

    /**
     * Fetch one or more creditor type records using custom selection criteria.
     * 
     * @param criteria selection criteria suited for a particular kind of query.
     * @return A List of abitrary objects.
     * @throws CreditorException
     */
    Object findCreditorType(String criteria) throws CreditorException;

    /**
     * Fetch a creditor type record using its id.
     * 
     * @param id internal unique id of the creditor type.
     * @return An arbitrary object.
     * @throws CreditorException
     */
    Object findCreditorTypeById(int id) throws CreditorException;

    /**
     * Persist creditor data changes by either adding a new or updating an 
     * existing creditor to an external data provider.
     * 
     * @param obj 
     *          The base creditor object
     * @param objExt 
     *          An object that serves as an extension to the base creditor object.
     * @return int
     * @throws CreditorException
     */
    int maintainCreditor(Creditor obj, Object objExt) throws CreditorException;

    /**
     * Delete a creditor record from the system.
     * 
     * @param credId internal unique id of the creditor.
     * @return int
     * @throws CreditorException
     */
    int deleteCreditor(int credId) throws CreditorException;

    /**
     * Validate creditor and, if applicable, its extension object.
     * 
     * @param obj 
     *          The base creditor object
     * @throws CreditorException
     */
    void validateCreditor(Creditor obj) throws CreditorException;

    /**
     * Validate creditor's extension object.
     * 
     * @param obj
     *          An object that serves as an extension to the base creditor object.
     * @throws CreditorException
     */
    void validateCreditorExt(Object obj) throws CreditorException;

}
