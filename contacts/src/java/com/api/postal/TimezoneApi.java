package com.api.postal;

import java.util.List;

import com.api.BaseDataSource;

import com.bean.TimeZone;

/**
 * This interface provides functionality to gather information 
 * pertaining to world timezones.
 * 
 * @author RTerrell
 *
 */
public interface TimezoneApi extends BaseDataSource {

    /**
     * Finds data pertaining to a particular time zone.
     * 
     * @param timezoneId The id of the time zone.
     * @return {@link TimeZone}
     * @throws TimezoneException
     */
    Object findTimeZoneById(int timezoneId) throws TimezoneException;

    /**
     * Finds all time zones that are stored in the system.
     * 
     * @return A List of time zones.
     * @throws TimezoneException
     */
    Object findAllTimeZones() throws TimezoneException;
}
