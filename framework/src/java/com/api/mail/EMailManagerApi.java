package com.api.mail;

import com.bean.mail.EMailBean;

/**
 * Interface that provides methods for setting up and sending emails.
 * 
 * @author roy.terrell
 * @deprecated Will be replaced by {@link com.api.messaging.email.CommonMailApi} in future versions.
 *
 */
public interface EMailManagerApi {
    /**
     * Transmits the email message to its destination.
     * 
     * @return int
     * @throws EMailException
     */
    int sendMail() throws EMailException;

    /**
     * Closes the Transport object.
     * 
     * @throws EMailException
     */
    void closeTransport() throws EMailException;

    /**
     * Sets the email bean.
     * 
     * @param value
     */
    void setEmailBean(EMailBean value);

    /**
     * Gets the name of the SMTP server.
     * 
     * @return String
     */
    String getSmtpServer();

    /**
     * Sets the SMTP Server name.
     * 
     * @param value String
     */
    void setSmtpServer(String value);

    /**
     * Gets the name of the POP Server.
     * 
     * @return String
     */
    String getPopServer();

    /**
     * Sets the name of the POP Server.
     * 
     * @param value String
     */
    void setPopServer(String value);

    /**
     * Gets the user id.
     * 
     * @return String
     */
    String getMailUser();

    /**
     * Sets the user id.
     * 
     * @param value String
     */
    void setMailUser(String value);

    /**
     * Gets the password.
     *  
     * @return String
     */
    String getMailPassword();

    /**
     * Sets the mail password.
     * 
     * @param value String
     */
    void setMailPassword(String value);

    /**
     * Gets the internal SMTP Domain name.
     * 
     * @return String
     */
    String getInternalSmtpDomain();

    /**
     * Sets the internal SMTP Domain name.
     * 
     * @param value String
     */
    void setInternalSmtpDomain(String value);
}
