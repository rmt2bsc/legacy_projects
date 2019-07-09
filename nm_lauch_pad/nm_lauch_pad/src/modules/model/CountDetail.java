package modules.model;

/**
 * The common model class Representing the joining of cnt_hdr and cnt_dtl tables
 * 
 * @author rterrell
 *
 */

public class CountDetail extends Count {

    private long newHdrId;

    private String hdrStatus;

    private String dtlStatus;

    private int qtySum;

    private int count;

    private int assocNo;

    public CountDetail() {
        super();
    }

    /**
     * @return the hdrStatus
     */
    public String getHdrStatus() {
        return hdrStatus;
    }

    /**
     * @param hdrStatus
     *            the hdrStatus to set
     */
    public void setHdrStatus(String hdrStatus) {
        this.hdrStatus = hdrStatus;
    }

    /**
     * @return the dtlStatus
     */
    public String getDtlStatus() {
        return dtlStatus;
    }

    /**
     * @param dtlStatus
     *            the dtlStatus to set
     */
    public void setDtlStatus(String dtlStatus) {
        this.dtlStatus = dtlStatus;
    }

    /**
     * @return the qtySum
     */
    public int getQtySum() {
        return qtySum;
    }

    /**
     * @param qtySum
     *            the qtySum to set
     */
    public void setQtySum(int qtySum) {
        this.qtySum = qtySum;
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
     * @return the assocNo
     */
    public int getAssocNo() {
        return assocNo;
    }

    /**
     * @param assocNo
     *            the assocNo to set
     */
    public void setAssocNo(int assocNo) {
        this.assocNo = assocNo;
    }

    /**
     * @return the newHdrId
     */
    public long getNewHdrId() {
        return newHdrId;
    }

    /**
     * @param newHdrId
     *            the newHdrId to set
     */
    public void setNewHdrId(long newHdrId) {
        this.newHdrId = newHdrId;
    }

}
