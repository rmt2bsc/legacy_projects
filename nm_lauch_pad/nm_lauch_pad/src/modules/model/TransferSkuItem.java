package modules.model;

import java.util.Date;

import com.nv.util.GeneralUtil;

/**
 * A class for a sku item related to transfers.
 * 
 * @author rterrell
 *
 */
public class TransferSkuItem extends SkuItem {
    public static final int TRANS_TYPE_SI = 1;

    public static final int TRANS_TYPE_BI = 2;

    private long tranferId;

    private int transferType;

    private String transferStatus;

    private Double retail;

    private Integer qtySent;

    private Integer qtyRecv;

    private Integer qtyReq;

    private Date sendByDate;

    /**
     * Create TransferSkuItem object
     */
    public TransferSkuItem() {
        return;
    }

    /**
     * @return the tranferId
     */
    public long getTranferId() {
        return tranferId;
    }

    /**
     * @param tranferId
     *            the tranferId to set
     */
    public void setTranferId(long tranferId) {
        this.tranferId = tranferId;
    }

    /**
     * @return the retail
     */
    public Double getRetail() {
        return retail;
    }

    /**
     * Return the Transfer retail as a numeric String formatted as
     * "$###0.00;($###0.00)".
     * 
     * @return a formatted numeric String
     */
    public String getRetailStr() {
        try {
            return GeneralUtil.formatNumber(this.retail, "###0.00;(###0.00)");
        } catch (Exception e) {
            return this.retail.toString();
        }
    }

    /**
     * @param retail
     *            the retail to set
     */
    public void setRetail(Double retail) {
        this.retail = retail;
    }

    /**
     * @return the qtySent
     */
    public Integer getQtySent() {
        return qtySent;
    }

    /**
     * @param qtySent
     *            the qtySent to set
     */
    public void setQtySent(Integer qtySent) {
        this.qtySent = qtySent;
    }

    /**
     * @return the qtyRecv
     */
    public Integer getQtyRecv() {
        return qtyRecv;
    }

    /**
     * @param qtyRecv
     *            the qtyRecv to set
     */
    public void setQtyRecv(Integer qtyRecv) {
        this.qtyRecv = qtyRecv;
    }

    /**
     * @return the qtyReq
     */
    public Integer getQtyReq() {
        return qtyReq;
    }

    /**
     * @param qtyReq
     *            the qtyReq to set
     */
    public void setQtyReq(Integer qtyReq) {
        this.qtyReq = qtyReq;
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
     * @return the sendByDateStr
     */
    public String getSendByDateStr() {
        if (this.sendByDate == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.sendByDate, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the transferType
     */
    public int getTransferType() {
        return transferType;
    }

    /**
     * @param transferType
     *            the transferType to set
     */
    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    /**
     * @return the transferStatus
     */
    public String getTransferStatus() {
        return transferStatus;
    }

    /**
     * @param transferStatus
     *            the transferStatus to set
     */
    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

}
