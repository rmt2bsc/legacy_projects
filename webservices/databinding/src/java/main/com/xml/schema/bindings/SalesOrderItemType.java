//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.23 at 11:17:17 PM CDT 
//


package com.xml.schema.bindings;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The sales order item record.
 * 
 * <p>Java class for sales_order_item_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sales_order_item_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sales_order_item_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="sales_order_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="customer_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="item_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="item_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="back_order_qty" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="markup" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="unit_cost" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="order_qty" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sales_order_item_type", propOrder = {
    "salesOrderItemId",
    "salesOrderId",
    "customerId",
    "itemId",
    "itemName",
    "backOrderQty",
    "markup",
    "unitCost",
    "orderQty"
})
public class SalesOrderItemType {

    @XmlElement(name = "sales_order_item_id", required = true, defaultValue = "0")
    protected BigInteger salesOrderItemId;
    @XmlElement(name = "sales_order_id", required = true, defaultValue = "0")
    protected BigInteger salesOrderId;
    @XmlElement(name = "customer_id", required = true)
    protected BigInteger customerId;
    @XmlElement(name = "item_id", required = true, defaultValue = "0")
    protected BigInteger itemId;
    @XmlElement(name = "item_name", required = true)
    protected String itemName;
    @XmlElement(name = "back_order_qty")
    protected BigDecimal backOrderQty;
    @XmlElement(required = true, defaultValue = "0")
    protected BigDecimal markup;
    @XmlElement(name = "unit_cost", required = true, defaultValue = "0")
    protected BigDecimal unitCost;
    @XmlElement(name = "order_qty", required = true, defaultValue = "0")
    protected BigInteger orderQty;

    /**
     * Gets the value of the salesOrderItemId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSalesOrderItemId() {
        return salesOrderItemId;
    }

    /**
     * Sets the value of the salesOrderItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSalesOrderItemId(BigInteger value) {
        this.salesOrderItemId = value;
    }

    /**
     * Gets the value of the salesOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSalesOrderId() {
        return salesOrderId;
    }

    /**
     * Sets the value of the salesOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSalesOrderId(BigInteger value) {
        this.salesOrderId = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCustomerId(BigInteger value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the itemId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getItemId() {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setItemId(BigInteger value) {
        this.itemId = value;
    }

    /**
     * Gets the value of the itemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the value of the itemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemName(String value) {
        this.itemName = value;
    }

    /**
     * Gets the value of the backOrderQty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBackOrderQty() {
        return backOrderQty;
    }

    /**
     * Sets the value of the backOrderQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBackOrderQty(BigDecimal value) {
        this.backOrderQty = value;
    }

    /**
     * Gets the value of the markup property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarkup() {
        return markup;
    }

    /**
     * Sets the value of the markup property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarkup(BigDecimal value) {
        this.markup = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUnitCost(BigDecimal value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the orderQty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOrderQty() {
        return orderQty;
    }

    /**
     * Sets the value of the orderQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOrderQty(BigInteger value) {
        this.orderQty = value;
    }

}
