package com.api.address;

import com.api.Contact;


/**
 * An interface represented by methods that queries an external data 
 * source for personal and business contact addresses and manages its many 
 * different persistence states.
 * 
 * @author RTerrell
 *
 */
public interface AddressApi extends Contact {
	
	
    /**
     * Fetch one or more address records using the person id.
     * 
     * @param personId The person id.
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByPersonId(int personId) throws AddressException;

    /**
     * Fetch one or more address records using the business id.
     * 
     * @param busId The business Id.
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByBusinessId(int busId) throws AddressException;

    /**
     * Fetch one or more address records using the value of line 1 address. 
     *  
     * @param addrLine1 Line #1 of the address
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByAddr1(String addrLine1) throws AddressException;

    /**
     * Fetch one or more address records using the zip code value.
     *  
     * @param zipcode The zip code to search.
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByZip(String zipcode) throws AddressException;

    /**
     * Fetch one or more address records using the home phone number.
     * 
     * @param phoneNo The home phone number.
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByHomeNo(String phoneNo) throws AddressException;

    /**
     * Fetch one or more address records using the work phone number.
     * 
     * @param phoneNo The work phone number.
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByWorkNo(String phoneNo) throws AddressException;

    /**
     * Fetch one or more address records using the cellular phone number.
     * 
     * @param phoneNo The cellular phone number.
     * @return An arbitrary object.
     * @throws AddressException
     */
    Object findAddrByCellNo(String phoneNo) throws AddressException;

    /**
     * Fetch one or more address records using the fax phone number.
     * 
     * @param phoneNo The fax number.
     * @return An arbitrary object. 
     * @throws AddressException
     */
    Object findAddrByFaxNo(String phoneNo) throws AddressException;

    /**
     * Fetch one or more address records using the pager number.
     * 
     * @param phoneNo the pager number.
     * @return An arbitrary object. 
     * @throws AddressException
     */
    Object findAddrByPagerNo(String phoneNo) throws AddressException;

    /**
     * Fetch one or more address records using the main number.
     * 
     * @param phoneNo The main phone number.
     * @return An arbitrary object. 
     * @throws AddressException
     */
    Object findAddrByMainNo(String phoneNo) throws AddressException;
    
    /**
     * Deletes a contact's address using the id of the address.
     * 
     * @param id THe address id.
     * @return The total number of rows deleted
     * @throws AddressException
     */
    int deleteContact(int id) throws AddressException;

    /**
     * Copies the contnets of a contact's Address object to another.
     * 
     * @param source The Address object to copy.
     * @return Address data.
     * @throws AddressException
     */
    Object copyAddress(Object source) throws AddressException;
}
