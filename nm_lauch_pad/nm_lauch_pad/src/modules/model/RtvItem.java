package modules.model;

import com.nv.util.GeneralUtil;

/**
 * The common model class for managing RTV Sku Item data
 * 
 * @author rterrell
 *
 */
public class RtvItem extends SkuItem {

    private long rtvNo;

    private Double origRetail;

    private Double ticketRetail;

    private Integer expectedQty;

    private String pcFlag;

    private Integer pcNo;

    private Integer reasonCd;

    /**
     * Create a RtvItem
     */
    public RtvItem() {
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
     * @return the origRetail
     */
    public Double getOrigRetail() {
        return origRetail;
    }

    /**
     * Return the RTV original retail as a numeric String formatted as
     * "$###0.00;($###0.00)".
     * 
     * @return a formatted numeric String
     */
    public String getOrigRetailStr() {
        try {
            return GeneralUtil.formatNumber(this.origRetail,
                    "###0.00;(###0.00)");
        } catch (Exception e) {
            return this.origRetail.toString();
        }
    }

    /**
     * @param origRetail
     *            the origRetail to set
     */
    public void setOrigRetail(Double origRetail) {
        this.origRetail = origRetail;
    }

    /**
     * @return the ticketRetail
     */
    public Double getTicketRetail() {
        return ticketRetail;
    }

    /**
     * @param ticketRetail
     *            the ticketRetail to set
     */
    public void setTicketRetail(Double ticketRetail) {
        this.ticketRetail = ticketRetail;
    }

    /**
     * @return the expectedQty
     */
    public Integer getExpectedQty() {
        return expectedQty;
    }

    /**
     * @param expectedQty
     *            the expectedQty to set
     */
    public void setExpectedQty(Integer expectedQty) {
        this.expectedQty = expectedQty;
    }

    /**
     * @return the pcFlag
     */
    public String getPcFlag() {
        return pcFlag;
    }

    /**
     * @param pcFlag
     *            the pcFlag to set
     */
    public void setPcFlag(String pcFlag) {
        this.pcFlag = pcFlag;
    }

    /**
     * @return the pcNo
     */
    public Integer getPcNo() {
        return pcNo;
    }

    /**
     * @param pcNo
     *            the pcNo to set
     */
    public void setPcNo(Integer pcNo) {
        this.pcNo = pcNo;
    }

    /**
     * @return the reasonCd
     */
    public Integer getReasonCd() {
        return reasonCd;
    }

    /**
     * @param reasonCd
     *            the reasonCd to set
     */
    public void setReasonCd(Integer reasonCd) {
        this.reasonCd = reasonCd;
    }

}
