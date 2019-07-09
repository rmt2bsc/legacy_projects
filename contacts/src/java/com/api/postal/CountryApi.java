package com.api.postal;

import java.util.List;

import com.api.BaseDataSource;

import com.bean.Country;

/**
 * This interface provides functionality to gather information 
 * pertaining to various countries.
 * 
 * @author RTerrell
 *
 */
public interface CountryApi extends BaseDataSource {
    /**
     * Finds data pertaining to a particular country.
     * 
     * @param countryId The id of the country.
     * @return {@link Country} if found and null in not found.
     * @throws CountryException
     */
    Object findCountryById(int countryId) throws CountryException;

    /**
     * Finds countries by country name.
     * 
     * @param name The name of the country.
     * @return A List of objects.
     * @throws CountryException
     */
    Object findCountryByName(String name) throws CountryException;

    /**
     * Find countries based on custom selection criteria.
     * 
     * @param criteria Custom selectin criteria
     * @return A List of objects.
     * @throws CountryException
     */
    Object findCountry(String criteria) throws CountryException;
    
    /**
     * Adds or updates a record via some external datasource pertaining to a object 
     * representing a country.
     * 
     * @param obj An {@link com.bean.Country Country} object
     * @return int
     * @throws CountryException
     */
    int maintainCountry(Country obj) throws CountryException;
    
    /**
     * Deletes a country record from an external datasource.
     * 
     * @param obj An {@link com.bean.Country Country} object
     * @return  The total number of records effected from the transaction.
     * @throws CountryException
     */
    int deleteCountry(Country obj) throws CountryException;
}
