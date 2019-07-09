package modules.report;

/**
 * Exception class for identifying situations where a report worker fails during
 * the sending of the request to the UNIX server.
 * 
 * @author rterrell
 *
 */
public class ReportWorkerException extends Exception {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public ReportWorkerException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public ReportWorkerException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public ReportWorkerException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public ReportWorkerException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
