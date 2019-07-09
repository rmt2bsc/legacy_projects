//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.23 at 11:17:17 PM CDT 
//


package com.xml.schema.bindings;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Describes general code details data 
 * 
 * <p>Java class for code_detail_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="code_detail_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="shortdesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="longdesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "code_detail_type", propOrder = {
    "codeId",
    "shortdesc",
    "longdesc"
})
public class CodeDetailType {

    @XmlElement(name = "code_id", required = true, defaultValue = "0")
    protected BigInteger codeId;
    @XmlElement(required = true, nillable = true)
    protected String shortdesc;
    @XmlElement(required = true, nillable = true)
    protected String longdesc;
    @XmlAttribute
    protected String label;

    /**
     * Gets the value of the codeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodeId() {
        return codeId;
    }

    /**
     * Sets the value of the codeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodeId(BigInteger value) {
        this.codeId = value;
    }

    /**
     * Gets the value of the shortdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortdesc() {
        return shortdesc;
    }

    /**
     * Sets the value of the shortdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortdesc(String value) {
        this.shortdesc = value;
    }

    /**
     * Gets the value of the longdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongdesc() {
        return longdesc;
    }

    /**
     * Sets the value of the longdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongdesc(String value) {
        this.longdesc = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

}
