package com.api.messaging.email.smtp;

import com.api.messaging.ProviderConfig;
import com.api.messaging.ResourceFactory;
import com.api.messaging.email.EmailException;

/**
 * Creates instances of SMTP related classes.
 * 
 * @author RTerrell
 *
 */
public class SmtpFactory {

    /**
     * Creates an instance of SmtpApi by which the SMTP configuration information 
     * is discovered internally by the system.
     * 
     * @return {@link com.api.messaging.email.smtp.SmtpApi SmtpApi} or null 
     *         when class cannot be instantiated.
     */
    public static final SmtpApi getSmtpInstance() {
        ProviderConfig config = ResourceFactory.getSmtpConfigInstance();
        return SmtpFactory.getSmtpInstance(config);
    }

    /**
     * 
     * @param smtpServerName
     * @param authenticate
     * @param userId
     * @param password
     * @return
     */
    public static final SmtpApi getSmtpInstance(String smtpServerName, boolean authenticate, String userId, String password) {
        ProviderConfig config = ResourceFactory.getSmtpConfigInstance(smtpServerName, authenticate, userId, password);
        return SmtpFactory.getSmtpInstance(config);
    }

    /**
     * 
     * @param config
     * @return
     */
    private static final SmtpApi getSmtpInstance(ProviderConfig config) {
        SmtpApi api = null;
        try {
            api = new SmtpImpl(config);
            return api;
        }
        catch (EmailException e) {
            return null;
        }
    }
}
