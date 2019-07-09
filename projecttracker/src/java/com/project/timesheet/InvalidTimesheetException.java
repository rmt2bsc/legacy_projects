package com.project.timesheet;

import com.util.RMT2Exception;

/**
 * Represents exceptions regarding an invalid timesheet.
 * 
 * @author appdev
 *
 */
public class InvalidTimesheetException extends RMT2Exception {
    private static final long serialVersionUID = -8851874846044566159L;

    public InvalidTimesheetException() {
	super();
    }

    public InvalidTimesheetException(String msg) {
	super(msg);
    }

    public InvalidTimesheetException(int code) {
	super(code);
    }

    public InvalidTimesheetException(String msg, Throwable e) {
	super(msg, e);
    }

    public InvalidTimesheetException(Exception e) {
	super(e);
    }
}
