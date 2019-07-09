package com.bean.mail;

//import java.util.Date;
import java.util.ArrayList;

//import javax.mail.Multipart;
import javax.mail.MessagingException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import javax.mail.internet.AddressException;

import javax.activation.FileDataSource;
import javax.activation.DataHandler;

import com.api.mail.EMailException;

import com.bean.RMT2BaseBean;

import com.constants.GeneralConst;

//import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * This class is designed to capture, house, and track data items that are
 * essential for setting up and sending/receiving electronic mail.
 * 
 * @author roy.terrell
 * @deprecated Will be replaced by {@link com.api.messaging.email.EmailMessageBean} in future versions.
 * 
 */
public class EMailBean extends RMT2BaseBean {

    private InternetAddress fromAddress;

    private InternetAddress toAddress[];

    private InternetAddress ccAddress[];

    private InternetAddress bccAddress[];

    private MimeBodyPart body;

    private MimeMultipart mainBody;

    private ArrayList attachments;

    private String subject;

    private int contentType;

    /** Value that describes the text email content type. */
    public static final String TEXT_CONTENT = "text/plain";

    /** Value that describes the HTML email content type. */
    public static final String HTML_CONTENT = "text/html";

    /** Value that describes the ascii email content type. */
    public static final String ASCII_CONTENT = "US-ASCII";

    /** Semi colon as a Mulitiple internet address String delimiter */
    public static final int ADDR_DELIM_SEMICOLON = 0;

    /** Comma as a Mulitiple internet address String delimiter */
    public static final int ADDR_DELIM_COMMA = 1;

    /** Space as a Mulitiple internet address String delimiter */
    public static final int ADDR_DELIM_SPACE = 2;

    /**
     * Constructs an EMailBean object and initializes its member variables.
     * 
     * @throws SystemException
     */
    public EMailBean() throws SystemException {
        super();
    }

    /**
     * Initializes the member variables of this class.
     */
    public void initBean() throws SystemException {
        this.fromAddress = new InternetAddress();
        this.toAddress = null;
        this.ccAddress = null;
        this.bccAddress = null;
        this.body = null;
        this.mainBody = new MimeMultipart();
        this.attachments = new ArrayList();
        this.subject = null;
        this.contentType = 0;
    }

    /**
     * Get the From address.
     * 
     * @return InternetAddress
     */
    public InternetAddress getFromAddress() {
        return this.fromAddress;
    }

    /**
     * Set the From Address using an String email address.
     * 
     * @param value
     *            String
     */
    public void setFromAddress(String value) {
        this.fromAddress.setAddress(value);
    }

    /**
     * Get the To Address.
     * 
     * @return InternetAddress[]
     */
    public InternetAddress[] getToAddress() {
        return this.toAddress;
    }

    /**
     * Set the To Address using a String of one or more email internet addresses
     * separated by commas.
     * 
     * @param value
     *            A list of addresses separated by commas.
     */
    public void setToAddress(String value) {
        this.toAddress = this.initAddress(value);
    }

    /**
     * Get CC Address.
     * 
     * @return InternetAddress[]
     */
    public InternetAddress[] getCCAddress() {
        return this.ccAddress;
    }

    /**
     * Set the CC Address using a String of one or more email internet addresses
     * separated by commas.
     * 
     * @param value
     *            A list of addresses separated by commas.
     */
    public void setCCAddress(String value) {
        this.ccAddress = this.initAddress(value);
    }

    /**
     * Get Blind CC Address.
     * 
     * @return InternetAddress[]
     */
    public InternetAddress[] getBCCAddress() {
        return this.bccAddress;
    }

    /**
     * Set the Blind CC Address using a String of one or more email internet
     * addresses separated by commas.
     * 
     * @param value
     *            A list of addresses separated by commas.
     */
    public void setBCCAddress(String value) {
        this.bccAddress = this.initAddress(value);
    }

    /**
     * Get body part of email.
     * 
     * @return MimeBodyPart
     */
    public MimeBodyPart getBody() {
        return this.body;
    }

    /**
     * Set the body part of the email. In the event thee body cannot be
     * initialized, then it is set to null.
     * 
     * @param _content
     *            The content of the body.
     * @param _mimeType
     *            The MIME type. Defaults to HTML content when passed as null.
     */
    public void setBody(Object _content, String _mimeType) {

        try {
            if (_mimeType == null) {
                // Default to text/plain
                _mimeType = EMailBean.HTML_CONTENT;
            }
            if (this.body == null) {
                this.body = new MimeBodyPart();
            }
            this.body.setContent(_content, _mimeType);
        }
        catch (MessagingException e) {
            this.body = null;
        }
    }

    /**
     * Get the email attachments.
     * 
     * @return ArrayList
     */
    public ArrayList getAttachments() {
        return this.attachments;
    }

    /**
     * Adds an attachment to the email based on _fileName.
     * 
     * @param _fileName
     *            The path of the file to attach to the email.
     */
    public void setAttachments(String _fileName) {
        MimeBodyPart obj = new MimeBodyPart();
        try {
            // Put a file in the second part
            FileDataSource fds = new FileDataSource(_fileName);
            obj.setDataHandler(new DataHandler(fds));
            obj.setFileName(fds.getName());

            // Add attachment to list of attachments for this EMail
            if (this.attachments == null) {
                this.attachments = new ArrayList();
            }
            this.attachments.add(obj);
            return;
        }
        catch (MessagingException e) {
            // Do nothing
        }
    }

    /**
     * Creates an array of InternetAddress objects using a String list of emails
     * separated by commas.
     * 
     * @param emailAddresses
     *            A list of one or more emails separateda by commas.
     * @return An array of InternetAddress objects. Returns null if
     *         emailAddresses cannot be parsed.
     */
    protected InternetAddress[] initAddress(String emailAddresses) {
        try {
            return InternetAddress.parse(emailAddresses);
        }
        catch (AddressException e) {
            return null;
        }
    }

    /**
     * Gets the email address as a String.
     * 
     * @param address
     *            InternetAddress
     * @return email address or null if address is invalid.
     */
    public static String toString(InternetAddress address) {
        // Address must be valid
        if (address == null) {
            return null;
        }
        return address.getAddress();
    }

    /**
     * Gets the email address of each element in the InternetAddress array and
     * concatenates each email address into a String list. By default, each
     * address is separted by a semi-colon.
     * 
     * @param addresses
     *            An array of InternetAddress objects.
     * @return String list of emails separated by semi-colons.
     */
    public static String toString(InternetAddress addresses[]) {
        return EMailBean.toString(addresses, EMailBean.ADDR_DELIM_SEMICOLON);
    }

    /**
     * Gets the email address of each element in the InternetAddress array and
     * concatenates each email address into a String list separted by delimiter.
     * 
     * @param addresses
     *            An array of InternetAddress objects.
     * @param delimiter
     *            An integer value representing semi-colon=0, comma=1, or
     *            space=3.
     * @return String list of emails separated by the specified delimiter.
     *         Returns null if addresses does not contain at least one email, if
     *         delimiter is invalid, or if emails could not be parsed.
     */
    public static String toString(InternetAddress addresses[], int delimiter) {
        String strDelim = null;

        // Must have at least one address to process.
        if (addresses.length <= 0) {
            return null;
        }
        // Determine the delimiter that will separate emails
        switch (delimiter) {
        case EMailBean.ADDR_DELIM_COMMA:
            strDelim = ",";
            break;
        case EMailBean.ADDR_DELIM_SEMICOLON:
            strDelim = ";";
            break;
        case EMailBean.ADDR_DELIM_SPACE:
            strDelim = GeneralConst.SPACE;
            break;
        default:
            return null;
        }
        return EMailBean.toString(addresses, strDelim);
    }

    /**
     * Gets the email address of each element in the InternetAddress array and
     * concatenates each email address into a String list separted by the String
     * delimiter.
     * 
     * @param addresses
     *            An array of InternetAddress objects.
     * @param delimiter
     *            The delimiter (semi-colon, comma, or space) that separates the
     *            emails.
     * @return String list of emails separated by the specified delimiter.
     */
    private static String toString(InternetAddress addresses[], String delimiter) {
        String results = "";
        for (int ndx = 0; ndx < addresses.length; ndx++) {
            if (results.length() > 0) {
                results += ";" + EMailBean.toString(addresses[ndx]);
            }
            else {
                results += EMailBean.toString(addresses[ndx]);
            }
        }
        return results;
    }

    /**
     * Get the Email subject.
     * 
     * @return String.
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Set the email subject
     * 
     * @param value
     *            String
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Set the email content type.
     * 
     * @param value
     *            int.
     */
    public void setContentType(int value) {
        this.contentType = value;
    }

    /**
     * Get the content type of the email.
     * 
     * @return int
     */
    public int getContentType() {
        return this.contentType;
    }

    /**
     * Builds the email body and adds any available attachements.
     * 
     * @return MimeMultipart
     * @throws EMailException
     *             If the body and/or attachments could not be added to the
     *             MimeMulitpart object.
     */
    public MimeMultipart assembleBody() throws EMailException {
        MimeBodyPart temp;

        try {
            // Add Main Body Content
            if (this.body != null) {
                this.mainBody.addBodyPart(this.body);
            }

            // Add any attachments, if applicable
            for (int ndx = 0; ndx < this.attachments.size(); ndx++) {
                temp = (MimeBodyPart) this.attachments.get(ndx);
                this.mainBody.addBodyPart(temp);
            }
            return this.mainBody;
        }
        catch (MessagingException e) {
            throw new EMailException(e);
        }
    }

}