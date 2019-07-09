package modules.model;

import java.util.Date;

import com.nv.util.GeneralUtil;

/**
 * The model class for managing Transfer data
 * 
 * @author rterrell
 *
 */
public class Transfer extends CommonModel {

    private int tranferType;

    private long tranferId;

    private Date transferDate;

    private Long master;

    private Integer dept;

    private int fromStore;

    private Integer toStore;

    private Integer reasonCd;

    private String message;

    private Integer manifestNo;

    private Integer proBill;

    private String carrierId;

    private Date shipDate;

    private Integer noPkg;

    private Date sendByDate;

    private String pkgType;

    /**
     * 
     */
    public Transfer() {
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
     * @return the transferDate
     */
    public Date getTransferDate() {
        return transferDate;
    }

    /**
     * @param transferDate
     *            the transferDate to set
     */
    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    /**
     * @return the master
     */
    public Long getMaster() {
        return master;
    }

    /**
     * @param master
     *            the master to set
     */
    public void setMaster(Long master) {
        this.master = master;
    }

    /**
     * @return the dept
     */
    public Integer getDept() {
        return dept;
    }

    /**
     * @param dept
     *            the dept to set
     */
    public void setDept(Integer dept) {
        this.dept = dept;
    }

    /**
     * @return the fromStore
     */
    public int getFromStore() {
        return fromStore;
    }

    /**
     * @param fromStore
     *            the fromStore to set
     */
    public void setFromStore(int fromStore) {
        this.fromStore = fromStore;
    }

    /**
     * @return the toStore
     */
    public Integer getToStore() {
        return toStore;
    }

    /**
     * @param toStore
     *            the toStore to set
     */
    public void setToStore(Integer toStore) {
        this.toStore = toStore;
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

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the manifestNo
     */
    public Integer getManifestNo() {
        return manifestNo;
    }

    /**
     * @param manifestNo
     *            the manifestNo to set
     */
    public void setManifestNo(Integer manifestNo) {
        this.manifestNo = manifestNo;
    }

    /**
     * @return the proBill
     */
    public Integer getProBill() {
        return proBill;
    }

    /**
     * @param proBill
     *            the proBill to set
     */
    public void setProBill(Integer proBill) {
        this.proBill = proBill;
    }

    /**
     * @return the carrierId
     */
    public String getCarrierId() {
        return carrierId;
    }

    /**
     * @param carrierId
     *            the carrierId to set
     */
    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    /**
     * @return the shipDate
     */
    public Date getShipDate() {
        return shipDate;
    }

    /**
     * @param shipDate
     *            the shipDate to set
     */
    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    /**
     * @return the noPkg
     */
    public Integer getNoPkg() {
        return noPkg;
    }

    /**
     * @param noPkg
     *            the noPkg to set
     */
    public void setNoPkg(Integer noPkg) {
        this.noPkg = noPkg;
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
     * @return the transferDateStr
     */
    public String getTransferDateStr() {
        if (this.transferDate == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.transferDate, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
     * @return the shipDateStr
     */
    public String getShipDateStr() {
        if (this.shipDate == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.shipDate, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the tranferType
     */
    public int getTranferType() {
        return tranferType;
    }

    /**
     * @param tranferType
     *            the tranferType to set
     */
    public void setTranferType(int tranferType) {
        this.tranferType = tranferType;
    }

    /**
     * @return the pkgType
     */
    public String getPkgType() {
        return pkgType;
    }

    /**
     * @param pkgType
     *            the pkgType to set
     */
    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

}
