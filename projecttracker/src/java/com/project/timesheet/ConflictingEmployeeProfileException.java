package com.project.timesheet;

import com.util.RMT2Exception;

/**
 * Represents exceptions regarding an invalid timesheet.
 * 
 * @author appdev
 *
 */
public class ConflictingEmployeeProfileException extends RMT2Exception {
    private static final long serialVersionUID = -8851874846044566159L;

    public ConflictingEmployeeProfileException() {
	super();
    }

    public ConflictingEmployeeProfileException(String msg) {
	super(msg);
    }

    public ConflictingEmployeeProfileException(int code) {
	super(code);
    }

    public ConflictingEmployeeProfileException(String msg, Throwable e) {
	super(msg, e);
    }

    public ConflictingEmployeeProfileException(Exception e) {
	super(e);
    }
}
