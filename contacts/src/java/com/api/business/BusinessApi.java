package com.api.business;

import com.api.Contact;


/**
 * An interface represented by methods that queries an external data 
 * source for Business contacts and manages its many different persistence 
 * states.
 * 
 * @author RTerrell
 * 
 */
public interface BusinessApi extends Contact {
    /**
     * Contact business type value
     */
    static int UNKNOWN_BUSTYPE = -100;
    /** 
     * Contact service type value
     */
    static int UNKNOWN_SERVTYPE = -110;

    /**
     * Fetches a business entiry along with its address information.
     * 
     * @param busId THe internal id of the business.
     * @return An arbitrary object represetnting the business and its address.
     * @throws ContactException
     */
    Object findBusAddress(int busId) throws BusinessException;
    
    
	/**
	 * Fetches business contacts using the first name of the contact person.
	 * 
	 * @param firstName The first name of business contact person.
	 * @return Arbitrary object.
	 * @throws BusinessException
	 */
    Object findBusByContactFirstName(String firstName) throws BusinessException;

    /**
     * Fetches business contacts using the last name of the contact person.
     * @param lastName The last name of the business contact person.
     * @return Arbitrary object.
     * @throws BusinessException
     */
    Object findBusByContactLastName(String lastName) throws BusinessException;

    /**
     * Fetches business contacts by business type.
     * 
     * @param busTypeId The id of the business type.
     * @return Arbitrary object.
     * @throws BusinessException
     */
    Object findBusByBusType(int busTypeId) throws BusinessException;

    /**
     * Fetches business contacts by business service type.
     * 
     * @param serviceTypeId The id of the service type.
     * @return Arbitrary object.
     * @throws BusinessException
     */
    Object findBusByServType(int serviceTypeId) throws BusinessException;

    /**
     * Fetches business contacts by long name.
     * 
     * @param longname The long name of the business.
     * @return Arbitrary object.
     * @throws BusinessException
     */
    Object findBusByLongName(String longname) throws BusinessException;

    /**
     * Fetches business contacts by short name.
     * 
     * @param shortname 
     *          the short name of the business which the format is 
     *          <Last name>, <First name>.
     * @return List of arbirtrary objects.
     * @throws BusinessException
     */
    Object findBusByShortName(String shortname) throws BusinessException;

    /**
     * Fetches business contacts by a tax id number.
     * 
     * @param taxId The tax id.
     * @return Arbitrary object.
     * @throws BusinessException
     */
    Object findBusByTaxId(String taxId) throws BusinessException;

    /**
     * Fetches business contacts by a website url.
     * 
     * @param url The URL of the busiess' website.
     * @return Arbitrary object.
     * @throws BusinessException
     */
    Object findBusByWebsite(String url) throws BusinessException;

    /**
     * Copies the contents of a business contact object to another.
     * 
     * @param source The business contact to copy.
     * @return The replicated business contact.
     * @throws BusinessException
     */
    Object copyBusiness(Object source) throws BusinessException;
    
    /**
     * Fetches business contacts using a list id's.
     * 
     * @param idList A list of id's
     * @return An arbitrary object.
     * @throws BusinessException
     */
    Object findByBusinessId(String idList) throws BusinessException;

}
