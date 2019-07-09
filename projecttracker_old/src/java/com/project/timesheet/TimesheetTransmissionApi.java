package com.project.timesheet;

import java.util.Map;

import com.bean.ProjClient;
import com.bean.ProjTimesheet;
import com.bean.VwEmployeeExt;
import com.project.ProjectException;

/**
 * Interface for transmitting a timesheet from one party to another.
 * 
 * @author RTerrell
 *
 */
public interface TimesheetTransmissionApi {

    final String SUBJECT_PREFIX = "Timesheet Submission for ";

    /**
     * Sends a copy of a given timesheet to a designated recipient.
     * 
     * @param timesheet 
     *          An instance of {@link com.bean.ProjTimesheet ProjTimesheet}
     * @param employee 
     *          An instance of {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @param client 
     *          An instance of {@link com.bean.ProjClient ProjClient}
     * @param hours 
     *          A List of Map instances which the key/value pairs represent 
     *          {@link com.bean.VwTimesheetProjectTask VwTimesheetProjectTask} and 
     *          {@link com.bean.ProjEvent VwTimesheetProjectTask}, respectively.
     * @return int
     * @throws ProjectException
     *           Thrown when either <i>timesheet</i>, <i>employee</i>, <i>client</i>, 
     *           or <i>hours</i> are invalid.  If <i>hours</i> does not contain any 
     *           entries. 
     * @throws TimesheetTransmissionException
     *           Error occurs sending timesheet data to its designated recipient.
     */
    int send(ProjTimesheet timesheet, VwEmployeeExt employee, ProjClient client, Map hours) throws ProjectException, TimesheetTransmissionException;
}
