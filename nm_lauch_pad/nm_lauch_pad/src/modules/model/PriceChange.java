package modules.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nv.util.GeneralUtil;

/**
 * A class representing the Price Change model
 * 
 * @author rterrell
 *
 */
public class PriceChange extends CommonModel {

    private int dept;

    private Date effDate;

    private int reasonCd;

    private String message;

    private long skuLower;

    private long skuUpper;

    private List<PriceChangeSkuItem> items;

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
     * @return the effDate
     */
    public Date getEffDate() {
        return effDate;
    }

    /**
     * @param effDate
     *            the effDate to set
     */
    public void setEffDate(Date effDate) {
        this.effDate = effDate;
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
     * @return the skuLower
     */
    public long getSkuLower() {
        return skuLower;
    }

    /**
     * @param skuLower
     *            the skuLower to set
     */
    public void setSkuLower(long skuLower) {
        this.skuLower = skuLower;
    }

    /**
     * @return the skuUpper
     */
    public long getSkuUpper() {
        return skuUpper;
    }

    /**
     * @param skuUpper
     *            the skuUpper to set
     */
    public void setSkuUpper(long skuUpper) {
        this.skuUpper = skuUpper;
    }

    /**
     * @return the items
     */
    public List<PriceChangeSkuItem> getItems() {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<PriceChangeSkuItem> items) {
        this.items = items;
    }

    public void addItem(PriceChangeSkuItem item) {
        if (item == null) {
            return;
        }
        if (this.items == null) {
            this.items = new ArrayList<PriceChangeSkuItem>();
        }
        this.items.add(item);
    }

    /**
     * @return the effDateSttr
     */
    public String getEffDateStr() {
        if (this.effDate == null) {
            return null;
        }
        try {
            return GeneralUtil.formatDate(this.effDate, "MM-dd-yyyy");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
