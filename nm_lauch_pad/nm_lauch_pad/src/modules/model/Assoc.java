/**
 * 
 */
package modules.model;

import java.util.Date;

/**
 * The model object representing an Associate.
 * 
 * @author rterrell
 *
 */
public class Assoc {
    private int id;

    private int newId;

    private String lastName;

    private String firstName;

    private String midInit;

    private String password;

    private int securityLevel;

    private Date lastLogin;

    private Date lastChange;

    private String assocType;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the newId
     */
    public int getNewId() {
        return newId;
    }

    /**
     * @param newId
     *            the newId to set
     */
    public void setNewId(int newId) {
        this.newId = newId;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the midInit
     */
    public String getMidInit() {
        return midInit;
    }

    /**
     * @param midInit
     *            the midInit to set
     */
    public void setMidInit(String midInit) {
        this.midInit = midInit;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the securityLevel
     */
    public int getSecurityLevel() {
        return securityLevel;
    }

    /**
     * @param securityLevel
     *            the securityLevel to set
     */
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    /**
     * @return the lastLogin
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * @param lastLogin
     *            the lastLogin to set
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * @return the lastChange
     */
    public Date getLastChange() {
        return lastChange;
    }

    /**
     * @param lastChange
     *            the lastChange to set
     */
    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    /**
     * @return the assocType
     */
    public String getAssocType() {
        return assocType;
    }

    /**
     * @param assocType
     *            the assocType to set
     */
    public void setAssocType(String assocType) {
        this.assocType = assocType;
    }

}
