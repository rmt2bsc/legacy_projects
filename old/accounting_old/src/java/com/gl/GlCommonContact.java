package com.gl;

import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * Common General Ledger contact class
 *
 * @author Roy Terrell
 */
public class GlCommonContact extends RMT2BaseBean {

    private static final long serialVersionUID = 8399441538996661782L;

    private int businessId;

    private String description;

    private String name;

    private String shortname;

    private int servType;

    private int busType;

    private String contactFirstname;

    private String contactLastname;

    private String contactPhone;

    private String contactExt;

    private String contactEmail;

    private String taxId;

    private String website;

    private double balance;

    private int addrId;

    private String addrPhoneCell;

    private String addrPhoneExt;

    private String addrPhoneFax;

    private String addrPhoneHome;

    private String addrPhoneMain;

    private String addrPhonePager;

    private String addrPhoneWork;

    private int addrZip;

    private int addrZipext;

    private String addr1;

    private String addr2;

    private String addr3;

    private String addr4;

    private int addrPersonId;

    private int addrBusinessId;

    private String zipCity;

    private String zipState;

    // Getter/Setter Methods

    /**
     * Default constructor.
     *
     * @author auto generated.
     */
    public GlCommonContact() throws SystemException {
	super();
    }

   
    /**
     * Sets the value of member variable businessId
     *
     * @author auto generated.
     */
    public void setBusinessId(int value) {
	this.businessId = value;
    }

    /**
     * Gets the value of member variable businessId
     *
     * @author atuo generated.
     */
    public int getBusinessId() {
	return this.businessId;
    }

    /**
     * Sets the value of member variable name
     *
     * @author auto generated.
     */
    public void setName(String value) {
	this.name = value;
    }

    /**
     * Gets the value of member variable name
     *
     * @author atuo generated.
     */
    public String getName() {
	return this.name;
    }

    /**
     * Sets the value of member variable shortname
     *
     * @author auto generated.
     */
    public void setShortname(String value) {
	this.shortname = value;
    }

    /**
     * Gets the value of member variable shortname
     *
     * @author atuo generated.
     */
    public String getShortname() {
	return this.shortname;
    }

    /**
     * Sets the value of member variable servType
     *
     * @author auto generated.
     */
    public void setServType(int value) {
	this.servType = value;
    }

    /**
     * Gets the value of member variable servType
     *
     * @author atuo generated.
     */
    public int getServType() {
	return this.servType;
    }

    /**
     * Sets the value of member variable busType
     *
     * @author auto generated.
     */
    public void setBusType(int value) {
	this.busType = value;
    }

    /**
     * Gets the value of member variable busType
     *
     * @author atuo generated.
     */
    public int getBusType() {
	return this.busType;
    }

    /**
     * Sets the value of member variable contactFirstname
     *
     * @author auto generated.
     */
    public void setContactFirstname(String value) {
	this.contactFirstname = value;
    }

    /**
     * Gets the value of member variable contactFirstname
     *
     * @author atuo generated.
     */
    public String getContactFirstname() {
	return this.contactFirstname;
    }

    /**
     * Sets the value of member variable contactLastname
     *
     * @author auto generated.
     */
    public void setContactLastname(String value) {
	this.contactLastname = value;
    }

    /**
     * Gets the value of member variable contactLastname
     *
     * @author atuo generated.
     */
    public String getContactLastname() {
	return this.contactLastname;
    }

    /**
     * Sets the value of member variable contactPhone
     *
     * @author auto generated.
     */
    public void setContactPhone(String value) {
	this.contactPhone = value;
    }

    /**
     * Gets the value of member variable contactPhone
     *
     * @author atuo generated.
     */
    public String getContactPhone() {
	return this.contactPhone;
    }

    /**
     * Sets the value of member variable contactExt
     *
     * @author auto generated.
     */
    public void setContactExt(String value) {
	this.contactExt = value;
    }

    /**
     * Gets the value of member variable contactExt
     *
     * @author atuo generated.
     */
    public String getContactExt() {
	return this.contactExt;
    }

    /**
     * Sets the value of member variable taxId
     *
     * @author auto generated.
     */
    public void setTaxId(String value) {
	this.taxId = value;
    }

    /**
     * Gets the value of member variable taxId
     *
     * @author atuo generated.
     */
    public String getTaxId() {
	return this.taxId;
    }

    /**
     * Sets the value of member variable website
     *
     * @author auto generated.
     */
    public void setWebsite(String value) {
	this.website = value;
    }

    /**
     * Gets the value of member variable website
     *
     * @author atuo generated.
     */
    public String getWebsite() {
	return this.website;
    }

    /**
     * Sets the value of member variable balance
     *
     * @author auto generated.
     */
    public void setBalance(double value) {
	this.balance = value;
    }

    /**
     * Gets the value of member variable balance
     *
     * @author atuo generated.
     */
    public double getBalance() {
	return this.balance;
    }

    /**
     * Stubbed initialization method designed to implemented by developer.

     *
     * @author auto generated.
     */
    public void initBean() throws SystemException {
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
	return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }

    /**
     * @return the addrId
     */
    public int getAddrId() {
        return addrId;
    }

    /**
     * @param addrId the addrId to set
     */
    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    /**
     * @return the addrPhoneCell
     */
    public String getAddrPhoneCell() {
        return addrPhoneCell;
    }

    /**
     * @param addrPhoneCell the addrPhoneCell to set
     */
    public void setAddrPhoneCell(String addrPhoneCell) {
        this.addrPhoneCell = addrPhoneCell;
    }

    /**
     * @return the addrPhoneExt
     */
    public String getAddrPhoneExt() {
        return addrPhoneExt;
    }

    /**
     * @param addrPhoneExt the addrPhoneExt to set
     */
    public void setAddrPhoneExt(String addrPhoneExt) {
        this.addrPhoneExt = addrPhoneExt;
    }

    /**
     * @return the addrPhoneFax
     */
    public String getAddrPhoneFax() {
        return addrPhoneFax;
    }

    /**
     * @param addrPhoneFax the addrPhoneFax to set
     */
    public void setAddrPhoneFax(String addrPhoneFax) {
        this.addrPhoneFax = addrPhoneFax;
    }

    /**
     * @return the addrPhoneHome
     */
    public String getAddrPhoneHome() {
        return addrPhoneHome;
    }

    /**
     * @param addrPhoneHome the addrPhoneHome to set
     */
    public void setAddrPhoneHome(String addrPhoneHome) {
        this.addrPhoneHome = addrPhoneHome;
    }

    /**
     * @return the addrPhoneMain
     */
    public String getAddrPhoneMain() {
        return addrPhoneMain;
    }

    /**
     * @param addrPhoneMain the addrPhoneMain to set
     */
    public void setAddrPhoneMain(String addrPhoneMain) {
        this.addrPhoneMain = addrPhoneMain;
    }

    /**
     * @return the addrPhonePager
     */
    public String getAddrPhonePager() {
        return addrPhonePager;
    }

    /**
     * @param addrPhonePager the addrPhonePager to set
     */
    public void setAddrPhonePager(String addrPhonePager) {
        this.addrPhonePager = addrPhonePager;
    }

    /**
     * @return the addrPhoneWork
     */
    public String getAddrPhoneWork() {
        return addrPhoneWork;
    }

    /**
     * @param addrPhoneWork the addrPhoneWork to set
     */
    public void setAddrPhoneWork(String addrPhoneWork) {
        this.addrPhoneWork = addrPhoneWork;
    }

    /**
     * @return the addrZip
     */
    public int getAddrZip() {
        return addrZip;
    }

    /**
     * @param addrZip the addrZip to set
     */
    public void setAddrZip(int addrZip) {
        this.addrZip = addrZip;
    }

    /**
     * @return the addrZipext
     */
    public int getAddrZipext() {
        return addrZipext;
    }

    /**
     * @param addrZipext the addrZipext to set
     */
    public void setAddrZipext(int addrZipext) {
        this.addrZipext = addrZipext;
    }

    /**
     * @return the addr1
     */
    public String getAddr1() {
        return addr1;
    }

    /**
     * @param addr1 the addr1 to set
     */
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    /**
     * @return the addr2
     */
    public String getAddr2() {
        return addr2;
    }

    /**
     * @param addr2 the addr2 to set
     */
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    /**
     * @return the addr3
     */
    public String getAddr3() {
        return addr3;
    }

    /**
     * @param addr3 the addr3 to set
     */
    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    /**
     * @return the addr4
     */
    public String getAddr4() {
        return addr4;
    }

    /**
     * @param addr4 the addr4 to set
     */
    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    /**
     * @return the addrPersonId
     */
    public int getAddrPersonId() {
        return addrPersonId;
    }

    /**
     * @param addrPersonId the addrPersonId to set
     */
    public void setAddrPersonId(int addrPersonId) {
        this.addrPersonId = addrPersonId;
    }

    /**
     * @return the addrBusinessId
     */
    public int getAddrBusinessId() {
        return addrBusinessId;
    }

    /**
     * @param addrBusinessId the addrBusinessId to set
     */
    public void setAddrBusinessId(int addrBusinessId) {
        this.addrBusinessId = addrBusinessId;
    }

    /**
     * @return the zipCity
     */
    public String getZipCity() {
        return zipCity;
    }

    /**
     * @param zipCity the zipCity to set
     */
    public void setZipCity(String zipCity) {
        this.zipCity = zipCity;
    }

    /**
     * @return the zipState
     */
    public String getZipState() {
        return zipState;
    }

    /**
     * @param zipState the zipState to set
     */
    public void setZipState(String zipState) {
        this.zipState = zipState;
    }

}