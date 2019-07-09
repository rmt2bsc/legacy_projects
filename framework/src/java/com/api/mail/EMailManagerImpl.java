package com.api.mail;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.NoSuchProviderException;
import javax.mail.AuthenticationFailedException;
import javax.mail.IllegalWriteException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import java.util.List;
import java.util.Properties;

import java.util.Date;

import com.api.AbstractApiImpl;

import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;

import com.bean.mail.EMailBean;

import com.util.RMT2String;
import com.util.SystemException;

/**
 * This API is used for sending email using the SMTP Protocol. Inherits
 * AbstractApiImpl class and implements the EMailManagerApi interface.
 * 
 * @author roy.terrell
 * @deprecated Will be replaced by {@link com.api.messaging.email.smtp.SmtpImpl} in future versions.
 * 
 */
public class EMailManagerImpl extends AbstractApiImpl implements EMailManagerApi {

    private EMailBean emailBean;

    private Session emailSession;

    private MimeMessage email;

    private SMTPTransport tp;

    private String smtpServer;

    private String popServer;

    private boolean isAuthenticate;

    private String mailUser;

    private String mailPassword;

    private String internalSMTPDomain;

    /** Email message content is of type text. */
    protected final int TEXT_CT = 1;

    /** Email message content is of type HTML. */
    protected final int HTML_CT = 2;

    /** SMTP Server key name */
    protected final String SMTP_SERVER = "SMTP_SERVER";

    /** POP Server key name */
    protected final String POP_SERVER = "POP_SERVER";

    /** Mail Authentication key name */
    protected final String MAIL_AUTHENTICATE = "MAIL_AUTHENTICATE";

    /** SMTP Server user name key name */
    protected final String MAIL_USERNAME = "MAIL_USERNAME";

    /** SMTP Server user password key name */
    protected final String MAIL_PASSWORD = "MAIL_PASSWORD";

    /**
     * Creates an instance of EMailManagerImpl class.
     * 
     * @return EMailManagerImpl
     */
    public static EMailManagerImpl getInstance() {
        try {
            return new EMailManagerImpl();
        }
        catch (SystemException e) {
            return null;
        }
    }

    /**
     * Obtains preference values from the database to be used throughout the
     * life of the API. Creates the EMail session object and the Message object.
     * Not callable by user.
     * 
     * @throws SystemException
     */
    private EMailManagerImpl() throws SystemException {
        super();
        Properties props = new Properties();
        String flag;

        // Set Outgoing Server

        props.put("mail.smtp.host", this.smtpServer);
        // Enable SMTP authentication
        flag = String.valueOf(this.isAuthenticate);
        props.put("mail.smtp.auth", flag);

        // Initialize E-Mail Session
        this.emailSession = Session.getDefaultInstance(props, null);
        // Create a MIME style email message
        this.email = new MimeMessage(this.emailSession);
    }

    /**
     * Initializes the SMTP and POP server names, user id and password values.
     */
    protected void initApi() throws DatabaseException, SystemException {
        String temp;
        this.emailSession = null;
        this.email = null;
        this.tp = null;
        this.smtpServer = null;
        this.isAuthenticate = false;

        // Get SMTP Server value
        this.smtpServer = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SMTP_SERVER);
        // Get POP Server value
        this.popServer = System.getProperty(HttpSystemPropertyConfig.PROPNAME_POP_SERVER);
        // Get Authentication value
        temp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_MAIL_AUTH);
        this.isAuthenticate = (temp.equalsIgnoreCase("true"));

        // Get user name and password for mail server if authentication is
        // required
        if (this.isAuthenticate) {
            // Get User Name
            this.mailUser = System.getProperty(HttpSystemPropertyConfig.PROPNAME_MAIL_UID);
            // Get Password
            this.mailPassword = System.getProperty(HttpSystemPropertyConfig.PROPNAME_MAIL_PW);
        }

        // Get internal email doamin name.
        temp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_INTERNAL_SMTP_DOMAIN);
        this.internalSMTPDomain = temp;
    }

    /**
     * Closes the Transport object (tp) if it is valid and nullifies selected
     * member variables.
     * 
     * @throws EMailException
     */
    public void closeTransport() throws EMailException {
        this.emailSession = null;
        this.email = null;
        this.smtpServer = null;
        this.popServer = null;

        try {
            if (this.tp != null && this.tp.isConnected()) {
                this.tp.close();
            }
        }
        catch (MessagingException e) {
            throw new EMailException(e.getMessage());
        }
        finally {
            this.tp = null;
        }

    } // End close

    /**
     * Initializes the components of E-Mail message and transmits the email
     * message to its recipient(s). If authentication is required, then a
     * transport object is created in order to email the message. Otherwise, the
     * message is sent using a static call to "send" of the Transport object.
     * Various Exceptions are caught based on a given issue.
     * 
     * @return Always returns 1.
     * @throws EMailException
     */
    public int sendMail() throws EMailException {
        this.validateMessage();
        this.setupMessage();
        this.transportMessage();
        return 1;
    }

    /**
     * Validates the EMailBean Object by ensuring that the object is
     * instantiated, the To Address has at least on email address, and the From
     * Address is populated with an email address.
     * 
     * @throws EMailException
     *             if the email bean is null or the From-Address and/or the
     *             To-Address is null.
     */
    private void validateMessage() throws EMailException {
        if (this.emailBean == null) {
            throw new EMailException("Email bean object is invalid");
        }
        if (this.emailBean.getFromAddress() == null) {
            throw new EMailException("Email From-Address is required");
        }
        if (this.emailBean.getToAddress() == null) {
            throw new EMailException("Email To-Address is required");
        }
    }

    /**
     * Initializes the components of E-Mail message. Various Exceptions are
     * caught based on a given issue.
     * 
     * @throws EMailException
     *             When an IllegalWriteException, IllegalStateException, or a
     *             MessagingException occurs.
     */
    private void setupMessage() throws EMailException {
        InternetAddress addr[];
        String msg = null;

        // Begin initializing E-Mail Components
        try {
            msg = this.emailBean.getFromAddress().toString();
            this.email.setFrom(this.emailBean.getFromAddress());

            // Add To addresses
            msg = InternetAddress.toString(this.emailBean.getToAddress());
            msg = "To Recipients";
            this.email.setRecipients(MimeMessage.RecipientType.TO, this.emailBean.getToAddress());

            // Check if we need to add CC Recipients
            msg = "CC Recipients";
            addr = this.emailBean.getCCAddress();
            if (addr != null && addr.length > 0) {
                this.email.setRecipients(MimeMessage.RecipientType.CC, addr);
            }

            // Check if we need to add BCC Recipients
            msg = "BCC Recipients";
            addr = this.emailBean.getBCCAddress();
            if (addr != null && addr.length > 0) {
                this.email.setRecipients(MimeMessage.RecipientType.BCC, addr);
            }

            msg = "Subject Line";
            this.email.setSubject(this.emailBean.getSubject());
            msg = "Sent Date";
            this.email.setSentDate(new Date());
            msg = "Header Line";
            this.email.setHeader("X-Mailer", "MailFormJava");
            msg = "Body Content";
            this.email.setContent(this.emailBean.assembleBody());
        }
        catch (IllegalWriteException e) {
            throw new EMailException("EMailBean Error on " + msg + ": An IllegalWriteException occurred");
        }
        catch (IllegalStateException e) {
            throw new EMailException("EMailBean Error on " + msg + ": An IllegalStateException occurred");
        }
        catch (MessagingException e) {
            throw new EMailException("EMailBean Error on " + msg + ": An MessagingException occurred");
        }
    }

    /**
     * Performs the actual routing of message. If authentication is required,
     * then a transport object is created in order to email the message.
     * Otherwise, the message is sent using a static call to "send" of the
     * Transport object. Various Exceptions are caught based on a given issue.
     * 
     * @throws EMailException
     */
    private void transportMessage() throws EMailException {
        Address addr[] = null;
        String msg = null;
        try {
            if (this.isAuthenticate) {
                if (this.tp == null || !this.tp.isConnected()) {
                    addr = this.emailBean.getToAddress();
                    this.tp = (SMTPTransport) this.emailSession.getTransport(addr[0]);
                    // The "From" email address must be a valid account that can be authenticated
                    String email = this.emailBean.getFromAddress().getAddress();
                    List<String> tokens = RMT2String.getTokens(email, "@");
                    String username = "invalid_user";
                    if (tokens != null && tokens.size() > 0) {
                        username = tokens.get(0);
                    }
                    this.tp.connect(this.smtpServer, username, this.mailPassword);
                }

                if (this.tp.isConnected()) {
                    this.email.saveChanges();
                    // Must use sendMessage to transport email since
                    // authentication is required.
                    this.tp.sendMessage(this.email, this.email.getAllRecipients());
                    this.tp.close();
                }
            }
            else {
                // Send message the simple way
                Transport.send(this.email);
            }
            System.out.println("Email was sent successfully to " + addr[0].toString());
        }
        catch (NoSuchProviderException e) {
            msg = "NoSuchProviderException occurred - " + e.getMessage() + " Address: " + addr[0].toString();
            System.out.println(msg);
            throw new EMailException(msg);
        }
        catch (AuthenticationFailedException e) {
            System.out.println("AuthenticationFailedException occurred - " + e.getMessage() + " Address: " + addr[0].toString());
            throw new EMailException("EMailBean Error: A AuthenticationFailedException error occurred");
        }
        catch (IllegalStateException e) {
            msg = "IllegalStateException occurred - " + e.getMessage() + " Address: " + addr[0].toString();
            System.out.println(msg);
            throw new EMailException(msg, -1);
        }
        catch (SendFailedException e) {
            msg = "SendFailedException occurred - " + e.getMessage() + " Address: " + addr[0].toString();
            System.out.println(msg);
            throw new EMailException(msg, -1);
        }
        catch (MessagingException e) {
            msg = "MessageException occurred - " + e.getMessage() + " Address: " + addr[0].toString();
            System.out.println(msg);
            throw new EMailException(msg, -1);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setEmailBean(EMailBean value) {
        this.emailBean = value;
    }

    /**
     * {@inheritDoc}
     */
    public String getSmtpServer() {
        return this.smtpServer;
    }

    /**
     * {@inheritDoc}
     */
    public void setSmtpServer(String value) {
        this.smtpServer = value;
    }

    /**
     * {@inheritDoc}
     */
    public String getPopServer() {
        return this.popServer;
    }

    /**
     * {@inheritDoc}
     */
    public void setPopServer(String value) {
        this.popServer = value;
    }

    /**
     * {@inheritDoc}
     */
    public String getMailUser() {
        return this.mailUser;
    }

    /**
     * {@inheritDoc}
     */
    public void setMailUser(String value) {
        this.mailUser = value;
    }

    /**
     * {@inheritDoc}
     */
    public String getMailPassword() {
        return this.mailPassword;
    }

    /**
     * {@inheritDoc}
     */
    public void setMailPassword(String value) {
        this.mailPassword = value;
    }

    /**
     * {@inheritDoc}
     */
    public String getInternalSmtpDomain() {
        return this.internalSMTPDomain;
    }

    /**
     * {@inheritDoc}
     */
    public void setInternalSmtpDomain(String value) {
        this.internalSMTPDomain = value;
    }

}
