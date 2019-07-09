package com.api.messaging;

import com.util.SystemException;

/**
 * An interface for binding messages to an object and binding objects to messages.
 * 
 * @author RTerrell
 *
 */
public interface MessageBinder {

    /**
     * Converting a java object to String form.
     * 
     * @param msgObj
     *          an arbitrary object representing the message 
     * @return String
     *          the results of the conversion.
     * @throws SystemException
     */
    String marshalMessage(Object msgObj) throws SystemException;

    /**
     * Converting a java object to String form and optionally formats the output for better readability.
     * 
     * @param msgObj
     *          the java object that is to be converted.
     * @param formatOutput
     *          boolean indicating if XML is to be formatted for readability.  When set 
     *          to true, the document will be formatted.  When set to false, the document 
     *          will be presented in raw format.
     * @return String
     *          the XML document
     * @throws SystemException
     */
    String marshalMessage(Object msgObj, boolean formatOutput) throws SystemException;

    /**
     * Converting a String to object form.
     * 
     * @param msgStr
     *          a String representing the message
     * @return Object
     *          an arbitrary object
     * @throws SystemException
     */
    Object unMarshalMessage(String msgStr) throws SystemException;

    /**
     * Initializes the target environment for binding objects.
     * 
     * @param source
     *          points to an object or area which serves as the source of 
     *          input for the process of initializing the binding environment.
     * @throws SystemException
     */
    void setupEnv(Object source) throws SystemException;
}
