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
 * <p>Java class for ip_criteria_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ip_criteria_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ip_standard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ip_network" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ip_criteria_type", propOrder = {
    "ipStandard",
    "ipNetwork"
})
public class IpCriteriaType {

    @XmlElement(name = "ip_standard")
    protected String ipStandard;
    @XmlElement(name = "ip_network")
    protected Long ipNetwork;

    /**
     * Gets the value of the ipStandard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpStandard() {
        return ipStandard;
    }

    /**
     * Sets the value of the ipStandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpStandard(String value) {
        this.ipStandard = value;
    }

    /**
     * Gets the value of the ipNetwork property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIpNetwork() {
        return ipNetwork;
    }

    /**
     * Sets the value of the ipNetwork property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIpNetwork(Long value) {
        this.ipNetwork = value;
    }

}
