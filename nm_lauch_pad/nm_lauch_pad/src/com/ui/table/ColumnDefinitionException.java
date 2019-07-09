package com.ui.table;

/**
 * Exception class for identifying column definition errors for JTable related
 * instances.
 * 
 * @author rterrell
 *
 */
public class ColumnDefinitionException extends RuntimeException {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public ColumnDefinitionException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public ColumnDefinitionException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public ColumnDefinitionException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public ColumnDefinitionException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
