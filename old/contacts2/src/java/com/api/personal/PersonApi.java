package com.api.personal;

import com.api.Contact;
import com.api.ContactException;


/**
 * An interface represented by methods that queries an external data 
 * source for personal contacts and manages its many different 
 * persistence states.
 * 
 * @author RTerrell
 * 
 */
public interface PersonApi extends Contact {
    
    /**
     * Fetches a perrson entity along with its address information.
     * 
     * @param perId THe internal id of the business.
     * @return An arbitrary object represetnting the person and its address.
     * @throws ContactException
     */
    Object findPerAddress(int perId) throws PersonException;

    /**
     * Find personal contacts using first name.
     * 
     * @param firstName The first name of the contact.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByFirstName(String firstName) throws PersonException;

    /**
     * Find personal contacts using last name.
     * 
     * @param lastName The last name of the contact.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByLastName(String lastName) throws PersonException;

    /**
     * Find personal contacts by gender.
     * 
     * @param genderId The gender id.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByGender(int genderId) throws PersonException;

    /**
     * Find persona contacts by marital status.
     * 
     * @param maritalStatId The marital status id.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByMaritalStatus(int maritalStatId) throws PersonException;

    /**
     * Find personal contacts by race.
     * 
     * @param raceId The race id.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByRace(int raceId) throws PersonException;

    /**
     * Find personal contacts by social security number.
     * 
     * @param ssn The person's social security number.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerBySSN(String ssn) throws PersonException;

    /**
     * Find personal contacts by birth date.
     * 
     * @param dob The date of birth of a contact.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByBirthDate(String dob) throws PersonException;

    /**
     * Find personal contacts by email address.
     * 
     * @param emailId The email address of the contact.
     * @return An arbitrary object
     * @throws PersonException
     */
    Object findPerByEMail(String emailId) throws PersonException;

    /**
     * Copy the data of a personal contact to another.
     * 
     * @param source The personal contact to be copied.
     * @return an arbitrary object as the new person
     * @throws PersonException
     */
    Object copyPerson(Object source) throws PersonException;

}
