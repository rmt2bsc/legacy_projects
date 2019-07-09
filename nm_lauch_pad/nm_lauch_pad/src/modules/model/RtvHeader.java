package modules.model;

import java.util.Date;

import com.nv.util.GeneralUtil;

/**
 * The common model class for managing RTV Header data
 * 
 * @author rterrell
 *
 */
public class RtvHeader extends CommonModel {

    private long rtvNo;

    private String rtvType;

    private int dept;

    private int vendor;

    private Date dateEntered;

    private int reasonCd;

    private int storeNo;

    private Date sendByDate;

    private String raNo;

    private double retail;

    private int qty;

    private Date statusDate;

    private int assoc;

    private Integer qtySummed;

    /**
     * Create a RtvHeader.
     */
    public RtvHeader() {
        super();
    }

    /**
     * @return the rtvNo
     */
    public long getRtvNo() {
        return rtvNo;
    }

    /**
     * @param rtvNo
     *            the rtvNo to set
     */
    public void setRtvNo(long rtvNo) {
        this.rtvNo = rtvNo;
    }

    /**
     * @return the dept
     */
    public int getDept() {
        return dept;
    }

    /**
     * @param dept
     *            the dept to set
     */
    public void setDept(int dept) {
        this.dept = dept;
    }

    /**
     * @return the vendor
     */
    public int getVendor() {
        return vendor;
    }

    /**
     * @param vendor
     *            the vendor to set
     */
    public void setVendor(int vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the dateEntered
     */
    public Date getDateEntered() {
        return dateEntered;
    }

    /**
     * @param dateEntered
     *            the dateEntered to set
     */
    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    /**
     * @return the reasonCd
     */
    public int getReasonCd() {
        return reasonCd;
    }

    /**
     * @param reasonCd
     *            the reasonCd to set
     */
    public void setReasonCd(int reasonCd) {
        this.reasonCd = reasonCd;
    }

    /**
     * @return the storeNo
     */
    public int getStoreNo() {
        return storeNo;
    }

    /**
     * @param storeNo
     *            the storeNo to set
     */
    public void setStoreNo(int storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * @return the sendByDate
     */
    public Date getSendByDate() {
        return sendByDate;
    }

    /**
     * @param sendByDate
     *            the sendByDate to set
     */
    public void setSendByDate(Date sendByDate) {
        this.sendByDate = sendByDate;
    }

    /**
     * @return the raNo
     */
    public String getRaNo() {
        return raNo;
    }

    /**
     * @param raNo
     *            the raNo to set
     */
    public void setRaNo(String raNo) {
        this.raNo = raNo;
    }

    /**
     * @return the retail
     */
    public double getRetail() {
        return retail;
    }

    /**
     * @param retail
     *            the retail to set
     */
    public void setRetail(double retail) {
        this.retail = retail;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty
     *            the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
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
     * @return the assoc
     */
    public int getAssoc() {
        return assoc;
    }

    /**
     * @param assoc
     *            the assoc to set
     */
    public void setAssoc(int assoc) {
        this.assoc = assoc;
    }

    /**
     * Get the String version of date entered property.
     * 
     * @return the dateEntered
     */
    public String getDateEnteredStr() {
        if (this.dateEntered == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.dateEntered, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the rtvType
     */
    public String getRtvType() {
        return rtvType;
    }

    /**
     * @param rtvType
     *            the rtvType to set
     */
    public void setRtvType(String rtvType) {
        this.rtvType = rtvType;
    }

    /**
     * @return the qtySummed
     */
    public Integer getQtySummed() {
        return qtySummed;
    }

    /**
     * @param qtySummed
     *            the qtySummed to set
     */
    public void setQtySummed(Integer qtySummed) {
        this.qtySummed = qtySummed;
    }
}
