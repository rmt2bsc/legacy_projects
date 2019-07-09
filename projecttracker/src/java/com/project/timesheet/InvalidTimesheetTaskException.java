package com.project.timesheet;

/**
 * Represents exceptions regarding an invalid timesheet task.
 * 
 * @author appdev
 *
 */
public class InvalidTimesheetTaskException extends InvalidTimesheetException {

    private static final long serialVersionUID = -4516909917215118987L;

    /**
     * 
     */
    public InvalidTimesheetTaskException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public InvalidTimesheetTaskException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param code
     */
    public InvalidTimesheetTaskException(int code) {
        super(code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidTimesheetTaskException(String msg, Throwable e) {
        super(msg, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public InvalidTimesheetTaskException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
