package modules.model;

import java.util.Date;

import com.nv.util.GeneralUtil;

/**
 * The Count model representing the cnt_hdr table.
 * 
 * @author rterrell
 *
 */
public class Count extends CommonModel {

    private int hdrId;

    private int division;

    private Date inventoryDate;

    private Date startDate;

    private Date startTime;

    private Date statusDate;

    private int lastModifiedBy;

    private Date transmitDate;

    private int count;

    /**
     * Create a Count object
     */
    public Count() {
        return;
    }

    /**
     * @return the hdrId
     */
    public int getHdrId() {
        return hdrId;
    }

    /**
     * @param hdrId
     *            the hdrId to set
     */
    public void setHdrId(int hdrId) {
        this.hdrId = hdrId;
    }

    /**
     * @return the division
     */
    public int getDivision() {
        return division;
    }

    /**
     * @param division
     *            the division to set
     */
    public void setDivision(int division) {
        this.division = division;
    }

    /**
     * @return the inventoryDate
     */
    public Date getInventoryDate() {
        return inventoryDate;
    }

    /**
     * @param inventoryDate
     *            the inventoryDate to set
     */
    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * @return the lastModifiedBy
     */
    public int getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * @param lastModifiedBy
     *            the lastModifiedBy to set
     */
    public void setLastModifiedBy(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * @return the transmitDate
     */
    public Date getTransmitDate() {
        return transmitDate;
    }

    /**
     * @param transmitDate
     *            the transmitDate to set
     */
    public void setTransmitDate(Date transmitDate) {
        this.transmitDate = transmitDate;
    }

    /**
     * Get inventory date as a String in the format of "MM-dd-yyyy".
     * 
     * @return String
     */
    public String getInventoryDateStr() {
        if (this.inventoryDate == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.inventoryDate, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get start date as a String in the format of "MM-dd-yyyy HH:mm:ss".
     * 
     * @return String
     */
    public String getStartDateTimeStr() {
        if (this.startDate == null) {
            return null;
        }
        try {
            return GeneralUtil
                    .formatDate(this.startDate, "MM-dd-yyyy HH:mm:ss");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get start date as a String in the format of "MM-dd-yyyy".
     * 
     * @return String
     */
    public String getStartDateStr() {
        if (this.startDate == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.startDate, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

}
