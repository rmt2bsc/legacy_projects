package com.remoteservices.http;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.*;
import java.net.*;
import java.util.*;

import com.bean.RMT2Base;

import com.util.RMT2Base64Encoder;

/**
 * A class to simplify HTTP applet-to-server or servlet-to-servlet
 * communication. It abstracts the communication into messages, which can be
 * either GET or POST. It can be used like this:
 * <p>
 * <blockquote> 
 * URL url = new URL(getCodeBase(), "/servlet/ServletName"); <br>
 * HttpClient msg = new HttpClient(url);
 * <p> 
 * // Parameters may optionally be set using java.util.Properties<br>
 * Properties props = new Properties(); <br>
 * props.put("name", "value");
 * <p> 
 * // Headers, cookies, and authorization may be set as well<br>
 * msg.setHeader("Accept", "image/png"); // optional <br>
 * msg.setCookie("JSESSIONID", "9585155923883872"); // optional<br>
 * msg.setAuthorization("guest", "try2gueSS"); // optional<br>
 * InputStream in = msg.sendGetMessage(props); </blockquote>
 * 
 * @author roy.terrell
 * @deprecated Will be removed in future releases.   Use {@link com.api.messaging.webservice.http.HttpClient HttpClient}.
 * 
 */

public class HttpClient extends RMT2Base {
    private Logger logger;

    private URL servlet;

    private URL tempURL;

    private Hashtable headers;

    private String protocol;

    private String host;

    private String urlSuffix;

    private int port;

    /**
     * Default constructor.
     * 
     */
    private HttpClient() {
        super();
        logger = Logger.getLogger("HttpClient");
        this.init();
    }

    /**
     * Constructs a new HttpClient that uses a String url to create an URL that
     * will be used to communicate with the servlet at the specified URI.
     * 
     * @param url
     *            The URL of the the server resource (typically a servlet) with
     *            which to communicate in String format.
     * @throws HttpException
     *             When a Malformed URL Exception occurs.
     */
    public HttpClient(String url) throws HttpException {
        this();
        try {
            this.servlet = new URL(url);
        }
        catch (MalformedURLException e) {
            this.msg = "A MalformedURLException error occurred.  More Details: " + e.getMessage() == null ? "" : e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new HttpException(this.msg);
        }
    }

    /**
     * Constructs a new HttpClient that can be used to communicate with
     * the servlet at the specified URL. _servletCodeBase the server resource
     * (typically a servlet) with which to communicate.
     * 
     * @param _servletCodeBase
     *            base URL
     */
    public HttpClient(URL _servletCodeBase) {
        this();
        this.servlet = _servletCodeBase;
    }

    /**
     * Constructs a new HttpMessage that can be used to communicate with the
     * servlet at the specified URL.
     * 
     * @param codeBase
     *            the client resource (typically an applet) with which is
     *            requesting to initiate communication with a servlet
     * @param servletName
     *            The name of the servlet to communicate with.
     * @throws HttpException
     */
    public HttpClient(URL codeBase, String servletName) throws HttpException {
        this(codeBase.getProtocol(), codeBase.getHost(), codeBase.getPort(), servletName);
        this.protocol = codeBase.getProtocol();
        this.host = codeBase.getHost();
        this.port = codeBase.getPort();
        this.urlSuffix = servletName;
    }

    /**
     * Constructs a new HttpClient using protocol, host, port, and
     * servletName to create an URL that will be used to communicate with an
     * actual servlet.
     * 
     * @param protocol
     *            the name of the protocol to use.
     * @param host
     *            The name of the host.
     * @param port
     *            The port number on the host.
     * @param servletName
     *            The servlet on the host.
     * @throws HttpException
     *             When a Malformed URL Exception occurs.
     */
    public HttpClient(String protocol, String host, int port, String servletName) throws HttpException {
        this();
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.urlSuffix = servletName;
        try {
            this.servlet = new URL(this.protocol, this.host, this.port, this.urlSuffix);
        }
        catch (MalformedURLException e) {
            this.msg = "A MalformedURLException error occurred.  More Details: " + e.getMessage() == null ? "" : e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new HttpException(this.msg);
        }
    }

    /**
     * Initializes member variables.
     */
    public void init() {
        super.init();
        this.tempURL = null;
        this.headers = null;
        this.protocol = null;
        this.host = null;
        this.urlSuffix = null;
        this.port = 0;
    }

    /**
     * Performs a GET request to the servlet, with no query string. Returns an
     * InputStream to read the response
     * 
     * @return InputStream
     * @throws HttpException
     */
    public InputStream sendGetMessage() throws HttpException {
        return sendGetMessage(null);
    }

    /**
     * Performs a GET request to the servlet, building a query string from the
     * supplied properties list.
     * 
     * @param args
     *            The arguments that are to be appended to the URL
     * @return InputStream
     * @throws HttpException
     */
    public InputStream sendGetMessage(Properties args) throws HttpException {
        String argString = ""; // default

        try {
            if (args != null) {
                argString = "?" + toEncodedString(args);
            }
            URL url = new URL(servlet.toExternalForm() + argString);

            // Turn off caching
            URLConnection con = url.openConnection();
            con.setUseCaches(false);

            // Send headers
            sendHeaders(con);

            return con.getInputStream();
        }
        catch (IOException e) {
            throw new HttpException(e.getMessage(), -1000);
        }
    }

    /**
     * Performs a POST request to the servlet, with no query string.
     * 
     * @return InputStream
     * @throws HttpException
     */
    public InputStream sendPostMessage() throws HttpException {
        return sendPostMessage(null);
    }

    /**
     * Performs a POST request to the servlet, building post data from the
     * supplied properties list.
     * 
     * @param args
     *            The arguments to build as http post data.
     * @return InputStream
     * @throws HttpException
     */
    public InputStream sendPostMessage(Properties args) throws HttpException {
        String argString = ""; // default
        if (args != null) {
            argString = toEncodedString(args); // notice no "?"
        }

        try {
            URLConnection con = servlet.openConnection();

            // Prepare for both input and output
            con.setDoInput(true);
            con.setDoOutput(true);

            // Turn off caching
            con.setUseCaches(false);

            // Work around a Netscape bug
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Send headers
            sendHeaders(con);

            // Write the arguments as post data
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(argString);
            out.flush();
            out.close();

            // In order for this call to work, ensure that the servlet has the 
            // doGet(HttpServletRequest, HttpServletResponse) and doPost(HttpServletRequest, HttpServletResponse) 
            // methods.   Without these two methods, this call will produce a FileNotFoundException.
            return con.getInputStream();
        }
        catch (Throwable e) {
            this.msg = e.getMessage();
            throw new HttpException(this.msg);
        }

    }

    /**
     * Performs a POST request to the servlet, uploading a serialized object.
     * The servlet can receive the object in its doPost() method like this:
     * <p>
     * <blockquote> ObjectInputStream objin = new
     * ObjectInputStream(req.getInputStream());<br>
     * Object obj = objin.readObject();<br>
     * The type of the uploaded object can be determined through introspection.<br>
     * </blockquote>
     * 
     * @param obj
     *            The obj to be posted
     * @return InputStream
     * @throws HttpException
     */
    public InputStream sendPostMessage(Serializable obj) throws HttpException {

        try {
            URLConnection con = servlet.openConnection();

            // Prepare for both input and output
            con.setDoInput(true);
            con.setDoOutput(true);

            // Turn off caching
            con.setUseCaches(false);

            // Set the content type to be application/x-java-serialized-object
            con.setRequestProperty("Content-Type", "application/x-java-serialized-object");

            // Send headers
            sendHeaders(con);

            // Write the serialized object as post data
            ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
            out.writeObject(obj);
            out.flush();
            out.close();

            // In order for this cal to work, ensure theat the servlet has the 
            // doGet(HttpServletRequest, HttpServletResponse) and doPost(HttpServletRequest, HttpServletResponse) 
            // methods.   Without these two methods, this call will produce a FileNotFoundException.
            return con.getInputStream();
        }
        catch (IOException e) {
            throw new HttpException(e.getMessage(), -1200);
        }
    }

    /**
     * Sets a request header with the given name and value. The header persists
     * across multiple requests. The caller is responsible for ensuring there
     * are no illegal characters in the name and value.
     * 
     * @param name
     *            The header name
     * @param value
     *            The header value.
     */
    public void setHeader(String name, String value) {
        if (headers == null) {
            headers = new Hashtable();
        }
        headers.put(name, value);
    }

    /**
     * Send the contents of the headers hashtable to the server
     * 
     * @param con
     *            The URL Connection object
     */
    private void sendHeaders(URLConnection con) {
        if (headers != null) {
            Enumeration enm = headers.keys();
            while (enm.hasMoreElements()) {
                String name = (String) enm.nextElement();
                String value = (String) headers.get(name);
                con.setRequestProperty(name, value);
            }
        }
    }

    /**
     * Sets the cookie header
     * 
     * @param name
     *            Cookie Header name
     * @param value
     *            Cookie header value.
     */
    public void setCookie(String name, String value) {
        if (headers == null) {
            headers = new Hashtable();
        }
        String existingCookies = (String) headers.get("Cookie");
        if (existingCookies == null) {
            setHeader("Cookie", name + "=" + value);
        }
        else {
            setHeader("Cookie", existingCookies + "; " + name + "=" + value);
        }
    }

    /**
     * Sets the authorization information for the request (using BASIC
     * authentication via the HTTP Authorization header). The authorization*
     * persists across multiple requests.
     * 
     * @param name
     *            The user name
     * @param password
     *            The user password.
     */
    public void setAuthorization(String name, String password) {
        String authorization = RMT2Base64Encoder.encode(name + ":" + password);
        setHeader("Authorization", "Basic " + authorization);
    }

    /**
     * Converts a properties list to a URL-encoded query string
     * 
     * @param args
     *            Properties resource to convert
     * @return String of URL formatted key/value arguments
     */
    private String toEncodedString(Properties args) {
        StringBuffer buf = new StringBuffer();
        Enumeration names = args.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = args.getProperty(name);
            buf.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value));
            if (names.hasMoreElements()) {
                buf.append("&");
            }
        }
        return buf.toString();
    }
}
