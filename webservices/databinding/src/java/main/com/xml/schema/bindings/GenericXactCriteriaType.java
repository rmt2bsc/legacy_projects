//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.23 at 11:17:17 PM CDT 
//


package com.xml.schema.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generic_xact_criteria_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generic_xact_criteria_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="qry_Id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactAccountNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactAmount_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactAmount_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactDate_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactDate_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactReason_ADVSRCHOPTS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactSubtypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_XactTypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_AccountNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_BusinessName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_ConfirmNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_InvoiceNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_ItemAmount_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_ItemAmount_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qryRelOp_ItemAmount_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qryRelOp_ItemAmount_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qryRelOp_XactAmount_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qryRelOp_XactAmount_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qryRelOp_XactDate_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qryRelOp_XactDate_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_DateCreated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_DateUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qry_UserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generic_xact_criteria_type", propOrder = {
    "qryId",
    "qryXactId",
    "qryXactAccountNo",
    "qryXactAmount1",
    "qryXactAmount2",
    "qryXactDate1",
    "qryXactDate2",
    "qryXactReason",
    "qryXactReasonADVSRCHOPTS",
    "qryXactSubtypeId",
    "qryXactTypeId",
    "qryAccountNo",
    "qryBusinessName",
    "qryConfirmNo",
    "qryInvoiceNo",
    "qryItemAmount1",
    "qryItemAmount2",
    "qryRelOpItemAmount1",
    "qryRelOpItemAmount2",
    "qryRelOpXactAmount1",
    "qryRelOpXactAmount2",
    "qryRelOpXactDate1",
    "qryRelOpXactDate2",
    "qryDateCreated",
    "qryDateUpdated",
    "qryUserId"
})
public class GenericXactCriteriaType {

    @XmlElement(name = "qry_Id", required = true)
    protected String qryId;
    @XmlElement(name = "qry_XactId", required = true)
    protected String qryXactId;
    @XmlElement(name = "qry_XactAccountNo", required = true)
    protected String qryXactAccountNo;
    @XmlElement(name = "qry_XactAmount_1", required = true)
    protected String qryXactAmount1;
    @XmlElement(name = "qry_XactAmount_2", required = true)
    protected String qryXactAmount2;
    @XmlElement(name = "qry_XactDate_1", required = true)
    protected String qryXactDate1;
    @XmlElement(name = "qry_XactDate_2", required = true)
    protected String qryXactDate2;
    @XmlElement(name = "qry_XactReason", required = true)
    protected String qryXactReason;
    @XmlElement(name = "qry_XactReason_ADVSRCHOPTS", required = true)
    protected String qryXactReasonADVSRCHOPTS;
    @XmlElement(name = "qry_XactSubtypeId", required = true)
    protected String qryXactSubtypeId;
    @XmlElement(name = "qry_XactTypeId", required = true)
    protected String qryXactTypeId;
    @XmlElement(name = "qry_AccountNo", required = true)
    protected String qryAccountNo;
    @XmlElement(name = "qry_BusinessName", required = true)
    protected String qryBusinessName;
    @XmlElement(name = "qry_ConfirmNo", required = true)
    protected String qryConfirmNo;
    @XmlElement(name = "qry_InvoiceNo", required = true)
    protected String qryInvoiceNo;
    @XmlElement(name = "qry_ItemAmount_1", required = true)
    protected String qryItemAmount1;
    @XmlElement(name = "qry_ItemAmount_2", required = true)
    protected String qryItemAmount2;
    @XmlElement(name = "qryRelOp_ItemAmount_1", required = true)
    protected String qryRelOpItemAmount1;
    @XmlElement(name = "qryRelOp_ItemAmount_2", required = true)
    protected String qryRelOpItemAmount2;
    @XmlElement(name = "qryRelOp_XactAmount_1", required = true)
    protected String qryRelOpXactAmount1;
    @XmlElement(name = "qryRelOp_XactAmount_2", required = true)
    protected String qryRelOpXactAmount2;
    @XmlElement(name = "qryRelOp_XactDate_1", required = true)
    protected String qryRelOpXactDate1;
    @XmlElement(name = "qryRelOp_XactDate_2", required = true)
    protected String qryRelOpXactDate2;
    @XmlElement(name = "qry_DateCreated", required = true)
    protected String qryDateCreated;
    @XmlElement(name = "qry_DateUpdated", required = true)
    protected String qryDateUpdated;
    @XmlElement(name = "qry_UserId", required = true)
    protected String qryUserId;

    /**
     * Gets the value of the qryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryId() {
        return qryId;
    }

    /**
     * Sets the value of the qryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryId(String value) {
        this.qryId = value;
    }

    /**
     * Gets the value of the qryXactId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactId() {
        return qryXactId;
    }

    /**
     * Sets the value of the qryXactId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactId(String value) {
        this.qryXactId = value;
    }

    /**
     * Gets the value of the qryXactAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactAccountNo() {
        return qryXactAccountNo;
    }

    /**
     * Sets the value of the qryXactAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactAccountNo(String value) {
        this.qryXactAccountNo = value;
    }

    /**
     * Gets the value of the qryXactAmount1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactAmount1() {
        return qryXactAmount1;
    }

    /**
     * Sets the value of the qryXactAmount1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactAmount1(String value) {
        this.qryXactAmount1 = value;
    }

    /**
     * Gets the value of the qryXactAmount2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactAmount2() {
        return qryXactAmount2;
    }

    /**
     * Sets the value of the qryXactAmount2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactAmount2(String value) {
        this.qryXactAmount2 = value;
    }

    /**
     * Gets the value of the qryXactDate1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactDate1() {
        return qryXactDate1;
    }

    /**
     * Sets the value of the qryXactDate1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactDate1(String value) {
        this.qryXactDate1 = value;
    }

    /**
     * Gets the value of the qryXactDate2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactDate2() {
        return qryXactDate2;
    }

    /**
     * Sets the value of the qryXactDate2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactDate2(String value) {
        this.qryXactDate2 = value;
    }

    /**
     * Gets the value of the qryXactReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactReason() {
        return qryXactReason;
    }

    /**
     * Sets the value of the qryXactReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactReason(String value) {
        this.qryXactReason = value;
    }

    /**
     * Gets the value of the qryXactReasonADVSRCHOPTS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactReasonADVSRCHOPTS() {
        return qryXactReasonADVSRCHOPTS;
    }

    /**
     * Sets the value of the qryXactReasonADVSRCHOPTS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactReasonADVSRCHOPTS(String value) {
        this.qryXactReasonADVSRCHOPTS = value;
    }

    /**
     * Gets the value of the qryXactSubtypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactSubtypeId() {
        return qryXactSubtypeId;
    }

    /**
     * Sets the value of the qryXactSubtypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactSubtypeId(String value) {
        this.qryXactSubtypeId = value;
    }

    /**
     * Gets the value of the qryXactTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryXactTypeId() {
        return qryXactTypeId;
    }

    /**
     * Sets the value of the qryXactTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryXactTypeId(String value) {
        this.qryXactTypeId = value;
    }

    /**
     * Gets the value of the qryAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryAccountNo() {
        return qryAccountNo;
    }

    /**
     * Sets the value of the qryAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryAccountNo(String value) {
        this.qryAccountNo = value;
    }

    /**
     * Gets the value of the qryBusinessName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryBusinessName() {
        return qryBusinessName;
    }

    /**
     * Sets the value of the qryBusinessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryBusinessName(String value) {
        this.qryBusinessName = value;
    }

    /**
     * Gets the value of the qryConfirmNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryConfirmNo() {
        return qryConfirmNo;
    }

    /**
     * Sets the value of the qryConfirmNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryConfirmNo(String value) {
        this.qryConfirmNo = value;
    }

    /**
     * Gets the value of the qryInvoiceNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryInvoiceNo() {
        return qryInvoiceNo;
    }

    /**
     * Sets the value of the qryInvoiceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryInvoiceNo(String value) {
        this.qryInvoiceNo = value;
    }

    /**
     * Gets the value of the qryItemAmount1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryItemAmount1() {
        return qryItemAmount1;
    }

    /**
     * Sets the value of the qryItemAmount1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryItemAmount1(String value) {
        this.qryItemAmount1 = value;
    }

    /**
     * Gets the value of the qryItemAmount2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryItemAmount2() {
        return qryItemAmount2;
    }

    /**
     * Sets the value of the qryItemAmount2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryItemAmount2(String value) {
        this.qryItemAmount2 = value;
    }

    /**
     * Gets the value of the qryRelOpItemAmount1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryRelOpItemAmount1() {
        return qryRelOpItemAmount1;
    }

    /**
     * Sets the value of the qryRelOpItemAmount1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryRelOpItemAmount1(String value) {
        this.qryRelOpItemAmount1 = value;
    }

    /**
     * Gets the value of the qryRelOpItemAmount2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryRelOpItemAmount2() {
        return qryRelOpItemAmount2;
    }

    /**
     * Sets the value of the qryRelOpItemAmount2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryRelOpItemAmount2(String value) {
        this.qryRelOpItemAmount2 = value;
    }

    /**
     * Gets the value of the qryRelOpXactAmount1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryRelOpXactAmount1() {
        return qryRelOpXactAmount1;
    }

    /**
     * Sets the value of the qryRelOpXactAmount1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryRelOpXactAmount1(String value) {
        this.qryRelOpXactAmount1 = value;
    }

    /**
     * Gets the value of the qryRelOpXactAmount2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryRelOpXactAmount2() {
        return qryRelOpXactAmount2;
    }

    /**
     * Sets the value of the qryRelOpXactAmount2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryRelOpXactAmount2(String value) {
        this.qryRelOpXactAmount2 = value;
    }

    /**
     * Gets the value of the qryRelOpXactDate1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryRelOpXactDate1() {
        return qryRelOpXactDate1;
    }

    /**
     * Sets the value of the qryRelOpXactDate1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryRelOpXactDate1(String value) {
        this.qryRelOpXactDate1 = value;
    }

    /**
     * Gets the value of the qryRelOpXactDate2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryRelOpXactDate2() {
        return qryRelOpXactDate2;
    }

    /**
     * Sets the value of the qryRelOpXactDate2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryRelOpXactDate2(String value) {
        this.qryRelOpXactDate2 = value;
    }

    /**
     * Gets the value of the qryDateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryDateCreated() {
        return qryDateCreated;
    }

    /**
     * Sets the value of the qryDateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryDateCreated(String value) {
        this.qryDateCreated = value;
    }

    /**
     * Gets the value of the qryDateUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryDateUpdated() {
        return qryDateUpdated;
    }

    /**
     * Sets the value of the qryDateUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryDateUpdated(String value) {
        this.qryDateUpdated = value;
    }

    /**
     * Gets the value of the qryUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQryUserId() {
        return qryUserId;
    }

    /**
     * Sets the value of the qryUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQryUserId(String value) {
        this.qryUserId = value;
    }

}