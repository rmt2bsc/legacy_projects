package com.api;

import com.api.BaseDataSource;

/**
 * A common contact interface.
 * 
 * @author RTerrell
 *
 */
public interface Contact extends BaseDataSource {

	/**
	 * Fetch contact record using an unique id.
	 * 
	 * @param uid The id of the address
	 * @return An arbitrary object.
	 * @throws ContactException
	 */
    Object findContact(int uid) throws ContactException;

    
    /**
     * Fetch one or more contact objects using custom selection criteria.
     * 
     * @param criteria Selection criteria.
     * @return An arbitrary object. 
     * @throws ContactException
     */
    Object findContact(String criteria) throws ContactException;

    /**
     * Drives the process of creating or updating a contact object to a data source.
     * 
     * @param data The contact object to apply updates to the data source.
     * @return int value.
     * @throws ContactException
     */
    int maintainContact(Object data) throws ContactException;

    /**
     * Deletes a contact object from a data source.
     * 
     * @param obj The contact object to delete.
     * @return int value
     * @throws ContactException
     */
    int deleteContact(Object obj) throws ContactException;

    /**
     * Validate a contact object.
     * 
     * @param obj The contact object to validate.
     * @throws ContactException
     */
    void validateContact(Object obj) throws ContactException;
    
    
}
