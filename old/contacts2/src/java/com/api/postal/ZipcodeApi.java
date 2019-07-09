package com.api.postal;

import java.util.List;

import com.api.BaseDataSource;
import com.api.db.pagination.PaginationQueryResults;

import com.bean.VwZipcode;
import com.bean.Zipcode;

/**
 * An interface that defines method needed to query an external data source for
 * zip code information.
 * 
 * @author RTerrell
 * 
 */
public interface ZipcodeApi extends BaseDataSource {
    /**
     * Find zip code data using a unique identifier representing the zip code
     * record.
     * 
     * @param uid
     *            The unique identifier
     * @return {@link Zipcode}
     * @throws ZipcodeException
     */
    Object findZipById(int uid) throws ZipcodeException;

    /**
     * Find zip code extension view data using a unique identifier representing the zip code
     * record.
     * 
     * @param uid
     *            The unique identifier
     * @return An arbitrary object representing the zip code extension
     * @throws ZipcodeException
     */
    Object findZipExtById(int uid) throws ZipcodeException;

    /**
     * Find zip code data using a String zip code value as the argument.
     * 
     * @param zipcode
     *            zip code.
     * @return {@link Zipcode}
     * @throws ZipcodeException
     */
    Object findZipByCode(String zipcode) throws ZipcodeException;

    /**
     * Find zip code extension data using a String zip code value as the argument.
     * 
     * @param zipcode
     *            zip code.
     * @return An arbitrary object representing the zip conde extension.
     * @throws ZipcodeException
     */
    Object findZipExtByCode(String zipcode) throws ZipcodeException;

    /**
     * Find zip code data using a integer zip code value as the argument.
     * 
     * @param zipcode
     * @return {@link Zipcode}
     * @throws ZipcodeException
     */
    Object findZipByCode(int zipcode) throws ZipcodeException;

    /**
     * Finds zip codes using incremental searching technique on a specific
     * column.
     * 
     * @param zipcode
     *            The zip code to search for.
     * @return A List of objects if found and null if not found.
     * @throws ZipcodeException
     */
    Object findZipX(String zipcode) throws ZipcodeException;

    /**
     * Finds one or more zip codes using incremental matching on a column that
     * represents the area code.
     * 
     * @param areaCode
     *            The are code.
     * @return A List of objects if found and null if not found.
     * @throws ZipcodeException
     */
    Object findZipByAreaCode(String areaCode) throws ZipcodeException;

    /**
     * Finds one or more zip codes using incremental matching on a column that
     * represents a State.
     * 
     * @param state
     *            A state of the United States.
     * @return A List of objects if found and null if not found.
     * @throws ZipcodeException
     */
    Object findZipByState(String state) throws ZipcodeException;

    /**
     * Finds one or more zip codes using incremental matching on a column
     * representing the city.
     * 
     * @param city
     *            The city in which the zip code belongs.
     * @return If found, retruns a List of objects. Otherwise, returns null.
     * @throws ZipcodeException
     */
    Object findZipByCity(String city) throws ZipcodeException;

    /**
     * Fetches one or more zip codes using incremental matching on a column that
     * represents the county.
     * 
     * @param county
     *            The county in which the zip code belongs.
     * @return A List of objects if found and null if not found.
     * @throws ZipcodeException
     */
    Object findZipByCounty(String county) throws ZipcodeException;

    /**
     * Fetches one or more zip codes using incremental matching on a column that
     * represents the time zone.
     * 
     * @param timeZone
     *            The time zone of a zip code.
     * @return A List of objects if found and null if not found.
     * @throws ZipcodeException
     */
    Object findZipByTimeZone(String timeZone) throws ZipcodeException;

    /**
     * Finds one or more zip codes based on custom criteria supplied by the
     * client.
     * 
     * @param criteria
     *            The selection criteria used to query a data source for zip
     *            codes.
     * @return A List of objects if found and null if not found.
     * @throws ZipcodeException
     *                  When criteria has not been entered.
     */
    Object findZip(String criteria) throws ZipcodeException;
    
    /**
     * 
     * @param criteria
     * @param pageNo
     * @return
     * @throws ZipcodeException
     */
    PaginationQueryResults findZip(String criteria, int pageNo) throws ZipcodeException ;
}
