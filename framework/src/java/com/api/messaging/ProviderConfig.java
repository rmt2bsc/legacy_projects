package com.api.messaging;

import com.bean.RMT2Base;

/**
 * Manages environment data regarding the messaging provider that is 
 * to be used.
 * 
 * @author appdev
 * 
 */
public class ProviderConfig extends RMT2Base {

    private String host;

    private String port;

    private String userId;

    private String password;

    private String jndiContextName;

    private String connectionFactory;

    private String messageId;

    private String guid;

    private boolean authenticate;

    private Object contextData;

    private Object requestData;

    private Object responseData;

    /**
     * Default constructor
     */
    protected ProviderConfig() {
        return;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * @return the jndiContext
     */
    public String getJndiContextName() {
        return jndiContextName;
    }

    /**
     * @param jndiContext the jndiContext to set
     */
    public void setJndiContextName(String jndiContext) {
        this.jndiContextName = jndiContext;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     *            the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid
     *            the guid to set
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return the authenticate
     */
    public boolean isAuthenticate() {
        return authenticate;
    }

    /**
     * @param authenticate
     *            the authenticate to set
     */
    public void setAuthenticate(boolean authenticate) {
        this.authenticate = authenticate;
    }

    /**
     * @return the requestData
     */
    public Object getRequestData() {
        return requestData;
    }

    /**
     * @param requestData
     *            the requestData to set
     */
    public void setRequestData(Object requestData) {
        this.requestData = requestData;
    }

    /**
     * @return the responseData
     */
    public Object getResponseData() {
        return responseData;
    }

    /**
     * @param responseData
     *            the responseData to set
     */
    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    /**
     * @return the contextData
     */
    public Object getContextData() {
        return contextData;
    }

    /**
     * @param contextData the contextData to set
     */
    public void setContextData(Object contextData) {
        this.contextData = contextData;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(String connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }
}
