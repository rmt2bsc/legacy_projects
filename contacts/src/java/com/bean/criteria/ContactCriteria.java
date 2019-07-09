package com.bean.criteria;

import com.bean.criteria.AncestorQueryCriteria;

import com.util.SystemException;

/**
 * Criteria class used for tracking UI selection criteria for the Contacts
 * Search page.
 * 
 * @author RTerrell
 *
 */
public class ContactCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -9194934472559574966L;

    private String qry_ContactType;
    private String qry_PersonId;
    private String qry_PerFirstname;
    private String qry_PerMidname;
    private String qry_PerLastname;
    private String qry_PerMaidenname;
    private String qry_PerGeneration;
    private String qry_PerShortname;
    private String qry_PerTitle;
    private String qry_PerGenderId;
    private String qry_PerMaritalStatus;
    private String qry_PerBirthDate;
    private String qry_PerRaceId;
    private String qry_PerSsn;
    private String qry_PerEmail;
    private String qry_BusinessId;
    private String qry_BusEntityTypeId;
    private String qry_BusServTypeId;
    private String qry_BusLongname;
    private String qry_BusShortname;
    private String qry_BusContactFirstname;
    private String qry_BusContactLastname;
    private String qry_BusContactPhone;
    private String qry_BusContactExt;
    private String qry_BusTaxId;
    private String qry_BusWebsite;
    private String qry_AddrId;
    private String qry_Addr1;
    private String qry_Addr2;
    private String qry_Addr3;
    private String qry_Addr4;
    private String qry_AddrZip;
    private String qry_AddrZipext;
    private String qry_AddrPhoneHome;
    private String qry_AddrPhoneWork;
    private String qry_AddrPhoneExt;
    private String qry_AddrPhoneMain;
    private String qry_AddrPhoneCell;
    private String qry_AddrPhoneFax;
    private String qry_AddrPhonePager;
    private String qry_ZipCity;
    private String qry_ZipState;

    private String qry_ContactId;
    private String qry_ContactName;

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    private ContactCriteria() throws SystemException {
    	super();
        return;
    }
    
    /**
     * Creates an instance of ContactCriteria class.
     * 
     * @return ContactCriteria.
     */
    public static ContactCriteria getInstance() {
        try {
            ContactCriteria criteria = new ContactCriteria();
            return criteria;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Set the contact type
     * @param value
     */
    public void setQry_ContactType(String value) {
        this.qry_ContactType = value;
    }

    /**
     * Get the contact type
     * @return
     */
    public String getQry_ContactType() {
        return this.qry_ContactType;
    }

    /**
     * Set the person id
     * @param value
     */
    public void setQry_PersonId(String value) {
        this.qry_PersonId = value;
    }

    /**
     * Get the person id.
     * @return
     */
    public String getQry_PersonId() {
        return this.qry_PersonId;
    }

    /**
     * Set the person first name.
     * @param value
     */
    public void setQry_PerFirstname(String value) {
        this.qry_PerFirstname = value;
    }

    /**
     * Get the person first name.
     * @return
     */
    public String getQry_PerFirstname() {
        return this.qry_PerFirstname;
    }

    /**
     * Set the person middle name.
     * @param value
     */
    public void setQry_PerMidname(String value) {
        this.qry_PerMidname = value;
    }

    /**
     * Get the person middle name
     * @return
     */
    public String getQry_PerMidname() {
        return this.qry_PerMidname;
    }

    /**
     * Set the preson last name.
     * @param value
     */
    public void setQry_PerLastname(String value) {
        this.qry_PerLastname = value;
    }

    /**
     * Get the person last name.
     * @return
     */
    public String getQry_PerLastname() {
        return this.qry_PerLastname;
    }

    /**
     * Set the person maiden name.
     * @param value
     */
    public void setQry_PerMaidenname(String value) {
        this.qry_PerMaidenname = value;
    }

    /**
     * Get the person maiden name.
     * @return
     */
    public String getQry_PerMaidenname() {
        return this.qry_PerMaidenname;
    }

    /**
     * Set the person generation.
     * @param value
     */
    public void setQry_PerGeneration(String value) {
        this.qry_PerGeneration = value;
    }

    /**
     * Get the person generation.
     * @return
     */
    public String getQry_PerGeneration() {
        return this.qry_PerGeneration;
    }

    /**
     * Set the person short name
     * @param value
     */
    public void setQry_PerShortname(String value) {
        this.qry_PerShortname = value;
    }

    /**
     * Get the person short name.
     * @return
     */
    public String getQry_PerShortname() {
        return this.qry_PerShortname;
    }

    /**
     * Set the person title.
     * @param value
     */
    public void setQry_PerTitle(String value) {
        this.qry_PerTitle = value;
    }

    /**
     * Get the person title
     * @return
     */
    public String getQry_PerTitle() {
        return this.qry_PerTitle;
    }

    /**
     * Set the person gender id.
     * @param value
     */
    public void setQry_PerGenderId(String value) {
        this.qry_PerGenderId = value;
    }

    /**
     * Get the person gender id.
     * @return
     */
    public String getQry_PerGenderId() {
        return this.qry_PerGenderId;
    }

    /**
     * Set the person marital status.
     * @param value
     */
    public void setQry_PerMaritalStatus(String value) {
        this.qry_PerMaritalStatus = value;
    }

    /**
     * Get the person marital status.
     * @return
     */
    public String getQry_PerMaritalStatus() {
        return this.qry_PerMaritalStatus;
    }

    /**
     * Set the person birth date.
     * @param value
     */
    public void setQry_PerBirthDate(String value) {
        this.qry_PerBirthDate = value;
    }

    /**
     * Get the person birth date.
     * @return
     */
    public String getQry_PerBirthDate() {
        return this.qry_PerBirthDate;
    }

    /**
     * Set the person race id.
     * @param value
     */
    public void setQry_PerRaceId(String value) {
        this.qry_PerRaceId = value;
    }

    /**
     * Get the person race id.
     * @return
     */
    public String getQry_PerRaceId() {
        return this.qry_PerRaceId;
    }

    /**
     * Set the person Social Security Number.
     * @param value
     */
    public void setQry_PerSsn(String value) {
        this.qry_PerSsn = value;
    }

    /**
     * Get the person social security number.
     * @return
     */
    public String getQry_PerSsn() {
        return this.qry_PerSsn;
    }

    /**
     * Set the person email
     * @param value
     */
    public void setQry_PerEmail(String value) {
        this.qry_PerEmail = value;
    }

    /**
     * Get the person email.
     * @return
     */
    public String getQry_PerEmail() {
        return this.qry_PerEmail;
    }

    /**
     * Set the business Id.
     * @param value
     */
    public void setQry_BusinessId(String value) {
        this.qry_BusinessId = value;
    }

    /**
     * Get the business id.
     * @return
     */
    public String getQry_BusinessId() {
        return this.qry_BusinessId;
    }

    /**
     * Set the business type.
     * @param value
     */
    public void setQry_BusEntityTypeId(String value) {
        this.qry_BusEntityTypeId = value;
    }

    /**
     * Get the business type.
     * @return
     */
    public String getQry_BusEntityTypeId() {
        return this.qry_BusEntityTypeId;
    }

    /**
     * Set the business service type.
     * @param value
     */
    public void setQry_BusServTypeId(String value) {
        this.qry_BusServTypeId = value;
    }

    /**
     * Get the business service type.
     * @return
     */
    public String getQry_BusServTypeId() {
        return this.qry_BusServTypeId;
    }

    /**
     * Set the business long name.
     * @param value
     */
    public void setQry_BusLongname(String value) {
        this.qry_BusLongname = value;
    }

    /**
     * Get the business long name.
     * @return
     */
    public String getQry_BusLongname() {
        return this.qry_BusLongname;
    }

    /**
     * Set the business short name.
     * @param value
     */
    public void setQry_BusShortname(String value) {
        this.qry_BusShortname = value;
    }

    /**
     * Get the business short name.
     * @return
     */
    public String getQry_BusShortname() {
        return this.qry_BusShortname;
    }

    /**
     * Set the business first name.
     * @param value
     */
    public void setQry_BusContactFirstname(String value) {
        this.qry_BusContactFirstname = value;
    }

    /**
     * Get the business first name.
     * @return
     */
    public String getQry_BusContactFirstname() {
        return this.qry_BusContactFirstname;
    }

    /**
     * Set the business last name.
     * @param value
     */
    public void setQry_BusContactLastname(String value) {
        this.qry_BusContactLastname = value;
    }

    /**
     * Get the business last name.
     * @return
     */
    public String getQry_BusContactLastname() {
        return this.qry_BusContactLastname;
    }

    /**
     * Set the business contact phone.
     * @param value
     */
    public void setQry_BusContactPhone(String value) {
        this.qry_BusContactPhone = value;
    }

    /**
     * Get the business contact phone.
     * @return
     */
    public String getQry_BusContactPhone() {
        return this.qry_BusContactPhone;
    }

    /**
     * Set the business contact phone extension.
     * @param value
     */
    public void setQry_BusContactExt(String value) {
        this.qry_BusContactExt = value;
    }

    /**
     * Get the business coontact phone extension.
     * @return
     */
    public String getQry_BusContactExt() {
        return this.qry_BusContactExt;
    }

    /**
     * Set the business tax id.
     * @param value
     */
    public void setQry_BusTaxId(String value) {
        this.qry_BusTaxId = value;
    }

    /**
     * Get the business tax id.
     * @return
     */
    public String getQry_BusTaxId() {
        return this.qry_BusTaxId;
    }

    /**
     * Set the business web site.
     * @param value
     */
    public void setQry_BusWebsite(String value) {
        this.qry_BusWebsite = value;
    }

    /**
     * Get the business web site
     * @return
     */
    public String getQry_BusWebsite() {
        return this.qry_BusWebsite;
    }

    /**
     * Set address id
     * @param value
     */
    public void setQry_AddrId(String value) {
        this.qry_AddrId = value;
    }

    /**
     * Get address id
     * @return
     */
    public String getQry_AddrId() {
        return this.qry_AddrId;
    }

    /**
     * Set address line 1.
     * @param value
     */
    public void setQry_Addr1(String value) {
        this.qry_Addr1 = value;
    }

    /**
     * Get address line 1.
     * @return
     */
    public String getQry_Addr1() {
        return this.qry_Addr1;
    }

    /**
     * Set address line 2.
     * @param value
     */
    public void setQry_Addr2(String value) {
        this.qry_Addr2 = value;
    }

    /**
     * Get address line 2.
     * @return
     */
    public String getQry_Addr2() {
        return this.qry_Addr2;
    }

    /**
     * Set address line 3.
     * @param value
     */
    public void setQry_Addr3(String value) {
        this.qry_Addr3 = value;
    }

    /**
     * Get address line 3.
     * @return
     */
    public String getQry_Addr3() {
        return this.qry_Addr3;
    }

    /**
     * Set address line 4.
     * @param value
     */
    public void setQry_Addr4(String value) {
        this.qry_Addr4 = value;
    }

    /**
     * Get address line 4.
     * @return
     */
    public String getQry_Addr4() {
        return this.qry_Addr4;
    }

    /**
     * Set address zip code.
     * @param value
     */
    public void setQry_AddrZip(String value) {
        this.qry_AddrZip = value;
    }

    /**
     * Get address zip code.
     * @return
     */
    public String getQry_AddrZip() {
        return this.qry_AddrZip;
    }

    /**
     * Set address zip code extension.
     * @param value
     */
    public void setQry_AddrZipext(String value) {
        this.qry_AddrZipext = value;
    }

    /**
     * Get address zip code extension.
     * @return
     */
    public String getQry_AddrZipext() {
        return this.qry_AddrZipext;
    }

    /**
     * Set address home phone number.
     * @param value
     */
    public void setQry_AddrPhoneHome(String value) {
        this.qry_AddrPhoneHome = value;
    }

    /**
     * Get address home phone number.
     * @return
     */
    public String getQry_AddrPhoneHome() {
        return this.qry_AddrPhoneHome;
    }

    /**
     * Set address work number.
     * @param value
     */
    public void setQry_AddrPhoneWork(String value) {
        this.qry_AddrPhoneWork = value;
    }

    /**
     * Get address work number.
     * @return
     */
    public String getQry_AddrPhoneWork() {
        return this.qry_AddrPhoneWork;
    }

    /**
     * Set address work number extension.
     * @param value
     */
    public void setQry_AddrPhoneExt(String value) {
        this.qry_AddrPhoneExt = value;
    }

    /**
     * Get address work number extension.
     * @return
     */
    public String getQry_AddrPhoneExt() {
        return this.qry_AddrPhoneExt;
    }

    /**
     * Set address main phone number 
     * @param value
     */
    public void setQry_AddrPhoneMain(String value) {
        this.qry_AddrPhoneMain = value;
    }

    /**
     * Get address main phone number.
     * @return
     */
    public String getQry_AddrPhoneMain() {
        return this.qry_AddrPhoneMain;
    }

    /**
     * Set address cell phone
     * @param value
     */
    public void setQry_AddrPhoneCell(String value) {
        this.qry_AddrPhoneCell = value;
    }

    /**
     * Get address cell phone.
     * @return
     */
    public String getQry_AddrPhoneCell() {
        return this.qry_AddrPhoneCell;
    }

    /**
     * Set address fax.
     * @param value
     */
    public void setQry_AddrPhoneFax(String value) {
        this.qry_AddrPhoneFax = value;
    }

    /**
     * Get address fax.
     * @return
     */
    public String getQry_AddrPhoneFax() {
        return this.qry_AddrPhoneFax;
    }

    /**
     * Set address page number.
     * @param value
     */
    public void setQry_AddrPhonePager(String value) {
        this.qry_AddrPhonePager = value;
    }

    /**
     * Get address pager number.
     * @return
     */
    public String getQry_AddrPhonePager() {
        return this.qry_AddrPhonePager;
    }

    /**
     * Set address city.
     * @param value
     */
    public void setQry_ZipCity(String value) {
        this.qry_ZipCity = value;
    }

    /**
     * Get address city.
     * @return
     */
    public String getQry_ZipCity() {
        return this.qry_ZipCity;
    }

    /**
     * Set address state.
     * @param value
     */
    public void setQry_ZipState(String value) {
        this.qry_ZipState = value;
    }

    /**
     * Get address state.
     * @return
     */
    public String getQry_ZipState() {
        return this.qry_ZipState;
    }

    /**
     * Initialize all member varialbes.
     */
    public void initBean() throws SystemException {
    	 this.qry_ContactType = "";
         this.qry_PerFirstname = "";
         this.qry_PerMidname = "";
         this.qry_PerLastname = "";
         this.qry_PerMaidenname = "";
         this.qry_PerGeneration = "";
         this.qry_PerShortname = "";
         this.qry_PerBirthDate = "";
         this.qry_PerSsn = "";
         this.qry_PerEmail = "";
         this.qry_BusLongname = "";
         this.qry_BusShortname = "";
         this.qry_BusContactFirstname = "";
         this.qry_BusContactLastname = "";
         this.qry_BusContactPhone = "";
         this.qry_BusContactExt = "";
         this.qry_BusTaxId = "";
         this.qry_BusWebsite = "";
         this.qry_Addr1 = "";
         this.qry_Addr2 = "";
         this.qry_Addr3 = "";
         this.qry_Addr4 = "";
         this.qry_AddrZipext = "";
         this.qry_AddrPhoneHome = "";
         this.qry_AddrPhoneWork = "";
         this.qry_AddrPhoneExt = "";
         this.qry_AddrPhoneMain = "";
         this.qry_AddrPhoneCell = "";
         this.qry_AddrPhoneFax = "";
         this.qry_AddrPhonePager = "";
         this.qry_ZipCity = "";
         this.qry_ZipState = "";
         this.qry_PersonId = "";
         this.qry_PerTitle = "";
         this.qry_PerGenderId = "";
         this.qry_PerMaritalStatus = "";
         this.qry_PerRaceId = "";
         this.qry_BusinessId = "";
         this.qry_BusEntityTypeId = "";
         this.qry_BusServTypeId = "";
         this.qry_AddrId = "";
         this.qry_AddrZip = "";
         this.qry_ContactId = null;
         this.qry_ContactName = null;
    }

    /**
     * @return the qry_ContactId
     */
    public String getQry_ContactId() {
        return qry_ContactId;
    }

    /**
     * @param qry_ContactId the qry_ContactId to set
     */
    public void setQry_ContactId(String qry_ContactId) {
        this.qry_ContactId = qry_ContactId;
    }

    /**
     * @return the qry_ContactName
     */
    public String getQry_ContactName() {
        return qry_ContactName;
    }

    /**
     * @param qry_ContactName the qry_ContactName to set
     */
    public void setQry_ContactName(String qry_ContactName) {
        this.qry_ContactName = qry_ContactName;
    }
}