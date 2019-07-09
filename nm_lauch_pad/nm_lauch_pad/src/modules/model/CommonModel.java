package modules.model;

import java.util.Date;

/**
 * A common model class.
 * 
 * @author rterrell
 *
 */
public class CommonModel {
    /**
     * Common Id property
     */
    protected int id;

    /**
     * Common current date property
     */
    protected Date currDate;

    /**
     * Common status property
     */
    protected String status;

    /**
     * Common status Date
     */
    protected Date statusDate;

    /**
     * Common new Status
     */
    protected String newStatus;

    /**
     * The id of the user currently logged on to the system.
     */
    protected int currentUserId;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the currDate
     */
    public Date getCurrDate() {
        return currDate;
    }

    /**
     * @param currDate
     *            the currDate to set
     */
    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the statusDate
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate
     *            the statusDate to set
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the newStatus
     */
    public String getNewStatus() {
        return newStatus;
    }

    /**
     * @param newStatus
     *            the newStatus to set
     */
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    /**
     * @return the currentUserId
     */
    public int getCurrentUserId() {
        return currentUserId;
    }

    /**
     * @param currentUserId
     *            the currentUserId to set
     */
    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }
}
