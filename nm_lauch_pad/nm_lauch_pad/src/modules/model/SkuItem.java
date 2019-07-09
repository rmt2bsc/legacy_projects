package modules.model;

import com.nv.util.GeneralUtil;

/**
 * A class representing Common SKU Item as a model.
 * 
 * @author rterrell
 *
 */
public class SkuItem extends CommonModel {

    private Long sSku;

    private Long overFlowSku;

    private Long sku;

    private String skuType;

    private Integer qty;

    private Integer dept;

    private Integer clazz;

    private Integer vendor;

    private Integer style;

    private Integer color;

    private Integer size;

    private Double price;

    /**
     * @return the sSku
     */
    public Long getsSku() {
        return sSku;
    }

    /**
     * @param sSku
     *            the sSku to set
     */
    public void setsSku(Long sSku) {
        this.sSku = sSku;
    }

    /**
     * @return the overFlowSku
     */
    public Long getOverFlowSku() {
        return overFlowSku;
    }

    /**
     * @param overFlowSku
     *            the overFlowSku to set
     */
    public void setOverFlowSku(Long overFlowSku) {
        this.overFlowSku = overFlowSku;
    }

    /**
     * @return the sku
     */
    public Long getSku() {
        return sku;
    }

    /**
     * @param sku
     *            the sku to set
     */
    public void setSku(Long sku) {
        this.sku = sku;
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
     * @return the vendor
     */
    public Integer getVendor() {
        return vendor;
    }

    /**
     * @param vendor
     *            the vendor to set
     */
    public void setVendor(Integer vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the style
     */
    public Integer getStyle() {
        return style;
    }

    /**
     * @param style
     *            the style to set
     */
    public void setStyle(Integer style) {
        this.style = style;
    }

    /**
     * @return the color
     */
    public Integer getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(Integer color) {
        this.color = color;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return the clazz
     */
    public Integer getClazz() {
        return clazz;
    }

    /**
     * @param clazz
     *            the clazz to set
     */
    public void setClazz(Integer clazz) {
        this.clazz = clazz;
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
     * @return the skuType
     */
    public String getSkuType() {
        return skuType;
    }

    /**
     * @param skuType
     *            the skuType to set
     */
    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Return the Price as a numeric String formatted as "$###0.00;($###0.00)".
     * 
     * @return a formatted numeric String
     */
    public String getPriceStr() {
        try {
            return GeneralUtil.formatNumber(this.price, "###0.00;(###0.00)");
        } catch (Exception e) {
            return this.price.toString();
        }
    }

    /**
     * Returns the Long sku by combining department, class, vendor, style,
     * color, and size values where each value is separated by a space.
     * 
     * @return Long SKU as a String.
     */
    public String getLongSku() {
        return this.dept.toString() + " " + this.clazz.toString() + " "
                + this.vendor.toString() + " " + this.style.toString() + " "
                + this.color.toString() + " " + this.size.toString();
    }
}
