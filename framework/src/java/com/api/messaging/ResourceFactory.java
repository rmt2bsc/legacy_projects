package com.api.messaging;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.config.HttpSystemPropertyConfig;
import com.api.messaging.JaxbBinder;
import com.api.messaging.MessageBinder;

/**
 * Assists clients as a factory for creating various Messaging resources 
 * necessary for connections, sessions, destinations and etc.  Currently, 
 * this api provide implementations for the following messaging resources: 
 * SMTP, POP3, JMS, and JAXB binding.  
 * 
 * @author RTerrell
 * 
 */
public class ResourceFactory {

    private static Logger logger = Logger.getLogger("ResourceFactory");

    public static final String JAXB_DEFAULT_PKG = System.getProperty("jms.jaxb.defaultpackage");

    private static MessageBinder DEFAULT_BINDER;

    /**
     * Obtains an empty initialized ProviderConfig instance. 
     * 
     * @return {@link com.api.messaging.ProviderConfig ProviderConfig}
     */
    public static ProviderConfig getConfigInstance() {
        ProviderConfig config = new ProviderConfig();
        return config;
    }

    /**
     * Builds HTTP URL provider configuration information from System properties
     * 
     * @return {@link com.api.messaging.ProviderConfig ProviderConfig}
     * @throws MessagingException
     */
    public static ProviderConfig getHttpConfigInstance() throws MessagingException {
        String url;
        String server;
        String servicesApp;
        String servicesServlet;

        // Build service URL
        server = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);
        servicesApp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICE_APP);
        servicesServlet = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICE_SERVLET);
        url = server + "/" + servicesApp + "/" + servicesServlet;

        // Validate syntax of URL
        try {
            new URL(url);
            ProviderConfig config = new ProviderConfig();
            config.setHost(url);
            return config;
        }
        catch (MalformedURLException e) {
            String msg = "HTTP Web Service URL, " + url + ", is syntactically incorrect.  Other Message: " + e.getMessage();
            logger.log(Level.ERROR, msg);
            throw new MessagingException(msg);
        }
    }

    /**
     * Builds SMTP provider configuration information from System properties
     * 
     * @return {@link com.api.messaging.ProviderConfig ProviderConfig}
     */
    public static ProviderConfig getSmtpConfigInstance() {
        ProviderConfig config = new ProviderConfig();
        String temp = null;
        temp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SMTP_SERVER);
        config.setHost(temp);

        // Get Authentication value
        temp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_MAIL_AUTH);
        boolean authRequired = temp == null ? false : new Boolean(temp).booleanValue();
        config.setAuthenticate(authRequired);

        String mailUser = null;
        String mailPassword = null;
        // Get user name and password for mail server if authentication is required
        if (authRequired) {
            // Get User Name
            mailUser = System.getProperty(HttpSystemPropertyConfig.PROPNAME_MAIL_UID);
            // Get Password
            mailPassword = System.getProperty(HttpSystemPropertyConfig.PROPNAME_MAIL_PW);
            config.setUserId(mailUser);
            config.setPassword(mailPassword);
        }
        return config;
    }

    /**
     * Builds SMTP provider configuration from data supplied by the user.
     * 
     * @param smtpServerName
     * @param authenticate
     * @param userId
     * @param password
     * @return
     */
    public static ProviderConfig getSmtpConfigInstance(String smtpServerName, boolean authenticate, String userId, String password) {
        ProviderConfig config = new ProviderConfig();
        config.setHost(smtpServerName);
        config.setAuthenticate(authenticate);
        if (authenticate) {
            config.setUserId(userId);
            config.setPassword(password);
        }
        return config;
    }

    /**
     * 
     * @param pop3ServerName
     * @param authenticate
     * @param userId
     * @param password
     * @return
     */
    public static ProviderConfig getPop3ConfigInstance(String pop3ServerName, boolean authenticate, String userId, String password) {
        ProviderConfig config = new ProviderConfig();
        config.setHost(pop3ServerName);
        config.setAuthenticate(authenticate);
        if (authenticate) {
            config.setUserId(userId);
            config.setPassword(password);
        }
        return config;
    }

    /**
     * 
     * @param providerUrl
     * @param contextClassName
     * @return
     */
    public static ProviderConfig getJmsConfigInstance(String providerUrl, String contextClassName) {
        ProviderConfig config = new ProviderConfig();
        config.setHost(providerUrl);
        config.setJndiContextName(contextClassName);
        return config;
    }

    /**
     * 
     * @return
     */
    public static ProviderConfig getJmsConfigInstance() {
        ProviderConfig config = new ProviderConfig();
        String providerUrl = System.getProperty("jms.providerurl");
        String contextClassName = System.getProperty("jms.contextclass");
        String conFactory = System.getProperty("jms.connectionfactory");

        config.setHost(providerUrl);
        config.setJndiContextName(contextClassName);
        config.setConnectionFactory(conFactory);
        return config;
    }

    /**
     * Obtains the ProviderConfig instance for the SOAP processor.  The only property set in the ProviderConfig 
     * instance will be the SOAP Host.  The SOAP Host should be configured as the System property and can be 
     * fetched as {@link com.api.config.HttpSystemPropertyConfig.SOAP_HOST SOAP_HOST} 
     * 
     * @return {@link com.api.messaging.ProviderConfig ProviderConfig}
     */
    public static ProviderConfig getSoapConfigInstance() {
        ProviderConfig config = new ProviderConfig();
        String soapHost = System.getProperty(HttpSystemPropertyConfig.SOAP_HOST);
        config.setHost(soapHost);
        return config;
    }

    /**
     * Obtains a reference to the default JAXB binder instance.  If the binder has not been 
     * initialized, then an instance is created.  The default JAXB context is considered to 
     * be com.xml.schema.bindings.  This method manages the JAXB binder instance as a 
     * Singleton.
     * 
     * @return {@link MessageBinder}
     */
    public static MessageBinder getJaxbMessageBinder() {
        if (ResourceFactory.DEFAULT_BINDER == null) {
            ResourceFactory.DEFAULT_BINDER = new JaxbBinder();
        }
        return ResourceFactory.DEFAULT_BINDER;
    }

    /**
     * Creates an instance of MessageBinder from a JAXB implementation which 
     * initializes the JAXB context with the specified package name. 

     * @param packageName
     *          String value that is the name of the java package where the JAXB 
     *          objects live.
     * @return {@link MessageBinder}
     */
    public static MessageBinder getJaxbMessageBinder(String packageName) {
        MessageBinder api = new JaxbBinder(packageName);
        return api;
    }
}
