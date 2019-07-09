package modules.model;

import com.nv.util.GeneralUtil;

/**
 * A model class for price change sku item.
 * 
 * @author rterrell
 *
 */
public class PriceChangeSkuItem extends SkuItem {

    private int pcNo;

    private double oldRetail;

    private double newRetail;

    private int markQty;

    /**
     * Create PriceChangeSkuItem object.
     */
    public PriceChangeSkuItem() {
        return;
    }

    /**
     * @return the pcNo
     */
    public int getPcNo() {
        return pcNo;
    }

    /**
     * @param pcNo
     *            the pcNo to set
     */
    public void setPcNo(int pcNo) {
        this.pcNo = pcNo;
    }

    /**
     * @return the oldRetail
     */
    public double getOldRetail() {
        return oldRetail;
    }

    /**
     * @param oldRetail
     *            the oldRetail to set
     */
    public void setOldRetail(double oldRetail) {
        this.oldRetail = oldRetail;
    }

    /**
     * @return the newRetail
     */
    public double getNewRetail() {
        return newRetail;
    }

    /**
     * @param newRetail
     *            the newRetail to set
     */
    public void setNewRetail(double newRetail) {
        this.newRetail = newRetail;
    }

    /**
     * @return the markQty
     */
    public int getMarkQty() {
        return markQty;
    }

    /**
     * @param markQty
     *            the markQty to set
     */
    public void setMarkQty(int markQty) {
        this.markQty = markQty;
    }

    /**
     * @return the oldRetailStr
     */
    public String getOldRetailStr() {
        try {
            return GeneralUtil
                    .formatNumber(this.oldRetail, "###0.00;(###0.00)");
        } catch (Exception e) {
            return String.valueOf(this.oldRetail);
        }
    }

    /**
     * @return the newRetailStr
     */
    public String getNewRetailStr() {
        try {
            return GeneralUtil
                    .formatNumber(this.newRetail, "###0.00;(###0.00)");
        } catch (Exception e) {
            return String.valueOf(this.newRetail);
        }
    }

}
