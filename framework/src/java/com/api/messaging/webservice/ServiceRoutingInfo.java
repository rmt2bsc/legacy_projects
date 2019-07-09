package com.api.messaging.webservice;

import com.bean.RMT2Base;

/**
 * Helper class that manages various properties of a service record such as service 
 * name, URL of the service, and its security indicator.
 * 
 * @author appdev
 */
public class ServiceRoutingInfo extends RMT2Base {
    private String host;

    private String name;

    private String url;

    private boolean secured;

    /**
     * 
     */
    public ServiceRoutingInfo() {
        return;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the secured
     */
    public boolean isSecured() {
        return secured;
    }

    /**
     * @param secured the secured to set
     */
    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

}
