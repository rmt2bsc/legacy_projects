package com.project.timesheet;

import com.util.RMT2Exception;

/**
 * Handles exceptions regarding timesheet transmissions from one party to another.
 * 
 * @author appdev
 *
 */
public class TimesheetTransmissionException extends RMT2Exception {
    private static final long serialVersionUID = -8851874846044566159L;

    public TimesheetTransmissionException() {
	super();
    }

    public TimesheetTransmissionException(String msg) {
	super(msg);
    }

    public TimesheetTransmissionException(int code) {
	super(code);
    }

    public TimesheetTransmissionException(String msg, int code) {
	super(msg, code);
    }

    public TimesheetTransmissionException(Exception e) {
	super(e);
    }
}
