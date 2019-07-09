package com.api.postal;

import com.api.BaseDataSource;

import com.bean.State;

/**
 * Queries an external data source for states and provinces of various countries.
 *
 * @author RTerrell
 *
 */
public interface StatesApi extends BaseDataSource {
	/**
	 * Find state information based on the state id.
	 *
	 * @param stateId The state id.
	 * @return {@link State} object when found and null when not found.
	 * @throws StatesException
	 */
    Object findStateById(int stateId) throws StatesException;

    /**
     * Find state information based on a target country.
     *
     * @param countryId The id of the country.
     * @return A List of objects pertaining to one or more states.
     * @throws StatesException
     */
    Object findStateByCountry(int countryId) throws StatesException;

    /**
     * Find one or more state data items that incrementally matches a state name.
     *
     * @param stateName The name of the state.
     * @return A List of objects pertaining to one or more states.
     * @throws StatesException
     */
    Object findStateByName(String stateName) throws StatesException;

    /**
     * Find state information using custom selection criteria.
     *
     * @param criteria Custom criteria
     * @return A List of objects pertaining to one or more states.
     * @throws StatesException
     *                      When query does not contain any selection criteria or general database erros.
     */
    Object findState(String criteria) throws StatesException;
    
    /**
     * Creates a new or modifies an existing state and persist to an external data source.
     * 
     * @param obj The state intstance to persist
     * @return int
     * @throws StatesException
     */
    int maintainState(State obj) throws StatesException;
    
    /**
     * Deletes a State record from an external datasource.
     * 
     * @param obj The state to delete.
     * @return The total number of row effected.
     * @throws StatesException
     */
    int deleteState(State obj) throws StatesException;
}
